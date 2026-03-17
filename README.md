# 📚 Book Store API

Book Store API is a RESTful backend application for managing an online bookstore. It allows users to browse books, manage a shopping cart, and place orders, while administrators can manage books and categories.

The application demonstrates a clean and scalable architecture based on Spring Boot. It includes authentication with JWT, role-based authorization, and practical business features such as filtering, pagination, and order processing.

This project is a solid example of a real-world backend system, combining security, database operations, and modular design in a clear and maintainable way.

---

## 🚀 Features

- User registration and authentication (JWT)
- Role-based access (USER / ADMIN)
- Book management (CRUD + search)
- Category management
- Shopping cart
- Order processing
- Global exception handling
- Validation support

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA (Hibernate)
- MySQL / PostgreSQL (configurable)
- Lombok
- Swagger (OpenAPI)

## 🔐 Authentication

- `/api/auth/register` – register user
- `/api/auth/login` – login and receive JWT token

Use token in requests:

---

## 📦 API Endpoints

### Books (`/api/books`)
- `GET /` – list books (pagination)
- `GET /{id}` – get book by id
- `POST /` – create book (ADMIN)
- `PUT /{id}` – update book (ADMIN)
- `DELETE /{id}` – delete book (ADMIN)
- `GET /search` – search books (filters: title, author, isbn)

### Categories (`/api/categories`)
- `GET /` – list categories
- `GET /{id}` – get category
- `POST /` – create (ADMIN)
- `PUT /{id}` – update (ADMIN)
- `DELETE /{id}` – delete (ADMIN)
- `GET /{id}/books` – books by category

### Cart (`/api/cart`)
- `GET /` – get cart
- `POST /` – add book
- `PUT /cart-items/{id}` – update quantity
- `DELETE /cart-items/{id}` – remove item

### Orders (`/api/orders`)
- `POST /` – place order
- `GET /` – user orders
- `GET /{id}/items` – order items
- `GET /{id}/items/{itemId}` – single item
- `PATCH /{id}` – update status (ADMIN)

---

## ⚙️ Configuration

Set in `application.properties`:
spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_secret_key
jwt.expiration=3600000

---

## ▶️ Run the App

mvn clean install
mvn spring-boot:run

App runs on:

http://localhost:8080

---

## 📄 Swagger

http://localhost:8080/swagger-ui/index.html

---

## 🧩 Notes

- Soft delete implemented for Book and Category
- Validation handled via DTO + custom annotation (`@FieldMatch`)
- Security based on stateless JWT authentication
- Pagination supported in main endpoints

---