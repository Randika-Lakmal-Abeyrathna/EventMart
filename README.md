# EventMart
Event driven architecture and microservices

## Overview
EventMart is a monorepo containing multiple microservices that work together to provide a comprehensive event management platform. The architecture follows event-driven principles and implements a microservices pattern for scalability and maintainability.

## Services

### eventmart-product-service
Handles all product-related operations including catalog management, product information, and search functionality.

### eventmart-inventory-service
Manages inventory and stock levels, warehouse operations, and stock alerts.

### eventmart-order-service
Handles order processing, order status tracking, and payment integration coordination.

### eventmart-gateway
API Gateway service that acts as the main entry point for all client requests, providing routing, load balancing, and security.

### eventmart-auth-service
Authentication and authorization service managing user authentication, token validation, and access control.

## Getting Started
Each service has its own README with specific instructions. Navigate to the respective service directory for more details.

## Architecture
This is a monorepo structure where all services are maintained in a single repository. Each service is independently deployable and can be developed, tested, and deployed separately.
