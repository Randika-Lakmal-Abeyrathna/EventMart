# 📦 eventmart-product-service

Product Service for EventMart, responsible for managing product catalog, product details, and search capabilities.

This service is built using Spring Boot, follows event-driven microservice principles, and supports both local and Docker-based development.

## 🧱 Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** (Database migrations)
- **Docker & Docker Compose**
- **Testcontainers** (Integration testing)
- **Lombok** (Boilerplate reduction)
- **MapStruct** (Object mapping)
- **SpringDoc OpenAPI** (API documentation)

## 🚀 Getting Started

You can run this service in two ways:

1. **Using Docker** (recommended)
2. **Running locally via IDE**

---

## 🐳 Option 1: Run with Docker (Recommended)

This approach provides a fully isolated environment and is the preferred way for development and integration testing.

### 1️⃣ Prerequisites

- **Docker**
- **Docker Compose**

### 2️⃣ Environment Configuration

Create a `.env` file in the service root:

```bash
cp .env.example .env
```

Update `.env` with your values:

```properties
SPRING_PROFILES_ACTIVE=docker

DB_URL=jdbc:postgresql://postgres:5432/product_db
DB_USERNAME=Dev
DB_PASSWORD=Dev@123
```

⚠️ **Note:** `.env` is not committed to version control.

### 3️⃣ Docker Compose Configuration

Docker Compose file location:

```
docker/docker-compose.dev.yml
```

This configuration:
- Starts PostgreSQL
- Builds and runs the product service
- Persists DB data using Docker volumes

### 4️⃣ Build & Run

From the service root directory:

```bash
docker compose -f docker/docker-compose.dev.yml up --build
```

Once started:
- **API available at:** `http://localhost:8080`
- **PostgreSQL exposed at:** `localhost:5433` (mapped from container port 5432)
- **API documentation:** `http://localhost:8080/swagger-ui.html`

### 5️⃣ Stopping the Service

```bash
docker compose -f docker/docker-compose.dev.yml down
```

To remove DB data:

```bash
docker volume rm eventmart-product-service_postgres_data
```

---

## 💻 Option 2: Run Locally (IDE / JVM)

This mode is useful for:
- Debugging
- Faster development iterations
- Running without Docker

### 1️⃣ Database Setup

You must have **PostgreSQL** running locally.

Create database:

```sql
CREATE DATABASE product_db;
```

### 2️⃣ Environment Variables

**Option A:** Create `application-local.properties`

Copy the example file:

```bash
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
```

Update with your credentials:

```properties
DB_URL=jdbc:postgresql://localhost:5432/product_db
DB_USERNAME=Dev
DB_PASSWORD=Dev@123
```

**Option B:** Set environment variables in your IDE run configuration:

```
SPRING_PROFILES_ACTIVE=local
DB_URL=jdbc:postgresql://localhost:5432/product_db
DB_USERNAME=Dev
DB_PASSWORD=Dev@123
```

💡 **Note:** IntelliJ does not automatically load `.env` files.

### 3️⃣ Run the Application

Run the main class:

```
EventmartProductServiceApplication
```

The service will start at:

```
http://localhost:8080
```

---

## 🧪 Testing

### Context Load Test

A lightweight smoke test verifies that the Spring context starts correctly:

```java
@SpringBootTest
class EventmartProductServiceApplicationTests {
    @Test
    void contextLoads() {}
}
```

### Running Tests

```bash
mvn test
```

Database-dependent tests use:
- **Testcontainers** (for integration tests with real PostgreSQL)
- **H2** (for fast unit tests, if configured)

---

## 🗄️ Database Migrations (Flyway)

Migrations located in:

```
src/main/resources/db/migration
```

- Flyway runs automatically on startup
- Hibernate schema generation is **disabled** (DDL auto: none)
- Migration files follow naming convention: `V{version}__{description}.sql`

Example migration files:
- `V1__create_product_tables.sql`
- `V2__updated_product_with_brand.sql`
- `V3__add_product_code_to_product_table.sql`

---

## 📁 Configuration Overview

| File | Purpose |
|------|---------|
| `application.yml` | Common/shared configuration |
| `application-docker.yml` | Docker-specific configuration |
| `application-local.properties.example` | Template for local DB credentials |
| `.env` | Runtime secrets for Docker (not committed) |
| `docker-compose.dev.yml` | Local integration environment |

---

## 🔐 Security & Secrets

- **No secrets are committed to Git**
- `.env` and `application-local.properties` are ignored in `.gitignore`
- Credentials are injected via environment variables
- Use strong passwords in production environments

---

## 🧠 Development Notes

- **Docker service names** (e.g., `postgres`) are only resolvable inside Docker network
- Use `localhost` when connecting from host machine or IDE
- Flyway manages DB schema (Hibernate DDL is disabled)
- The PostgreSQL port is mapped to `5433` on the host to avoid conflicts with local PostgreSQL instances

---

## 📌 Common Issues

### ❌ DB connection fails locally

**Problem:** Cannot connect to PostgreSQL from IDE

**Solution:**
- Ensure PostgreSQL is running on `localhost:5432`
- Verify environment variables are set in IDE run configuration
- Check credentials in `application-local.properties`

### ❌ Docker service cannot connect to DB

**Problem:** Application fails to connect to database in Docker

**Solution:**
- Use `postgres` as hostname in Docker profile (not `localhost`)
- Ensure both services are on the same Docker network
- Check `docker-compose.dev.yml` configuration

### ❌ Port already in use

**Problem:** `Address already in use: bind`

**Solution:**
- Stop any existing service running on port 8080 or 5433
- Or modify port mappings in `docker-compose.dev.yml`

---

## 🏁 Summary

| Mode | Use Case |
|------|----------|
| **Docker** | Integration testing, team consistency, isolated environment |
| **Local** | Debugging, rapid development, IDE support |

---

## 📖 Related Documentation

- [Root Project README](../../README.md)
- [Branching Strategy](../../docs/branching-strategy.md)
- [Pull Request Template](../../docs/pull_request_template.md)

---

## 🤝 Contributing

When contributing to this service:

1. Follow the [Branching Strategy](../../docs/branching-strategy.md)
2. Use the [Pull Request Template](../../docs/pull_request_template.md)
3. Ensure all tests pass locally before submitting PR
4. Update documentation if adding new features
5. Follow conventional commit messages: `feat(product): description`

---

## 📝 API Documentation

Once the service is running, access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

This provides a complete overview of all available endpoints, request/response schemas, and allows you to test APIs directly from the browser.
