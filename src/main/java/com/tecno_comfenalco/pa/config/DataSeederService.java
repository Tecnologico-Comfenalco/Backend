package com.tecno_comfenalco.pa.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.tecno_comfenalco.pa.features.catalog.CatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.ProductsCatalogEntity;
import com.tecno_comfenalco.pa.features.catalog.repository.ICatalogRepository;
import com.tecno_comfenalco.pa.features.catalog.repository.IProductsCatalogRepository;
import com.tecno_comfenalco.pa.features.category.CategoryEntity;
import com.tecno_comfenalco.pa.features.category.repository.ICategoryRepository;
import com.tecno_comfenalco.pa.features.distributor.DistributorEntity;
import com.tecno_comfenalco.pa.features.distributor.repository.IDistributorRepository;
import com.tecno_comfenalco.pa.features.order.OrderDetailEntity;
import com.tecno_comfenalco.pa.features.order.OrderDetailIdEmbedded;
import com.tecno_comfenalco.pa.features.order.OrderEntity;
import com.tecno_comfenalco.pa.features.order.repository.IOrderRepository;
import com.tecno_comfenalco.pa.features.presales.PresalesEntity;
import com.tecno_comfenalco.pa.features.presales.repository.IPresalesRepository;
import com.tecno_comfenalco.pa.features.product.ProductEntity;
import com.tecno_comfenalco.pa.features.product.repository.IProductRepository;
import com.tecno_comfenalco.pa.features.store.StoreEntity;
import com.tecno_comfenalco.pa.features.store.StoresDistributorsEntity;
import com.tecno_comfenalco.pa.features.store.repository.IStoreRepository;
import com.tecno_comfenalco.pa.features.store.repository.IStoresDistributorsRepository;
import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.dto.DirectionDto;
import com.tecno_comfenalco.pa.shared.enums.DocumentTypeEnum;
import com.tecno_comfenalco.pa.shared.enums.StoreClaimStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataSeederService {

    @Qualifier("postgresUserRepository")
    private final IUserRepository userRepository;

    @Qualifier("postgresDistributorRepository")
    private final IDistributorRepository distributorRepository;

    @Qualifier("postgresStoreRepository")
    private final IStoreRepository storeRepository;

    @Qualifier("postgresPresalesRepository")
    private final IPresalesRepository presalesRepository;

    @Qualifier("postgresCatalogRepository")
    private final ICatalogRepository catalogRepository;

    @Qualifier("postgresCategoryRepository")
    private final ICategoryRepository categoryRepository;

    @Qualifier("postgresProductRepository")
    private final IProductRepository productRepository;

    @Qualifier("postgresOrderRepository")
    private final IOrderRepository orderRepository;

    private final IStoresDistributorsRepository storesDistributorsRepository;

    private final IProductsCatalogRepository productsCatalogRepository;

    private final PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Transactional
    public void seedDatabase(int totalRecords) {
        log.info("Iniciando generación de {} registros falsos...", totalRecords);

        // 1. Crear distribuidoras (10% de los registros) - cada una con su usuario
        // dedicado
        List<DistributorEntity> distributors = createDistributors(totalRecords / 100);
        log.info("Creadas {} distribuidoras con sus usuarios", distributors.size());

        // 2. Crear preventa (5% de los registros) - cada uno con su usuario dedicado y
        // asociado a un distribuidor
        List<PresalesEntity> presalesList = createPresales(distributors, totalRecords / 200);
        log.info("Creados {} preventistas con sus usuarios", presalesList.size());

        // 3. Crear tiendas (15% de los registros) - cada una con su usuario dedicado
        List<StoreEntity> stores = createStores(totalRecords / 7);
        log.info("Creadas {} tiendas con sus usuarios", stores.size());

        // 5. Crear relaciones tiendas-distribuidoras
        createStoresDistributors(stores, distributors);
        log.info("Creadas relaciones tiendas-distribuidoras");

        // 6. Crear catálogos (uno por distribuidora)
        List<CatalogEntity> catalogs = createCatalogs(distributors);
        log.info("Creados {} catálogos", catalogs.size());

        // 7. Crear categorías (5 por catálogo)
        List<CategoryEntity> categories = createCategories(catalogs);
        log.info("Creadas {} categorías", categories.size());

        // 8. Crear productos (40% de los registros)
        List<ProductEntity> products = createProducts(distributors, totalRecords * 2 / 5);
        log.info("Creados {} productos", products.size());

        // 9. Vincular productos con categorías
        linkProductsToCategories(products, categories);
        log.info("Productos vinculados a categorías");

        // 10. Crear órdenes (30% de los registros)
        createOrders(stores, presalesList, distributors, products, totalRecords * 3 / 10);
        log.info("Creadas órdenes con sus detalles");

        log.info("Generación de datos completada exitosamente!");
    }

    /**
     * Crea UN usuario dedicado para UN distribuidor con rol DISTRIBUTOR
     */
    private List<DistributorEntity> createDistributors(int count) {
        List<DistributorEntity> distributors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Crear usuario específico para este distribuidor
            UserEntity user = UserEntity.builder()
                    .username(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("password123"))
                    .enabled(true)
                    .roles(Set.of("DISTRIBUTOR")) // Solo rol DISTRIBUTOR
                    .build();
            user = userRepository.save(user);

            // Crear distribuidor vinculado al usuario
            DistributorEntity distributor = new DistributorEntity();
            distributor.setNIT(Long.valueOf(faker.number().digits(9)));
            distributor.setName(faker.company().name());
            distributor.setPhoneNumber(faker.phoneNumber().cellPhone());
            distributor.setEmail(faker.internet().emailAddress());
            distributor.setDirection(createRandomDirection());
            distributor.setUser(user);
            distributors.add(distributorRepository.save(distributor));
        }
        return distributors;
    }

    /**
     * Crea UN usuario dedicado para UN preventista con rol PRESALES
     * Cada preventista DEBE estar asociado a UN distribuidor
     */
    private List<PresalesEntity> createPresales(List<DistributorEntity> distributors, int count) {
        List<PresalesEntity> presalesList = new ArrayList<>();

        // Primero, asignar al menos un preventista a cada distribuidora
        int minIndex = Math.min(count, distributors.size());
        for (int i = 0; i < minIndex; i++) {
            // Crear usuario específico para este preventista
            UserEntity user = UserEntity.builder()
                    .username(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("password123"))
                    .enabled(true)
                    .roles(Set.of("PRESALES")) // Solo rol PRESALES
                    .build();
            user = userRepository.save(user);

            // Crear preventista vinculado al usuario y al distribuidor
            PresalesEntity presales = new PresalesEntity();
            presales.setName(faker.name().fullName());
            presales.setPhoneNumber(faker.phoneNumber().cellPhone());
            presales.setEmail(faker.internet().emailAddress());
            presales.setDocumentType(getRandomDocumentType());
            presales.setDocumentNumber(Long.valueOf(faker.number().digits(10)));
            presales.setUser(user);
            // Asignar secuencialmente para garantizar que cada distribuidora tenga al menos
            // uno
            presales.setDistributor(distributors.get(i % distributors.size()));
            presalesList.add(presalesRepository.save(presales));
        }

        // Luego, crear el resto de preventistas con distribuidoras aleatorias
        for (int i = minIndex; i < count; i++) {
            // Crear usuario específico para este preventista
            UserEntity user = UserEntity.builder()
                    .username(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("password123"))
                    .enabled(true)
                    .roles(Set.of("PRESALES")) // Solo rol PRESALES
                    .build();
            user = userRepository.save(user);

            // Crear preventista vinculado al usuario y a un distribuidor aleatorio
            PresalesEntity presales = new PresalesEntity();
            presales.setName(faker.name().fullName());
            presales.setPhoneNumber(faker.phoneNumber().cellPhone());
            presales.setEmail(faker.internet().emailAddress());
            presales.setDocumentType(getRandomDocumentType());
            presales.setDocumentNumber(Long.valueOf(faker.number().digits(10)));
            presales.setUser(user);
            presales.setDistributor(distributors.get(random.nextInt(distributors.size())));
            presalesList.add(presalesRepository.save(presales));
        }

        return presalesList;
    }

    /**
     * Crea UN usuario dedicado para UNA tienda con rol STORE
     */
    private List<StoreEntity> createStores(int count) {
        List<StoreEntity> stores = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Crear usuario específico para esta tienda
            UserEntity user = UserEntity.builder()
                    .username(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("password123"))
                    .enabled(true)
                    .roles(Set.of("STORE")) // Solo rol STORE
                    .build();
            user = userRepository.save(user);

            // Crear tienda vinculada al usuario
            StoreEntity store = new StoreEntity();
            store.setNIT(Long.valueOf(faker.number().digits(9)));
            store.setName(faker.company().name() + " Store");
            store.setPhoneNumber(faker.phoneNumber().cellPhone());
            store.setEmail(faker.internet().emailAddress());
            store.setDirection(createRandomDirection());
            store.setUser(user);
            store.setClaimStatus(getRandomClaimStatus());
            stores.add(storeRepository.save(store));
        }
        return stores;
    }

    private void createStoresDistributors(List<StoreEntity> stores, List<DistributorEntity> distributors) {
        for (StoreEntity store : stores) {
            // Cada tienda se asocia con 1-3 distribuidoras
            int numDistributors = random.nextInt(3) + 1;
            for (int i = 0; i < numDistributors; i++) {
                StoresDistributorsEntity sd = new StoresDistributorsEntity();
                sd.setStore(store);
                sd.setDistributor(distributors.get(random.nextInt(distributors.size())));
                sd.setInternalClientCode("CLIENT-" + faker.number().digits(6));
                storesDistributorsRepository.save(sd);
            }
        }
    }

    private List<CatalogEntity> createCatalogs(List<DistributorEntity> distributors) {
        List<CatalogEntity> catalogs = new ArrayList<>();
        for (DistributorEntity distributor : distributors) {
            CatalogEntity catalog = new CatalogEntity();
            catalog.setDistributor(distributor);
            catalogs.add(catalogRepository.save(catalog));
        }
        return catalogs;
    }

    private List<CategoryEntity> createCategories(List<CatalogEntity> catalogs) {
        List<CategoryEntity> categories = new ArrayList<>();
        String[] categoryNames = { "Bebidas", "Snacks", "Lácteos", "Carnes", "Frutas", "Verduras", "Panadería",
                "Limpieza", "Aseo Personal", "Electrónica" };

        for (CatalogEntity catalog : catalogs) {
            for (String categoryName : categoryNames) {
                CategoryEntity category = new CategoryEntity();
                category.setName(categoryName);
                category.setCatalog(catalog);
                categories.add(categoryRepository.save(category));
            }
        }
        return categories;
    }

    private List<ProductEntity> createProducts(List<DistributorEntity> distributors, int count) {
        List<ProductEntity> products = new ArrayList<>();
        ProductEntity.Unit[] units = ProductEntity.Unit.values();

        for (int i = 0; i < count; i++) {
            ProductEntity product = new ProductEntity();
            product.setName(faker.commerce().productName());
            product.setPrice(Double.valueOf(faker.commerce().price().replace(",", "")));
            product.setUnit(units[random.nextInt(units.length)]);
            product.setDistributor(distributors.get(random.nextInt(distributors.size())));
            products.add(productRepository.save(product));
        }
        return products;
    }

    private void linkProductsToCategories(List<ProductEntity> products, List<CategoryEntity> categories) {
        if (categories.isEmpty() || products.isEmpty()) {
            log.warn("No hay categorías o productos para vincular");
            return;
        }

        int linksCreated = 0;
        for (ProductEntity product : products) {
            // Cada producto se asocia con 1-3 categorías aleatorias
            int numCategories = random.nextInt(3) + 1;
            Set<CategoryEntity> selectedCategories = new HashSet<>();

            // Seleccionar categorías únicas
            while (selectedCategories.size() < numCategories && selectedCategories.size() < categories.size()) {
                CategoryEntity category = categories.get(random.nextInt(categories.size()));
                selectedCategories.add(category);
            }

            // Crear las asociaciones
            for (CategoryEntity category : selectedCategories) {
                ProductsCatalogEntity productCatalog = new ProductsCatalogEntity();
                productCatalog.setProduct(product);
                productCatalog.setCategory(category);
                productsCatalogRepository.save(productCatalog);
                linksCreated++;
            }

            // Asignar una categoría principal al producto (la primera del conjunto)
            if (!selectedCategories.isEmpty()) {
                CategoryEntity primaryCategory = selectedCategories.iterator().next();
                // Buscar el ProductsCatalogEntity correspondiente para asignarlo al producto
                List<ProductsCatalogEntity> productCatalogs = productsCatalogRepository.findAll();
                for (ProductsCatalogEntity pc : productCatalogs) {
                    if (pc.getProduct().getId().equals(product.getId()) &&
                            pc.getCategory().getId().equals(primaryCategory.getId())) {
                        product.setCategoryProduct(pc);
                        productRepository.save(product);
                        break;
                    }
                }
            }
        }
        log.info("Creados {} vínculos productos-categorías", linksCreated);
    }

    private void createOrders(List<StoreEntity> stores, List<PresalesEntity> presalesList,
            List<DistributorEntity> distributors, List<ProductEntity> products, int count) {
        OrderEntity.OrderStatus[] statuses = OrderEntity.OrderStatus.values();

        for (int i = 0; i < count; i++) {
            OrderEntity order = new OrderEntity();
            order.setIva_percent(19.0);
            order.setStatus(statuses[random.nextInt(statuses.length)]);
            order.setStore(stores.get(random.nextInt(stores.size())));

            // 70% de las órdenes tienen preventa asignada
            if (random.nextDouble() < 0.7 && !presalesList.isEmpty()) {
                order.setPresales(presalesList.get(random.nextInt(presalesList.size())));
            }

            order.setDistributorId(distributors.get(random.nextInt(distributors.size())).getId());

            // Crear detalles de la orden (2-5 productos por orden)
            // Usar un Set para evitar productos duplicados
            List<OrderDetailEntity> orderDetails = new ArrayList<>();
            Set<ProductEntity> selectedProducts = new HashSet<>();
            int numProducts = random.nextInt(4) + 2;
            double total = 0.0;

            // Seleccionar productos únicos
            while (selectedProducts.size() < numProducts && selectedProducts.size() < products.size()) {
                ProductEntity product = products.get(random.nextInt(products.size()));
                selectedProducts.add(product);
            }

            // Crear detalles de orden para cada producto único
            for (ProductEntity product : selectedProducts) {
                OrderDetailIdEmbedded detailId = new OrderDetailIdEmbedded();
                detailId.setProductId(product.getId());

                OrderDetailEntity detail = new OrderDetailEntity();
                detail.setId(detailId);
                detail.setProduct(product);
                detail.setQuantity(random.nextInt(10) + 1);
                detail.setUnitPrice(product.getPrice());
                detail.setOrder(order);

                total += detail.getQuantity() * detail.getUnitPrice();
                orderDetails.add(detail);
            }

            order.setTotal(total * (1 + order.getIva_percent() / 100));
            order.setOrderDetails(orderDetails);

            orderRepository.save(order);
        }
    }

    // Métodos auxiliares
    private DirectionDto createRandomDirection() {
        return new DirectionDto(
                faker.address().streetAddress(),
                faker.address().city(),
                faker.address().streetName());
    }

    private DocumentTypeEnum getRandomDocumentType() {
        DocumentTypeEnum[] types = DocumentTypeEnum.values();
        return types[random.nextInt(types.length)];
    }

    private StoreClaimStatus getRandomClaimStatus() {
        StoreClaimStatus[] statuses = StoreClaimStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }
}
