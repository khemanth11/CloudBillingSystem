# Cloud Billing System

> A **Spring Boot** backend for user management, JWT auth, role-based access, and invoice lifecycle — similar in spirit to billing platforms used in SaaS and utilities.

---

## Highlights
  
| | |
|:---|:---|
| **Auth** | Register / login, **BCrypt** passwords, **JWT** bearer tokens |
| **Roles** | `ROLE_USER` (default) & `ROLE_ADMIN` — admin set in DB or via your workflow |
| **Users** | Safe **DTOs** (no password in JSON); admin can list all users |
| **Invoices** | Create (admin), list mine, list all (admin), **PATCH** status (`PENDING` / `PAID` / `OVERDUE`) |
| **Docs** | **Swagger UI** — interactive API explorer |
| **Errors** | Structured JSON for invalid login (401) |

---



## Tech stack

`Java 17` · `Spring Boot 3.5` · `Spring Security` · `Spring Data JPA` · `MySQL` · `JWT (jjwt)` · `Lombok` · `Maven` · `SpringDoc OpenAPI`

---

## Architecture

```
Controller → Service → Repository → MySQL
     ↓
   DTOs (safe responses) · JWT filter · Global exception handler
```

---

## Project layout

```
com.cloudbilling.billingsystem
├── controller/     AuthController, UserController, InvoiceController
├── service/        UserService, InvoiceService
├── repository/     UserRepository, InvoiceRepository
├── model/          User, Invoice
├── dto/            UserDTO, InvoiceDTO, AuthRequest, AuthResponse
├── security/       SecurityConfig, JwtAuthenticationFilter, JwtUtil
└── exception/      GlobalExceptionHandler, InvalidCredentialsException
```

---

## API reference

**Auth header (protected routes):** `Authorization: Bearer <token>`

| Method | Endpoint | Description | Who |
|:------:|----------|-------------|-----|
| `POST` | `/auth/register` | Register (password hashed; default role `ROLE_USER`) | Public |
| `POST` | `/auth/login` | Returns JWT | Public |
| `GET` | `/users/{id}` | User by ID (no password) | Authenticated |
| `GET` | `/users/all` | All users (DTOs, no passwords) | **ADMIN** |
| `POST` | `/users` | Create user (hashed password) | Authenticated |
| `POST` | `/invoices/user/{userId}` | Create invoice for a user | **ADMIN** |
| `GET` | `/invoices/me` | Current user’s invoices | Authenticated |
| `GET` | `/invoices/all` | All invoices | **ADMIN** |
| `PATCH` | `/invoices/{id}/status` | Body: `{ "status": "PAID" }` (or `PENDING` / `OVERDUE`) | **ADMIN** |

**Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) (no login required to browse; use **Authorize** with `Bearer <token>` to try protected calls.)

---

## Prerequisites

- **JDK 17**
- **Maven 3.6+**
- **MySQL 8** (or compatible)

---

## Run locally

### 1. Create database

```sql
CREATE DATABASE cloud_billing_system;
```

### 2. Configure datasource

Copy the example file and set your credentials:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `application.properties` with your MySQL URL, username, and password.  
*Do not commit real passwords — keep `application.properties` out of version control if it contains secrets.*

Alternatively override with environment variables, e.g.:

```bash
set SPRING_DATASOURCE_USERNAME=root
set SPRING_DATASOURCE_PASSWORD=yourpassword
```

### 3. Start the app

```bash
mvn spring-boot:run
```

App runs at **http://localhost:8080**.

### 4. Admin user

New registrations get `ROLE_USER`. Promote a user to admin in MySQL:

```sql
UPDATE users SET role = 'ROLE_ADMIN' WHERE email = 'your@email.com';
```

---

## Quick examples (curl)

**Register**

```bash
curl -X POST http://localhost:8080/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Alice\",\"email\":\"alice@example.com\",\"password\":\"Secret123\"}"
```

**Login**

```bash
curl -X POST http://localhost:8080/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"alice@example.com\",\"password\":\"Secret123\"}"
```

**My invoices** (token from login)

```bash
curl http://localhost:8080/invoices/me ^
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Update invoice status** (admin token)

```bash
curl -X PATCH http://localhost:8080/invoices/1/status ^
  -H "Authorization: Bearer ADMIN_JWT_TOKEN" ^
  -H "Content-Type: application/json" ^
  -d "{\"status\":\"PAID\"}"
```

---

## Security notes

- Passwords are **never** returned in user DTOs.
- Use **strong DB credentials** in local `application.properties`; prefer **env vars** or a **gitignored** local config for real secrets.
- JWT signing key is suitable for development; for production, use a **fixed secret** from configuration or a secrets manager.

---

## License

Educational and portfolio use.

---

*Built with Spring Boot — backend ready for a web or mobile client.*
