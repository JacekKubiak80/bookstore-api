# 📚 Book Store API

Book Store API is a RESTful backend application for managing an online bookstore. It not only
organizes books, categories, and orders in a scalable and secure way, but also includes unique 
features such as automatic calculation of shopping cart totals and order amounts, flexible search 
for books by title, author, or ISBN, and DTO validation with a custom @FieldMatch annotation. Users 
can browse books, manage their shopping cart, and place orders, while administrators have full 
control over store content.

The application demonstrates a clean, production-ready architecture using Spring Boot, with 
JWT-based authentication, role-based authorization, and modular design for easy maintenance and 
scalability.

---

## 🚀 Features

### Unique Controller Functionalities
- **BookController**: allows filtering books by title, author, or ISBN and supports pagination.
- **OrderController**: automatically calculates shopping cart and order totals.

### Core Features
- User registration and authentication (JWT)
- Role-based access (USER / ADMIN)
- Book management (CRUD + search with filters)
- Category management (CRUD)
- Shopping cart management per user
- Order processing with automatic total calculation
- Global exception handling for consistent API responses
- Validation of DTOs, including custom `@FieldMatch` annotation

---
## 🏗 Architecture & Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA (Hibernate)
- MySQL / PostgreSQL
- Lombok
- Swagger (OpenAPI)

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

```http
GET /api/books HTTP/1.1
Host: localhost:8080
Authorization: Bearer <your-jwt-token>
```

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

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT configuration
jwt.secret=your_secret_key
jwt.expiration=3600000
```

## ▶️ Run the App

```bash
# Build the project
mvn clean install

# Run Spring Boot application
mvn spring-boot:run
```

## 📄 Swagger

http://localhost:8080/swagger-ui/index.html

## 🧩 Challenges / Lessons Learned
- **JWT Authentication & Role-Based Authorization**: Initially, I faced challenges differentiating 
access between regular users and administrators. I resolved this by creating a 
custom `JwtAuthorizationFilter` and configuring roles in Spring Security.

- **DTO Validation with @FieldMatch**: I wanted the `password` and `repeatPassword` fields to be 
validated together. I implemented a custom annotation `@FieldMatch` along with its validator class.

- **Pagination & Filtering**: When searching books by title, author, or ISBN, I encountered issues
with dynamically building queries. This was solved using Spring Data JPA Specifications combined
with `Pageable`.

- **Soft Delete**: The requirement for soft delete for books and categories required adding a
`deleted` flag and adjusting repository queries, instead of physically removing records.

## 📂 Postman / API Collection

A Postman collection is available for testing all 
endpoints with example requests and JWT authentication:  
[Book Store API.postman_collection.json](postman/Book%20Store%20API.postman_collection.json)

- Import the collection into Postman
- Use the provided **USER** or **ADMIN** credentials to generate JWT tokens
- Test all endpoints including registration, login, book management, cart, and orders

## 🎥 Application demo

**Demo wideo:**  

https://www.loom.com/share/f4d2bed3f76b40bf9d737b3bc53bd270
