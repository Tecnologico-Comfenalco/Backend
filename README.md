# Proyecto de Aula - Backend

Este es el backend de un proyecto universitario del cuarto semestre desarrollado en Spring Boot, diseñado para gestionar un sistema de distribución con múltiples entidades de negocio.

## 🏗️ Arquitectura del Proyecto

### Estructura Modular

El proyecto está organizado por **features/dominios de negocio** bajo `src/main/java/com/tecno_comfenalco/pa/features/`:

- **catalogos** - Gestión de catálogos de productos
- **distribuidoras** - Manejo de empresas distribuidoras
- **entregadores** - Gestión de personal de entrega
- **pedidos** - Sistema de gestión de pedidos
- **productos** - Administración de productos
- **proveedores** - Manejo de proveedores
- **rutas** - Gestión de rutas de entrega
- **tiendas** - Administración de tiendas
- **security** - Autenticación y autorización

### Patrón de Capas

Cada feature sigue la arquitectura estándar de Spring Boot:

```
Controller → Service → Repository → Entity/DTO
```

### Base de Datos Dual

El proyecto soporta **PostgreSQL** y **MongoDB** mediante:

- Repositorios separados por tecnología (`postgres/` y `mongo/`)
- Configuraciones específicas (`application-postgres.yml`, `application-mongo.yml`)
- Uso de perfiles Spring (`@Profile("postgres")`, `@Profile("mongo")`)

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 17+
- PostgreSQL 12+ o MongoDB 4.4+
- Maven 3.6+

### Configuración de Base de Datos

1. **Configurar variables de entorno:**

```bash
# PostgreSQL
export DB_URL="jdbc:postgresql://localhost:5432/db_pa"
export DB_USERNAME="postgres"
export DB_PASSWORD="tu_password"

# MongoDB
export MONGO_URI="mongodb://localhost:27017/db_pa"
```

2. **Activar perfil de base de datos:**
   En `application.yml`, configura:

```yaml
spring:
  profiles:
    active:
      - postgres # o 'mongo'
```

### Ejecutar la Aplicación

```bash
# Construir el proyecto
.\mvnw.cmd clean install

# Ejecutar la aplicación
.\mvnw.cmd spring-boot:run

# Ejecutar tests
.\mvnw.cmd test
```

La aplicación estará disponible en `http://localhost:8080/api`

## 📁 Estructura de Directorios

```
src/
├── main/
│   ├── java/com/tecno_comfenalco/pa/
│   │   ├── features/           # Módulos de negocio
│   │   │   ├── catalogos/
│   │   │   ├── distribuidoras/
│   │   │   ├── entregadores/
│   │   │   ├── pedidos/
│   │   │   ├── productos/
│   │   │   ├── proveedores/
│   │   │   ├── rutas/
│   │   │   └── tiendas/
│   │   ├── security/           # Autenticación y autorización
|   |   |-- shared/             # Configuraciones, DTO´s y utlidades compartidas
│   │   └── ProyectoDeAulaApplication.java
│   └── resources/
│       ├── application.yml               # Config principal
│       ├── application.example.yml       # Plantilla de configuración
│       ├── application-postgres.yml      # Config PostgreSQL
│       └── application-mongo.yml         # Config MongoDB
└── test/                      # Tests unitarios
```

## 🔧 Configuración

### Variables de Entorno

Para mayor seguridad, usa variables de entorno para datos sensibles:

```yaml
# application.yml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

### Perfiles de Spring

- `postgres` - Para usar PostgreSQL
- `mongo` - Para usar MongoDB

## 🔐 Autenticación

El sistema incluye autenticación basada en Spring Security con:

- Endpoint de login: `POST /api/auth/login`
- DTOs específicos para requests/responses
- Servicio de UserDetails personalizado
- Configuración de seguridad sin estado (stateless)

## 🛠️ Tecnologías

- **Framework:** Spring Boot 3.5.5
- **Java:** 17
- **Bases de Datos:** PostgreSQL, MongoDB
- **ORM:** Hibernate/JPA, Spring Data MongoDB
- **Seguridad:** Spring Security
- **Build:** Maven
- **Utilidades:** Lombok

## 📝 Desarrollo

### Agregar Nueva Feature

1. Crear directorio bajo `features/`
2. Implementar estructura: `repository/`, `service/`, `dto/`, controladores, entidades
3. Crear repositorios para ambas BD si es necesario
4. Añadir configuración específica si se requiere

### Convenciones

- Usar records para DTOs
- Implementar repositorios base con `@NoRepositoryBean`
- Separar implementaciones por base de datos con `@Profile`
- Mantener configuración en archivos YAML separados

## 📄 Licencia

Proyecto académico - Tecnológico Comfenalco

---

Para más información sobre convenciones y patrones específicos del proyecto, consulta [`.github/copilot-instructions.md`](.github/copilot-instructions.md).
