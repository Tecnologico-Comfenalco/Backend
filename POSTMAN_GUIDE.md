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

- `Create Store` - Crea una nueva tienda (auto-registro del dueño)
- `List All Stores` - Lista todas las tiendas
- `Get Store by ID` - Obtiene una tienda específica
- `Update Store` - Actualiza información de la tienda
- `Disable Store` - Desactiva una tienda (soft delete)

---

### ✅ Paso 11.1: Registrar Tienda por Distribuidor (Nuevo Flujo)

**Carpeta**: `05 - Stores (ADMIN/DISTRIBUTOR Role)`  
**Request**: `Register Store by Distributor`

Este endpoint permite a un distribuidor registrar una tienda para uno de sus clientes sin que el dueño tenga que registrarse primero.

1. Debes estar autenticado como **DISTRIBUTOR** o **ADMIN**
2. La tienda queda en estado `PENDING_CLAIM` (pendiente de ser reclamada por el dueño)
3. Necesitas el ID del distribuidor (`{{distributorId}}`)

**JSON enviado**:

```json
{
  "NIT": 800567890,
  "name": "Supermercado El Ahorro",
  "phoneNumber": "+57 315 222 3333",
  "email": "contacto@elahorro.com",
  "direction": {
    "street": "Carrera 45",
    "number": "78-90",
    "neighborhood": "Norte",
    "city": "Barranquilla",
    "department": "Atlántico"
  },
  "internalClientCode": "CLI-2024-001"
}
```

**Campos importantes**:

- `NIT`: Identificador único de la tienda (no puede repetirse)
- `internalClientCode`: Código que usa el distribuidor en su sistema ERP para identificar al cliente

**Resultado**:

- Se crea la tienda sin usuario asociado
- Estado: `PENDING_CLAIM`
- Se crea la relación con el distribuidor incluyendo el `internalClientCode`
- El dueño podrá reclamar la tienda más tarde usando su NIT

---

### ✅ Paso 11.2: Reclamar una Tienda (Claim Store)

**Request**: `Claim Store`

Este endpoint es **público** (no requiere autenticación previa) y permite al dueño de una tienda reclamar su negocio que fue previamente registrado por un distribuidor.

**JSON enviado**:

```json
{
  "NIT": 800567890,
  "email": "contacto@elahorro.com",
  "password": "MiPassword123!"
}
```

**Validaciones**:

- El NIT debe existir en el sistema
- La tienda debe estar en estado `PENDING_CLAIM`
- El email debe coincidir con el registrado por el distribuidor
- Se crea automáticamente el usuario con rol `STORE`

**Respuesta exitosa**:

```json
{
  "message": "Store successfully claimed!",
  "storeId": 5,
  "userId": 12,
  "username": "store_800567890"
}
```

**¿Qué pasa después del reclamo?**

1. Se crea un usuario con rol `STORE`
2. El username se genera automáticamente: `store_{NIT}`
3. La tienda cambia a estado `CLAIMED`
4. El dueño puede iniciar sesión con su username y password
5. Ahora el dueño puede gestionar sus pedidos

**Flujo completo del reclamo**:

```
1. Distribuidor registra tienda → Estado: PENDING_CLAIM
2. Dueño recibe notificación (email/WhatsApp)
3. Dueño ejecuta "Claim Store" con NIT, email y password
4. Sistema valida y crea usuario → Estado: CLAIMED
5. Dueño inicia sesión y gestiona su negocio
```

---

### ✅ Paso 11.3: Intentar Auto-Registro de Tienda Existente

Si un dueño intenta registrar una tienda (usando el endpoint normal `Create Store`) pero el NIT ya existe porque un distribuidor la registró primero, recibirá este mensaje:

```json
{
  "error": "A store with this NIT already exists. If you are the owner, please use the 'Claim Store' endpoint to claim your business."
}
```

Este mensaje le indica que debe usar el endpoint de reclamo en lugar del registro normal.

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
2. Debes tener los IDs de la tienda y la distribuidora (obligatorios)
3. El ID del preventista es **opcional** (puede ser `null` si la tienda pide directamente)
4. Ejecuta el request

**JSON enviado**:

```json
{
  "store_id": 1,
  "distributor_id": 1,
  "presales_id": null,
  "productEntities": [
    {
      "id": "UUID-DEL-PRODUCTO",
      "quantity": 10
    }
  ]
}
```

**Campos importantes**:

- `store_id`: ID de la tienda que realiza el pedido (obligatorio)
- `distributor_id`: ID de la distribuidora que atenderá el pedido (obligatorio)
- `presales_id`: ID del preventista que toma el pedido (opcional, puede ser `null`)
- `productEntities`: Lista de productos con sus cantidades

**Flujos de pedido**:

1. **Pedido con preventista** (preventista toma el pedido en campo):

```json
{
  "store_id": 5,
  "distributor_id": 2,
  "presales_id": 3,
  "productEntities": [...]
}
```

→ El sistema valida que el preventista pertenezca a la distribuidora

2. **Pedido directo de tienda** (sin preventista):

```json
{
  "store_id": 5,
  "distributor_id": 2,
  "presales_id": null,
  "productEntities": [...]
}
```

→ La tienda pide directamente a la distribuidora

**Requests disponibles**:

- `Create Order` - Crea un nuevo pedido
- `List All Orders` - Lista todos los pedidos del usuario
- `Get Order by ID` - Obtiene un pedido específico
- `Cancel Order` - Cancela un pedido

**Nota**:

- `distributor_id` es **OBLIGATORIO** - siempre se debe saber qué distribuidora atiende el pedido
- `presales_id` es **OPCIONAL** - puede ser `null` si la tienda hace el pedido directamente
- El `internalClientCode` se obtiene automáticamente de la relación `store + distributor`

---

## 🔑 Roles y Permisos Actualizados

| Endpoint                            | Rol Requerido             |
| ----------------------------------- | ------------------------- |
| `/auth/login`                       | Público                   |
| `/auth/test`                        | Autenticado               |
| `/distributors/*`                   | **ADMIN**                 |
| `/presales/*`                       | **DISTRIBUTOR**           |
| `/deliveries/*`                     | **DISTRIBUTOR**           |
| `/stores/register`                  | **Público** (permitAll)   |
| `/stores/claim`                     | **Público** (permitAll)   |
| `/stores/distributor/{id}/register` | **ADMIN, DISTRIBUTOR**    |
| `/stores/*` (otros endpoints)       | **ADMIN, DISTRIBUTOR**    |
| `/products/*`                       | Cualquier autenticado     |
| `/catalogs` (GET)                   | **DISTRIBUTOR, PRESALES** |
| `/catalogs` (POST/PUT)              | **DISTRIBUTOR**           |
| `/orders/*`                         | **STORE, PRESALES**       |

**Nuevos endpoints públicos**:

- `/stores/register` - Permite que cualquier dueño registre su tienda sin autenticación previa
- `/stores/claim` - Permite que un dueño reclame una tienda registrada por un distribuidor

---

## 🎯 Resumen del Flujo Completo

### Flujo Principal (Completo)

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

### Flujo Alternativo 1: Distribuidor Registra Tienda para su Cliente

```
1. Login como Admin → Guarda adminToken
2. Crear Distribuidor → Guarda distributorId y credenciales
3. Login como Distributor → Guarda distributorToken
4. Registrar Tienda por Distribuidor → Estado: PENDING_CLAIM
   - Incluye internalClientCode para integración ERP
5. [Tiempo después] Dueño reclama la tienda:
   - Usar endpoint público "Claim Store" (sin autenticación)
   - Proporcionar NIT, email y password
   - Sistema crea usuario automáticamente → Estado: CLAIMED
6. Dueño inicia sesión con sus nuevas credenciales
7. Dueño puede ver y gestionar sus pedidos
```

### Flujo Alternativo 2: Auto-Registro vs Reclamo

```
Escenario A (Auto-registro exitoso):
1. Dueño usa "Create Store" con NIT 800111222
2. NIT no existe → Tienda creada con estado: SELF_REGISTERED
3. Dueño puede iniciar sesión inmediatamente

Escenario B (NIT ya existe - debe reclamar):
1. Distribuidor ya registró tienda con NIT 800111222
2. Dueño intenta "Create Store" con mismo NIT
3. Sistema rechaza: "NIT already exists, use Claim Store"
4. Dueño usa "Claim Store" con NIT, email y password
5. Sistema valida email y crea usuario → Estado: CLAIMED
6. Dueño puede iniciar sesión con nuevas credenciales
```

### 📊 Estados de una Tienda (StoreClaimStatus)

| Estado            | Descripción                                                     | ¿Tiene Usuario? |
| ----------------- | --------------------------------------------------------------- | --------------- |
| `SELF_REGISTERED` | Dueño registró la tienda directamente (flujo normal)            | ✅ Sí           |
| `PENDING_CLAIM`   | Distribuidor registró la tienda, esperando que dueño la reclame | ❌ No           |
| `CLAIMED`         | Dueño reclamó una tienda que estaba en PENDING_CLAIM            | ✅ Sí           |

### 🔄 Transiciones de Estado Permitidas

```
SELF_REGISTERED → (Estado final, no cambia)
PENDING_CLAIM → CLAIMED (cuando dueño ejecuta "Claim Store")
CLAIMED → (Estado final, no cambia)
```

---

## � Integración con Sistemas ERP - internalClientCode

### ¿Qué es el internalClientCode?

El `internalClientCode` es un campo que permite a los distribuidores mantener la sincronización con sus sistemas ERP internos. Este código:

- Es definido por el distribuidor al registrar una tienda para su cliente
- Se almacena en la relación `StoresDistributors` (tabla intermedia)
- Se copia automáticamente a cada pedido cuando se crea
- Permite identificar al cliente en el sistema externo del distribuidor

### Flujo del internalClientCode

```
1. Distribuidor registra tienda para cliente
   POST /stores/distributor/{distributorId}/register
   {
     "NIT": 800567890,
     "internalClientCode": "CLI-2024-001",  ← Código del ERP
     ...
   }

2. Sistema guarda el código en StoresDistributors
   - store_id: 5
   - distributor_id: 2
   - internalClientCode: "CLI-2024-001"  ← Guardado aquí

3. Cliente reclama la tienda (o ya estaba registrado)
   - El internalClientCode se mantiene en la relación

4. Se crea un pedido (CON o SIN preventista)
   POST /orders
   {
     "store_id": 5,
     "distributor_id": 2,  ← OBLIGATORIO: Siempre se especifica la distribuidora
     "presales_id": 3,     ← OPCIONAL: Puede ser null si tienda pide directamente
     ...
   }

5. Sistema busca la relación StoresDistributors
   - Busca por: store_id=5, distributor_id=2 (ya no depende del preventista)
   - Si NO existe la relación: La crea automáticamente (internalClientCode=null)
   - Si existe la relación: Extrae el internalClientCode

6. Sistema guarda el pedido con el código
   OrderEntity {
     id: UUID,
     store: Store(5),
     presales: Presales(3) o null,  ← Puede ser null
     internalClientCode: "CLI-2024-001" o null,  ← Null si relación recién creada
     ...
   }

**Nota importante**: Si una tienda auto-registrada hace su primer pedido con un
distribuidor, el sistema crea automáticamente la relación StoresDistributors sin
código interno. El distribuidor puede asignar el código después, y los siguientes
pedidos lo incluirán.
```

### Respuesta del Endpoint de Pedidos

Cuando consultas un pedido, el `internalClientCode` viene incluido:

```json
{
  "order": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "iva_percent": 19.0,
    "total": 65450.0,
    "status": "PENDING",
    "store": { ... },
    "presales": { ... },
    "orderDetails": [ ... ],
    "internalClientCode": "CLI-2024-001"  ← Disponible para sincronización
  },
  "message": "Order successfully obtained!"
}
```

### Casos de Uso

**Caso 1: Tienda registrada por distribuidor - Pedido con preventista**

```json
POST /orders
{
  "store_id": 5,
  "distributor_id": 2,
  "presales_id": 3,
  "productEntities": [...]
}
```

- Distribuidor asignó código: `"CLI-2024-001"`
- El pedido incluirá ese código automáticamente
- El preventista pertenece a esa distribuidora

**Caso 2: Tienda registrada por distribuidor - Pedido directo (sin preventista)**

```json
POST /orders
{
  "store_id": 5,
  "distributor_id": 2,
  "presales_id": null,
  "productEntities": [...]
}
```

- Tienda pide directamente a la distribuidora (sin preventista)
- El código `"CLI-2024-001"` se obtiene de la relación store + distributor
- `internalClientCode` incluido en el pedido

**Caso 3: Tienda auto-registrada hace su primer pedido**

```json
POST /orders
{
  "store_id": 8,
  "distributor_id": 2,
  "presales_id": null,
  "productEntities": [...]
}
```

- **Comportamiento automático mejorado**:
  1. No hay relación previa StoresDistributors
  2. El sistema **crea automáticamente** la relación al crear el pedido
  3. `internalClientCode` será `null` en este primer pedido (relación nueva)
  4. El distribuidor puede **actualizar después** la relación para asignar el código
  5. Los siguientes pedidos de esta tienda con ese distribuidor usarán el código asignado

**Flujo de asignación del código**:

```
Pedido 1: internalClientCode = null (relación creada automáticamente)
    ↓
Distribuidor asigna código "CLI-2024-999" a la relación
    ↓
Pedido 2: internalClientCode = "CLI-2024-999" ✅
Pedido 3: internalClientCode = "CLI-2024-999" ✅
```

**Caso 4: Tienda trabaja con múltiples distribuidores**

- Distribuidor A: relación con código `"CLI-A-001"`
- Distribuidor B: relación con código `"CLI-B-500"`
- Pedidos a distribuidor A incluirán `"CLI-A-001"`
- Pedidos a distribuidor B incluirán `"CLI-B-500"`
- El código correcto se obtiene según el `distributor_id` del pedido

### Ventajas para la Integración

✅ **Trazabilidad**: Cada pedido sabe a qué cliente del ERP corresponde  
✅ **Sincronización**: Fácil mapeo entre sistema interno y plataforma B2B  
✅ **Multi-distribuidor**: Cada distribuidor mantiene sus propios códigos  
✅ **Auditoría**: Histórico completo de qué código se usó en cada pedido

---

## �🎉 ¡Listo!

Ahora tienes una colección completa y automatizada para probar tu API. Los scripts se encargan de:

- Guardar tokens automáticamente
- Almacenar IDs de recursos
- Mostrar información importante en la consola

**No necesitas copiar/pegar tokens ni IDs manualmente** - todo está automatizado. 🚀
