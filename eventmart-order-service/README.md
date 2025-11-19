# EventMart Order Service

Order management microservice for EventMart platform.

## Description

This service handles all order-related operations including:
- Order creation and management
- Order processing
- Order history and tracking

## Port

This service runs on port `8083`

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
http://localhost:8083/actuator/health
```
