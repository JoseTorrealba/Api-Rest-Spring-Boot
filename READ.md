# Orquidea Express API

This is a Spring Boot application for Orquidea Express API. It provides RESTful endpoints for managing products and customers.

## Table of Contents

- [Getting Started](#getting-started)
- [Building and Running](#building-and-running)
- [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Testing](#testing)
- [Docker](#docker)
- [Kubernetes Deployment](#kubernetes-deployment)

## Getting Started

### Prerequisites

- Java 17
- Maven 3.9.9
- Docker (optional)
- Kubernetes (optional)

### Clone the Repository

```sh
git clone https://github.com/your-repo/orquidea-express-api.git
cd orquidea-express-api

Build the Project
./mvnw clean package

Run the Application
./mvnw spring-boot:run

Endpoints
Products
GET /api/products - Get all products
GET /api/products/{id} - Get product by ID
POST /api/products - Create a new product
DELETE /api/products/{id} - Delete a product
Customers
GET /api/customers - Get all customers
GET /api/customers/{id} - Get customer by ID
POST /api/customers - Create a new customer
PUT /api/customers/{id} - Update a customer
DELETE /api/customers/{id} - Delete a customer
Configuration
Configuration
Configuration properties are defined in application.properties.

Testing
Run Unit Tests
Docker
Build Docker Image
Run Docker Container
Kubernetes Deployment
Apply Kubernetes Deployment
Access the Application
The application will be available at http://<node-ip>:30000.

License
This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

