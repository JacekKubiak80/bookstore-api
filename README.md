# 📚 Book Store API

Book Store API is a RESTful backend application for managing an online bookstore. It solves the common problem of organizing books, categories, and orders in a scalable and secure way. The system allows users to browse books, manage a shopping cart, and place orders, while administrators can manage the store’s content.

The application demonstrates a clean, production-like architecture using Spring Boot, with JWT-based authentication, role-based authorization, and modular design.

---

## 🚀 Features

- User registration and authentication (JWT)
- Role-based access (USER / ADMIN)
- Book management (CRUD + search with filters)
- Category management
- Shopping cart per user
- Order processing with total calculation
- Global exception handling
- Validation (including custom `@FieldMatch`)

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA (Hibernate)
- MySQL / PostgreSQL
- Lombok
- Swagger (OpenAPI)

---

## 🏗 Architecture

The application follows a layered architecture:

- Controller – handles HTTP requests
- Service – business logic
- Repository – database access
- DTO – data transfer
- Security – JWT authentication and authorization

---

## 🏗 Architecture Diagram

                ┌─────────────┐
                │   Client    │
                │(Browser/Postman)│
                └──────┬──────┘
                       │ HTTP Requests (JSON)
                       ▼
                ┌─────────────┐
                │ Controller  │
                │(REST Endpoints)│
                └──────┬──────┘
                       │
                       ▼
                ┌─────────────┐
                │  Service    │
                │(Business Logic)│
                └──────┬──────┘
                       │
                       ▼
                ┌─────────────┐
                │ Repository  │
                │(JPA / DB)  │
                └──────┬──────┘
                       │
                       ▼
                ┌─────────────┐
                │   MySQL DB  │
                └─────────────┘

Security Layer:
- JWT Authentication
- Role-based Authorization (USER / ADMIN)

## 🔐 Authentication

- `/api/auth/register` – register user
- `/api/auth/login` – login and receive JWT token

Use token in requests:

---

## 🔑 Test Credentials

USER:
email: user@test.com  
password: password123

ADMIN:
email: admin@test.com  
password: admin123

---

## 📬 Example Requests

### Register
POST /api/auth/register

```json
{
  "email": "user@test.com",
  "password": "password123",
  "repeatPassword": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```


### Login
POST /api/auth/login

```json
{
  "email": "user@test.com",
  "password": "password123"
}
```

### Create Book (ADMIN)
POST /api/books
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "123456789",
  "price": 39.99
}
```

## ⚙️ Configuration
...

spring.datasource.url=jdbc:mysql://localhost:3306/book_store

spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_secret_key
jwt.expiration=3600000


## ▶️ Run the App
...

mvn clean install
mvn spring-boot:run

## 📄 Swagger
...

http://localhost:8080/swagger-ui/index.html