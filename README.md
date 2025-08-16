

# **Restaurant Management System Backend**

## **Overview**

This project is a backend system for a restaurant management application. It handles user authentication, table management, booking management, and order processing. It uses **JWT-based authentication** to secure API endpoints and supports multiple roles like **ADMIN, MANAGER, WAITER, CHEF**.

---

## **Features**

* User registration and login with JWT authentication.
* Role-based access control for ADMIN, MANAGER, WAITER, and CHEF.
* CRUD operations for **Users, Tables, Bookings, and Orders**.
* Stateless REST API secured with JWT.
* Handles multiple user roles with permissions:

  * **ADMIN:** Full access to users, tables, bookings, and orders.
  * **MANAGER:** Access to tables, bookings, and orders.
  * **WAITER:** Access to tables, bookings, and orders.
  * **CHEF:** Access to tables, bookings, and orders.

---

## **Tech Stack**

* **Backend Framework:** Spring Boot
* **Database:** PostgreSQL
* **Security:** Spring Security + JWT
* **Java Version:** 17+
* **Build Tool:** Maven

---

## **Prerequisites**

* Java 17+
* Maven 3+
* Postman (for API testing)
* PostgreSQL 
---

## **Setup & Running the Project**

### **1. Clone the repository**

```bash
git clone https://github.com/dmvanushree/Restaurant-Backend/
cd Phase2
```

### **2. Configure application.properties**

### **3. Build & Run**

```bash
mvn clean install
mvn spring-boot:run
```

* Application will run on: `http://localhost:8080`

---

## **API Endpoints**

### **1. Authentication (Open Endpoint, No JWT Required)**

| Method | URL            | Request Body                                                                              | Response                        |
| ------ | -------------- | ----------------------------------------------------------------------------------------- | ------------------------------- |
| POST   | /auth/register | `{ "name": "Alice", "email": "alice@example.com", "password": "1234", "role": "WAITER" }` | `User registered successfully!` |
| POST   | /auth/login    | `{ "email": "alice@example.com", "password": "1234" }`                                    | `{ "token": "<JWT_TOKEN>" }`    |

**Authentication Flow:**

1. User registers via `/auth/register`.
2. User logs in via `/auth/login` → Receives **JWT Token**.
3. Use the JWT token in the **Authorization header** for secured endpoints:

```
Authorization: Bearer <JWT_TOKEN>
```

---

### **2. Users (JWT Required in Headers)**

| Method | URL         | Roles Allowed | Request Body                                                                           | Response       |
| ------ | ----------- | ------------- | -------------------------------------------------------------------------------------- | -------------- |
| GET    | /users      | ADMIN         | None                                                                                   | List of users  |
| GET    | /users/{id} | ADMIN         | None                                                                                   | User details   |
| POST   | /users      | ADMIN         | `{ "name": "Bob", "email": "bob@example.com", "password": "1234", "role": "MANAGER" }` | Created user   |
| PUT    | /users/{id} | ADMIN         | `{ "name": "Bob", "email": "bob@example.com", "password": "1234", "role": "MANAGER" }` | Updated user   |
| DELETE | /users/{id} | ADMIN         | None                                                                                   | 204 No Content |

---

### **3. Tables (JWT Required in Headers)**

| Method | URL          | Roles Allowed                | Request Body                          | Response       |
| ------ | ------------ | ---------------------------- | ------------------------------------- | -------------- |
| GET    | /tables      | ADMIN, MANAGER, WAITER, CHEF | None                                  | List of tables |
| GET    | /tables/{id} | ADMIN, MANAGER, WAITER, CHEF | None                                  | Table details  |
| POST   | /tables      | ADMIN, MANAGER, WAITER, CHEF | `{ "tableNumber": 1, "capacity": 4 }` | Created table  |
| PUT    | /tables/{id} | ADMIN, MANAGER, WAITER, CHEF | `{ "tableNumber": 1, "capacity": 4 }` | Updated table  |
| DELETE | /tables/{id} | ADMIN, MANAGER, WAITER, CHEF | None                                  | 204 No Content |

---

### **4. Bookings (JWT Required in Headers)**

| Method | URL            | Roles Allowed                | Request Body                                                                                                                     | Response         |
| ------ | -------------- | ---------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ---------------- |
| GET    | /bookings      | ADMIN, MANAGER, WAITER, CHEF | None                                                                                                                             | List of bookings |
| GET    | /bookings/{id} | ADMIN, MANAGER, WAITER, CHEF | None                                                                                                                             | Booking details  |
| POST   | /bookings      | ADMIN, MANAGER, WAITER, CHEF | `{ "customerName": "Alice", "bookingTime": "2025-08-14T19:00:00", "tableNumber": 1, "numberOfGuests": 4, "handledByUserId": 1 }` | Created booking  |
| PUT    | /bookings/{id} | ADMIN, MANAGER, WAITER, CHEF | `{ "customerName": "Alice", "bookingTime": "2025-08-14T19:00:00", "tableNumber": 1, "numberOfGuests": 4, "handledByUserId": 1 }` | Updated booking  |
| DELETE | /bookings/{id} | ADMIN, MANAGER, WAITER, CHEF | None                                                                                                                             | 204 No Content   |

---

### **5. Orders (JWT Required in Headers)**

| Method | URL          | Roles Allowed                | Request Body                                                      | Response       |
| ------ | ------------ | ---------------------------- | ----------------------------------------------------------------- | -------------- |
| GET    | /orders      | ADMIN, MANAGER, WAITER, CHEF | None                                                              | List of orders |
| GET    | /orders/{id} | ADMIN, MANAGER, WAITER, CHEF | None                                                              | Order details  |
| POST   | /orders      | ADMIN, MANAGER, WAITER, CHEF | `{ "tableId": 1, "items": ["Pizza", "Coke"], "totalPrice": 500 }` | Created order  |
| PUT    | /orders/{id} | ADMIN, MANAGER, WAITER, CHEF | `{ "items": ["Pizza", "Coke"], "totalPrice": 500 }`               | Updated order  |
| DELETE | /orders/{id} | ADMIN, MANAGER, WAITER, CHEF | None                                                              | 204 No Content |

---

## **Authentication Flow Diagram**

1. **Register:** Open endpoint → stores user in DB.
2. **Login:** Open endpoint → returns JWT token.
3. **Secured endpoints:** `/users/**`, `/tables/**`, `/bookings/**`, `/orders/**` → JWT required.
4. **Roles:** Check roles from JWT → allow/deny access.

---


* JWT token expires after a configured time (`jwt.expirationMs` in `application.properties`).
* Role-based access is enforced using Spring Security `hasRole` or `hasAnyRole`.
* For testing, you can use Postman to:

  1. Register a user.
  2. Login to get JWT.
  3. Set JWT in **Authorization header** and test secured endpoints.


