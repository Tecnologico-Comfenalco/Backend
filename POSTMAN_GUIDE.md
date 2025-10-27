# 🚀 Guía de Pruebas con Postman - Backend Proyecto Aula

## 📦 Importar la Colección

1. Abre **Postman**
2. Haz clic en **Import** (esquina superior izquierda)
3. Selecciona el archivo `postman-collection.json` que se encuentra en la raíz del proyecto Backend
4. La colección "Backend Proyecto Aula - API Tests" se agregará automáticamente

---

## 🔧 Configuración Inicial

### Variables de la Colección

La colección incluye las siguientes variables (se actualizan automáticamente):

- `baseUrl`: http://localhost:8080/api
- `adminToken`: Token JWT del admin (se guarda automáticamente)
- `distributorToken`: Token JWT del distribuidor (se guarda automáticamente)
- `distributorId`: ID del distribuidor creado
- `presalesId`: ID del preventista creado
- `deliveryId`: ID del entregador creado

**No necesitas configurar nada manualmente** - los scripts automáticos guardan los valores.

---

## 📋 Flujo de Pruebas Completo

### ✅ Paso 1: Autenticación como Admin

**Carpeta**: `01 - Authentication`  
**Request**: `Login as Admin`

1. Ejecuta el request (sin modificar nada)
2. ✅ El token se guarda automáticamente en `{{adminToken}}`
3. Verifica en la consola de Postman: "✅ Admin token guardado"

**Credenciales**:

```json
{
  "username": "admin",
  "password": "password",
  "rememberMe": false
}
```

---

### ✅ Paso 2: Crear un Distribuidor

**Carpeta**: `02 - Distributors (ADMIN Role)`  
**Request**: `Create Distributor`

1. Asegúrate de que `{{adminToken}}` está configurado (Paso 1)
2. Ejecuta el request
3. **IMPORTANTE**: En la consola de Postman, aparecerán las credenciales del distribuidor:
   ```
   📝 IMPORTANTE - Guarda estas credenciales del distribuidor:
      Username: [username_generado]
      Password: [password_generado]
   ```
4. **COPIA estas credenciales** - las necesitarás en el siguiente paso

**JSON enviado**:

```json
{
  "NIT": 900123456,
  "name": "Distribuidora Central",
  "phoneNumber": "+57 300 123 4567",
  "email": "contacto@distcentral.com",
  "direction": {
    "street": "Calle 50",
    "number": "23-45",
    "neighborhood": "Centro",
    "city": "Barranquilla",
    "department": "Atlántico"
  }
}
```

---

### ✅ Paso 3: Listar Distribuidores

**Request**: `List All Distributors`

- Ejecuta para ver todos los distribuidores registrados
- Verifica que el que creaste aparece en la lista

---

### ✅ Paso 4: Obtener Distribuidor por ID

**Request**: `Get Distributor by ID`

- Usa la variable `{{distributorId}}` que se guardó automáticamente
- Muestra la información detallada del distribuidor

---

### ✅ Paso 5: Actualizar Distribuidor

**Request**: `Update Distributor`

- Modifica la información del distribuidor
- Prueba cambiar el nombre, teléfono, email o dirección

---

### 🔄 Paso 6: Cambiar a Usuario Distribuidor

**Request**: `Login as Distributor`

1. **ACTUALIZA** el request con las credenciales que guardaste en el Paso 2:
   ```json
   {
     "username": "AQUÍ_TU_USERNAME_DEL_DISTRIBUIDOR",
     "password": "AQUÍ_TU_PASSWORD_DEL_DISTRIBUIDOR",
     "rememberMe": false
   }
   ```
2. Ejecuta el request
3. ✅ El token del distribuidor se guarda automáticamente en `{{distributorToken}}`

---

### ✅ Paso 7: Crear un Preventista (Presales)

**Carpeta**: `03 - Presales (DISTRIBUTOR Role)`  
**Request**: `Create Presales`

1. Asegúrate de estar autenticado como distribuidor (Paso 6)
2. Ejecuta el request
3. Las credenciales del preventista aparecerán en la consola

**JSON enviado**:

```json
{
  "name": "Juan Pérez",
  "phoneNumber": "+57 310 987 6543",
  "email": "juan.perez@distcentral.com",
  "documentType": "CC",
  "documentNumber": 1234567890
}
```

**Tipos de documento**: `CC`, `TI`, `CE`, `PASSPORT`

---

### ✅ Paso 8: Gestionar Preventistas

**Requests disponibles**:

- `List All Presales` - Lista todos los preventistas
- `Get Presales by ID` - Obtiene un preventista específico
- `Update Presales` - Actualiza información del preventista

---

### ✅ Paso 9: Crear un Entregador (Delivery)

**Carpeta**: `04 - Deliveries (DISTRIBUTOR Role)`  
**Request**: `Create Delivery`

1. Debes estar autenticado como distribuidor
2. Ejecuta el request
3. Las credenciales del entregador aparecerán en la consola

**JSON enviado**:

```json
{
  "name": "Carlos Rodríguez",
  "documentType": "CC",
  "documentNumber": 9876543210,
  "phoneNumber": "+57 320 456 7890",
  "licenseNumber": "LIC123456789",
  "licenseType": "B1"
}
```

**Tipos de licencia**: `A1`, `A2`, `A3`, `A4`, `B1`, `B2`, `B3`, `C1`, `C2`, `C3`, `C4`, `D1`, `D2`, `D3`, `D4`

---

### ✅ Paso 10: Gestionar Entregadores

**Requests disponibles**:

- `List All Deliveries` - Lista todos los entregadores
- `Get Delivery by ID` - Obtiene un entregador específico
- `Update Delivery` - Actualiza información del entregador

---

## 🎯 Resumen del Flujo

```
1. Login como Admin → Guarda adminToken
2. Crear Distribuidor → Guarda distributorId y credenciales
3. Listar/Ver/Actualizar Distribuidores (con adminToken)
4. Login como Distribuidor → Guarda distributorToken
5. Crear Preventista → Guarda presalesId
6. Gestionar Preventistas (con distributorToken)
7. Crear Entregador → Guarda deliveryId
8. Gestionar Entregadores (con distributorToken)
```

---

## 🔑 Roles y Permisos

| Endpoint          | Rol Requerido   |
| ----------------- | --------------- |
| `/auth/login`     | Público         |
| `/auth/test`      | Autenticado     |
| `/distributors/*` | **ADMIN**       |
| `/presales/*`     | **DISTRIBUTOR** |
| `/deliveries/*`   | **DISTRIBUTOR** |

---

## 💡 Scripts Automáticos

La colección incluye **scripts de prueba** que:

- ✅ Guardan automáticamente los tokens JWT
- ✅ Almacenan los IDs de recursos creados
- ✅ Muestran información importante en la consola
- ✅ Facilitan el flujo de pruebas sin copiar/pegar manualmente

---

## 🐛 Solución de Problemas

### Error 401 Unauthorized

- Verifica que el token esté guardado en las variables de colección
- Vuelve a ejecutar el login correspondiente

### Error 403 Forbidden

- Estás usando el token incorrecto para ese endpoint
- Los endpoints de `/distributors` requieren `adminToken`
- Los endpoints de `/presales` y `/deliveries` requieren `distributorToken`

### Error 404 Not Found

- Verifica que el servidor esté corriendo en `http://localhost:8080`
- Asegúrate de haber creado el recurso antes de intentar accederlo

### Variables no se guardan

- Abre la consola de Postman (View → Show Postman Console)
- Verifica los mensajes de confirmación
- Revisa que los scripts de prueba se ejecuten correctamente

---

## 📊 Otros Endpoints Disponibles

También tienes estos controladores que puedes explorar:

- `/stores` - Gestión de tiendas ✅ **INCLUIDO EN LA COLECCIÓN**
- `/orders` - Gestión de pedidos ✅ **INCLUIDO EN LA COLECCIÓN**
- `/catalogs` - Gestión de catálogos ✅ **INCLUIDO EN LA COLECCIÓN**
- `/vehicles` - Gestión de vehículos
- `/products` - Gestión de productos ✅ **INCLUIDO EN LA COLECCIÓN**
- `/routes` - Gestión de rutas

---

## 🆕 Nuevos Endpoints Agregados

### ✅ Paso 11: Crear una Tienda (Store)

**Carpeta**: `05 - Stores (ADMIN/DISTRIBUTOR Role)`  
**Request**: `Create Store`

1. Debes estar autenticado como distribuidor o admin
2. Ejecuta el request
3. Las credenciales de la tienda aparecerán en la consola

**JSON enviado**:

```json
{
  "NIT": 800234567,
  "name": "Tienda La Esquina",
  "phoneNumber": "+57 305 111 2222",
  "email": "contacto@laesquina.com",
  "direction": {
    "street": "Calle 80",
    "number": "45-67",
    "neighborhood": "El Prado",
    "city": "Barranquilla",
    "department": "Atlántico"
  }
}
```

**Requests disponibles**:

- `Create Store` - Crea una nueva tienda
- `List All Stores` - Lista todas las tiendas
- `Get Store by ID` - Obtiene una tienda específica
- `Update Store` - Actualiza información de la tienda
- `Disable Store` - Desactiva una tienda (soft delete)

---

### ✅ Paso 12: Crear un Producto

**Carpeta**: `06 - Products`  
**Request**: `Create Product`

1. Debes estar autenticado como distribuidor
2. Ejecuta el request
3. El ID del producto se guarda automáticamente

**JSON enviado**:

```json
{
  "name": "Coca Cola 2L",
  "catalog_id": 1,
  "price": 5500.0,
  "unit": "LITER"
}
```

**Unidades disponibles**: `UNIT`, `KILOGRAM`, `LITER`, `METER`, `PACK`, `BOX`

**Requests disponibles**:

- `Create Product` - Crea un nuevo producto
- `List All Products` - Lista todos los productos
- `Get Product by ID` - Obtiene un producto específico
- `Update Product` - Actualiza información del producto
- `Disable Product` - Desactiva un producto (soft delete)

---

### ✅ Paso 13: Gestionar Catálogo

**Carpeta**: `07 - Catalogs (DISTRIBUTOR/PRESALES Role)`

**Workflow del catálogo**:

1. **Get My Catalog** - Ver el catálogo de tu distribuidora
2. **Add Category to Catalog** - Agregar una categoría (ej: "Bebidas")
3. **Add Product to Category** - Agregar productos a la categoría
4. **Get Products by Category** - Ver productos de una categoría específica

**Ejemplo - Agregar Categoría**:

```json
{
  "name": "Bebidas"
}
```

**Ejemplo - Agregar Producto a Categoría**:

```json
{
  "productId": "UUID-DEL-PRODUCTO-AQUI"
}
```

**Nota**: Los preventistas (PRESALES) pueden VER el catálogo pero solo los distribuidores (DISTRIBUTOR) pueden modificarlo.

---

### ✅ Paso 14: Crear un Pedido (Order)

**Carpeta**: `08 - Orders (STORE/PRESALES Role)`  
**Request**: `Create Order`

1. Necesitas estar autenticado como STORE o PRESALES
2. Debes tener los IDs de la tienda y del preventista
3. Ejecuta el request

**JSON enviado**:

```json
{
  "store_id": 1,
  "presales_id": 1,
  "productEntities": [
    {
      "id": "UUID-DEL-PRODUCTO",
      "quantity": 10
    }
  ]
}
```

**Requests disponibles**:

- `Create Order` - Crea un nuevo pedido
- `List All Orders` - Lista todos los pedidos del usuario
- `Get Order by ID` - Obtiene un pedido específico
- `Cancel Order` - Cancela un pedido

**Nota**: Actualiza `store_id` y `presales_id` con los IDs reales que obtuviste al crear la tienda y el preventista.

---

## 🔑 Roles y Permisos Actualizados

| Endpoint               | Rol Requerido             |
| ---------------------- | ------------------------- |
| `/auth/login`          | Público                   |
| `/auth/test`           | Autenticado               |
| `/distributors/*`      | **ADMIN**                 |
| `/presales/*`          | **DISTRIBUTOR**           |
| `/deliveries/*`        | **DISTRIBUTOR**           |
| `/stores/*`            | **ADMIN, DISTRIBUTOR**    |
| `/products/*`          | Cualquier autenticado     |
| `/catalogs` (GET)      | **DISTRIBUTOR, PRESALES** |
| `/catalogs` (POST/PUT) | **DISTRIBUTOR**           |
| `/orders/*`            | **STORE, PRESALES**       |

---

## 🎯 Resumen del Flujo Completo

```
1. Login como Admin → Guarda adminToken
2. Crear Distribuidor → Guarda distributorId y credenciales
3. Login como Distributor → Guarda distributorToken
4. Crear Preventista → Guarda presalesId
5. Crear Entregador → Guarda deliveryId
6. Crear Tienda → Guarda storeId y credenciales
7. Crear Producto → Guarda productId
8. Agregar Categoría al Catálogo → Guarda categoryId
9. Agregar Producto a Categoría
10. Login como Presales → Guarda presalesToken
11. Crear Pedido con productos
```

---

## 🎉 ¡Listo!

Ahora tienes una colección completa y automatizada para probar tu API. Los scripts se encargan de:

- Guardar tokens automáticamente
- Almacenar IDs de recursos
- Mostrar información importante en la consola

**No necesitas copiar/pegar tokens ni IDs manualmente** - todo está automatizado. 🚀
