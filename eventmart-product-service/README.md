# EventMart Product Service

Product management microservice for EventMart platform.

## Description

This service handles all product-related operations including:
- Product catalog management
- Product information and metadata
- Product search and filtering

## Port

This service runs on port `8081`

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

## Health Check

```
http://localhost:8081/actuator/health
```
