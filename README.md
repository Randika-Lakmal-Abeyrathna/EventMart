# EventMart
Event driven architecture and microservices

## Overview
EventMart is a monorepo containing multiple microservices that work together to provide a comprehensive event management platform. The architecture follows event-driven principles and implements a microservices pattern for scalability and maintainability.

> **⚠️ Important**: This repository uses the `develop` branch for active development. Please read [BRANCHING_STRATEGY.md](./BRANCHING_STRATEGY.md) for details on our branching strategy and development workflow.

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

### For Contributors
1. Read our [Branching Strategy](./BRANCHING_STRATEGY.md) to understand the development workflow
2. Clone the repository (will checkout `develop` branch by default once configured)
3. Create feature branches from `develop`
4. Submit pull requests targeting `develop`

### For Development
Each service has its own README with specific instructions. Navigate to the respective service directory for more details.

## Architecture
This is a monorepo structure where all services are maintained in a single repository. Each service is independently deployable and can be developed, tested, and deployed separately.

## Branching Strategy
- **`develop`**: Default branch for active development (feature branches merge here)
- **`main`**: Production-only branch (only receives merges from develop for releases)

See [BRANCHING_STRATEGY.md](./BRANCHING_STRATEGY.md) for complete details and instructions.
