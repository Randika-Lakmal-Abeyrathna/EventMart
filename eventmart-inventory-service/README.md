# EventMart Inventory Service

Inventory management microservice for EventMart platform.

## Description

This service handles all inventory-related operations including:
- Stock management
- Inventory tracking
- Availability checks

## Port

This service runs on port `8082`

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
http://localhost:8082/actuator/health
```
