# EventMart Auth Service

Authentication and authorization microservice for EventMart platform.

## Description

This service handles all authentication and authorization operations including:
- User authentication
- Token management (JWT)
- Authorization and access control
- User session management

## Port

This service runs on port `8084`

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
http://localhost:8084/actuator/health
```
