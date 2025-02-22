# 🛒 Spring Shopping Carts

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen) ![Spring Security](https://img.shields.io/badge/Spring%20Security-Enabled-green) ![MySQL](https://img.shields.io/badge/Database-MySQL-blue)

Spring Shopping Carts is a RESTful API designed to streamline e-commerce operations. It offers a robust user management system, ensuring secure registration and authentication processes. With built-in Spring Security, it protects user data and prevents unauthorized access, making it a reliable solution for online shopping platforms.

The API provides comprehensive product management functionalities, allowing administrators to update and filter products efficiently. Users can browse the product catalog, while admins have the capability to modify product details. This flexibility ensures a seamless shopping experience with real-time product updates and filtering options.

For order processing, the API enables users to create orders, add items, and calculate the total cost dynamically. The order management system ensures accuracy in calculations and efficient handling of customer transactions. With Spring Boot 3 and MySQL integration, it guarantees high performance and scalability for growing businesses.

## ✨ Features

- 👤 **User Management**
  - 📝 Register a new user
  - 🔍 Get all users (Admin only)
  - ✏️ Update user details
  - ❌ Delete user (Admin only)

- 📦 **Product Management**
  - 📋 Get all products
  - 🛠️ Update product details
  - 🔎 Filter products

- 🛍️ **Order Management**
  - 🆕 Create an order
  - ➕ Add items to an order
  - 💰 Calculate order total

## 🛠 Technologies Used

- 🚀 **Spring Boot 3**
- 🔐 **Spring Security**
- 🗄️ **MySQL**
- ☕ **Java 17**

## 🔗 API Endpoints

### 👤 User Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/rest/v1/users/register` | 📝 Register a new user |
| `GET`  | `/rest/v1/users` | 🔍 Get all users (Admin only) |
| `PUT`  | `/rest/v1/users/{id}` | ✏️ Update user details |
| `DELETE` | `/rest/v1/users/{id}` | ❌ Delete user (Admin only) |

### 📦 Product Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET`  | `/rest/v1/products` | 📋 Get all products |
| `PUT`  | `/rest/v1/products/{id}` | 🛠️ Update product details |
| `GET`  | `/rest/v1/products/filter` | 🔎 Filter products |

### 🛍️ Order Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/rest/v1/orders` | 🆕 Create a new order |
| `PUT`  | `/rest/v1/orders/{id}/add` | ➕ Add items to an order |
| `GET`  | `/rest/v1/orders/{id}/calculate` | 💰 Calculate order total |

## 🏗 Setup and Installation

1. 📥 Clone the repository:
   ```bash
   git clone https://github.com/your-username/spring-shopping-carts.git
   cd spring-shopping-carts
   ```
2. ⚙️ Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/shopping_cart
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. 🏃 Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 📜 License
This project is licensed under the MIT License.

---

🎨 _Icons and further details can be customized as needed._

