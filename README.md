# 🚗 Car Rental Service

> A robust and scalable backend for a Car Rental System built with **Spring Boot 3** and **Java 21**.

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-brightgreen?style=for-the-badge&logo=spring-boot)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green?style=for-the-badge&logo=swagger)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Ready-blue?style=for-the-badge&logo=postgresql)

---

## 📖 Table of Contents
- [📌 Overview](#-overview)
- [🛠️ Tech Stack](#-tech-stack)
- [📂 Project Structure](#-project-structure)
- [🚀 Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation & Running](#installation--running)
- [📄 API Documentation](#-api-documentation)
- [💾 Database](#-database)
- [🛡️ License](#-license)

---

## 📌 Overview
**Car Rental Service** is a RESTful API designed to manage the core operations of a car rental business. It provides endpoints for managing:
- **Cars**: Listing, searching, and updating vehicle inventory.
- **Customers**: User management and profiles.
- **Reservations**: Booking logic and availability checks.
- **Locations**: Rental branches and pickup/drop-off points.
- **Extra Services**: Additional amenities for rentals.

The application follows a clean architecture with clear separation between Controllers, Services, Repositories, and Entities.

---

## 🛠️ Tech Stack
- **Language**: Java 21
- **Framework**: Spring Boot 3.5.7
- **Database Access**: Spring Data JPA (Hibernate)
- **Validation**: Spring Boot Starter Validation
- **DTO Mapping**: MapStruct
- **Boilerplate Reduction**: Lombok
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Databases Supported**: H2, Apache Derby, MySQL, PostgreSQL (Drivers included)

---

## 📂 Project Structure
The project follows a standard Maven directory layout:

```text
com.kerem
├── bootstrap       # Data initializers
├── config          # Configuration classes (OpenAPI, etc.)
├── controller      # REST Controllers (API Layer)
│   └── impl        # Controller Implementations
├── dto             # Data Transfer Objects
├── entities        # JPA Entities (Database Models)
├── exception       # Global Exception Handling
├── mapper          # MapStruct Interfaces
├── repository      # JPA Repositories (Data Access Layer)
│   └── specification # Custom JPA Specifications
└── service         # Business Logic Layer
```

---

## 🚀 Getting Started

### Prerequisites
*   **Java 21** or higher
*   **Maven** (or use the included `mvnw` wrapper)

### Installation & Running

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/K3remK/car-rental-service.git
    cd car-rental-service
    ```

2.  **Build the project**:
    ```bash
    ./mvnw clean install
    ```

3.  **Run the application**:
    ```bash
    ./mvnw spring-boot:run
    ```

The application will start on `http://localhost:8080`.

---

## 📄 API Documentation
This project uses **Swagger UI** for interactive API documentation.

Once the application is running, access the documentation at:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

The API docs include:
- Detailed endpoint descriptions
- Request/Response schemas
- Interactive "Try it out" feature

---

## 💾 Database
The application supports multiple databases. By default, it may depend on your `application.properties` configuration.
- **H2 / Derby**: In-memory or embedded for development/testing.
- **PostgreSQL / MySQL**: Production-ready support.

To switch databases, configure the `spring.datasource` properties in `src/main/resources/application.properties`.

---

## 🛡️ License
This project is open-source and available under the [MIT License](LICENSE).
