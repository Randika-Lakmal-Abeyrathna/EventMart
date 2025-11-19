# EventMart

Event driven architecture and microservices platform

## Overview

EventMart is a microservices-based event management platform built with Spring Boot and Spring Cloud. This is a monorepo containing all microservices for the EventMart platform.

## Microservices

This repository contains the following microservices:

1. **eventmart-product-service** (Port: 8081)
   - Product catalog and management

2. **eventmart-inventory-service** (Port: 8082)
   - Inventory and stock management

3. **eventmart-order-service** (Port: 8083)
   - Order processing and management

4. **eventmart-gateway** (Port: 8080)
   - API Gateway for routing requests

5. **eventmart-auth-service** (Port: 8084)
   - Authentication and authorization

## Architecture

This project follows a microservices architecture with:
- Spring Boot for individual services
- Spring Cloud Gateway for API routing
- Event-driven communication between services
- Independent deployment capabilities

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Project

To build all microservices:

```bash
mvn clean install
```

To build a specific microservice:

```bash
cd eventmart-<service-name>
mvn clean install
```

## Running Services

Each microservice can be run independently:

```bash
cd eventmart-<service-name>
mvn spring-boot:run
```

## Project Structure

```
EventMart/
├── eventmart-product-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── eventmart-inventory-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── eventmart-order-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── eventmart-gateway/
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── eventmart-auth-service/
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── pom.xml (parent POM)
└── README.md
```

## Contributing

Please read individual service README files for specific service details.
