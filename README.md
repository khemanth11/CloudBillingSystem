# Cloud Billing & Invoice Management System

A **RESTful** cloud billing and invoice management backend built with **Spring Boot**. It provides user authentication with **JWT**, role-based access control, and full invoice lifecycle management—suitable for SaaS or internal billing use cases.

---

## Features

- **Authentication & Authorization**
  - User registration and login
  - **JWT**-based stateless authentication
  - **Role-based access** (e.g. ADMIN vs regular users)
  - Password hashing with **BCrypt**

- **User Management**
  - Create users and list all users (ADMIN)
  - Get user by ID
  - User–invoice association

- **Invoice Management**
  - Create invoices for users (linked to user)
  - List **my invoices** (authenticated user)
  - List **all invoices** (admin/reporting)
  - Invoice fields: amount, currency, description, status (PENDING / PAID / OVERDUE), due date

- **API & Architecture**
  - REST APIs with **DTOs** for clean request/response
  - **Global exception handling** (e.g. invalid credentials → 401)
  - **Spring Data JPA** with MySQL
  - Layered design: Controller → Service → Repository

---

## Tech Stack

| Category        | Technology              |
|----------------|-------------------------|
| **Language**   | Java 17                 |
| **Framework**  | Spring Boot 3.5         |
| **Security**   | Spring Security + JWT   |
| **Persistence**| Spring Data JPA, MySQL  |
| **Validation** | Bean Validation         |
| **Build**      | Maven                   |
| **Utilities**  | Lombok                  |

---

## Project Structure

```
src/main/java/com/cloudbilling/billingsystem/
├── BillingSystemApplication.java
├── controller/          # REST endpoints
│   ├── AuthController.java
│   ├── InvoiceController.java
│   └── UserController.java
├── service/             # Business logic
│   ├── InvoiceService.java
│   └── UserService.java
├── repository/          # Data access
│   ├── InvoiceRepository.java
│   └── UserRepository.java
├── model/               # JPA entities
│   ├── Invoice.java
│   └── User.java
├── dto/                 # Request/Response DTOs
│   ├── AuthRequest.java, AuthResponse.java
│   ├── UserDTO.java
│   └── InvoiceDTO.java
├── security/            # JWT filter & config
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
└── exception/           # Global error handling
    ├── GlobalExceptionHandler.java
    └── InvalidCredentialsException.java
```

---

## API Overview

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/auth/register` | Register a new user | Public |
| `POST` | `/auth/login` | Login; returns JWT | Public |
| `GET`  | `/users/all` | List all users | ADMIN |
| `GET`  | `/users/{id}` | Get user by ID | Authenticated |
| `POST` | `/users` | Create user | Authenticated |
| `POST` | `/invoices/user/{userId}` | Create invoice for user | Authenticated |
| `GET`  | `/invoices/me` | Get current user's invoices | Authenticated |
| `GET`  | `/invoices/all` | Get all invoices | Authenticated |

**Authentication:** Send JWT in the `Authorization` header as `Bearer <token>` for protected endpoints.

---

## Prerequisites

- **Java 17**
- **Maven 3.6+**
- **MySQL 8** (or compatible)

---

## Setup & Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/billing-system.git
   cd billing-system
   ```

2. **Create MySQL database**
   ```sql
   CREATE DATABASE cloud_billing_system;
   ```

3. **Configure database**  
   Set your MySQL credentials (avoid committing real passwords). You can use environment variables or override in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/cloud_billing_system
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
   Or use env vars: `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```
   Server runs at **http://localhost:8080**.

---

## Quick API Examples

**Register**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"secret123","role":"USER"}'
```

**Login**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"secret123"}'
# Response: {"token":"<jwt_token>"}
```

**Get my invoices** (use token from login)
```bash
curl -X GET http://localhost:8080/invoices/me \
  -H "Authorization: Bearer <your_jwt_token>"
```

**Create invoice for a user**
```bash
curl -X POST http://localhost:8080/invoices/user/1 \
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{"amount":199.99,"currency":"USD","description":"Monthly subscription","status":"PENDING","dueDate":"2025-04-15"}'
```

---

## License

This project is open source and available for educational and portfolio use.

---

*Built with Spring Boot — suitable for resume and technical interviews.*
