# Copilot Instructions for AI Coding Agents

## Project Overview

This is a Spring Boot backend for a university project, organized by business domains under `src/main/java/com/tecno_comfenalco/pa/`. Each domain (e.g., `catalogos`, `distribuidoras`, `entregadores`, `pedidos`, `productos`, `proveedores`, `rutas`, `tiendas`) is further split into `features`, with subfolders for repositories (MongoDB and PostgreSQL), services, controllers, entities, and DTOs. The main entry point is `ProyectoDeAulaApplication.java`.

## Architecture & Data Flow

- Follows standard Spring Boot layered architecture: Controller → Service → Repository.
- Each domain/feature folder contains its own models, controllers, services, and DTOs. Repositories are split by database type (`mongo` and `postgres`).
- Database configuration is managed via `application.yml` in `src/main/resources`. Use environment variables for sensitive data (see below).
- Supports both PostgreSQL and MongoDB, with separate configuration files (`application-postgres.yml`, `application-mongo.yml`) and repository interfaces for each DB.

## Developer Workflows

- **Build:** Use Maven wrapper scripts (`mvnw`/`mvnw.cmd`). Example: `./mvnw clean install` (Linux/macOS) or `mvnw.cmd clean install` (Windows).
- **Run:** Main class is `ProyectoDeAulaApplication`. Use Maven or your IDE to run.
- **Test:** Tests are under `src/test/java/com/tecno_comfenalco/pa/`. Run with Maven: `mvnw.cmd test`.
- **Debug:** Standard Spring Boot debugging applies. Use IDE breakpoints or add logging as needed.

## Project-Specific Conventions

- **Environment Variables:** Database credentials and URLs should be set via environment variables (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`). Reference them in `application.yml` using `${VAR_NAME}` syntax.
- **Package Structure:** Each business domain is a separate package. Keep controllers, services, and repositories grouped by domain.
- **Configuration:** All configuration is centralized in `application.yml`. Avoid hardcoding sensitive values.

## Integration Points

- **Database:** PostgreSQL (JPA/Hibernate) and MongoDB (Spring Data Mongo), configured via separate YAML files and repository interfaces.
- **External Libraries:** Standard Spring Boot dependencies, Hibernate for JPA, Spring Data MongoDB.

## Examples

- Referencing environment variables in `application.yml`:
  ```yaml
  spring:
    datasource:
      url: ${DB_URL}
      username: ${DB_USERNAME}
      password: ${DB_PASSWORD}
  ```
- Running tests:
  ```powershell
  .\mvnw.cmd test
  ```

## Key Files & Directories

- `src/main/java/com/tecno_comfenalco/pa/` — Main source code, organized by domain
- `src/main/resources/application.yml` — Central configuration
- `src/main/resources/application-postgres.yml` — PostgreSQL config
- `src/main/resources/application-mongo.yml` — MongoDB config
- `src/main/java/com/tecno_comfenalco/pa/features/` — Feature modules, each with subfolders for repository, service, controller, entity, dto
- `src/main/java/com/tecno_comfenalco/pa/security/` — Security config, DTOs, and user domain
- `mvnw`, `mvnw.cmd` — Maven wrapper scripts
- `pom.xml` — Maven project descriptor

---

If any conventions or workflows are unclear, please ask for clarification or provide feedback to improve these instructions.
