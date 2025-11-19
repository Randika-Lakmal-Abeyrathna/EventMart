# EventMart Gateway

API Gateway for EventMart microservices platform.

## Description

This service acts as the API Gateway providing:
- Single entry point for all client requests
- Request routing to appropriate microservices
- Load balancing
- Security and authentication integration

## Port

This service runs on port `8080`

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
http://localhost:8080/actuator/health
```
