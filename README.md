# 📦 Product Service

## 🚀 Overview

The **Product Service** is a core microservice in the E-Commerce system responsible for:

* Managing product details
* Handling inventory (quantity)
* Validating product availability
* Reducing stock during order processing

It communicates with other services like:

* 🧾 Order Service
* 📧 Email Service
* 👤 User Service (future integration)

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Apache Kafka
* Lombok

---

## 📂 Project Structure

```
product-service
│── controller
│── service
│── repository
│── model
│── dto
│── exception
│── config
```

---

## ⚙️ Configuration

### application.yml / application.properties

```yaml
server:
  port: 8082

spring:
  application:
    name: product-service

  datasource:
    url: jdbc:postgresql://localhost:5432/shiroyadb
    username: postgres
    password: shiroya

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  kafka:
    producer:
      bootstrap-servers: localhost:9092
    consumer:
      bootstrap-servers: localhost:9092
      group-id: product-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "*"
```

---

## 🧱 Entity: Product

```java
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private String email;
}
```

---

## 📡 REST APIs

### 1️⃣ Create Product

**POST** `/product`

#### Request Body

```json
{
  "productId": "P1001",
  "name": "iPhone 15",
  "description": "Latest Apple iPhone",
  "price": 79999.00,
  "quantity": 10,
  "email": "admin@example.com"
}
```

---

### 2️⃣ Get Product by ProductId

**GET** `/product/{productId}`

#### Example

```
GET /product/P1001
```

---

### 3️⃣ Validate & Reduce Product (Order Flow)

**POST** `/product/validateAndReduce`

#### Request Body

```json
{
  "productId": "P1001",
  "quantity": 2
}
```

#### Responses

| Status | Meaning                              |
| ------ | ------------------------------------ |
| 200    | Product available & quantity reduced |
| 404    | Product not found                    |
| 409    | Insufficient quantity                |

---

## 🔄 Business Logic

### ✔️ Validate Product

* Check if product exists
* If not → return 404

### ✔️ Check Quantity

* If requested > available → return 409

### ✔️ Reduce Stock

* Deduct quantity
* Save updated product

---

## ⚠️ Common Errors & Fixes

### ❌ 404 Not Found

* Product does not exist in DB
* Check `productId`

---

### ❌ SQL Error: value too long

```
value too long for type character varying(20)
```

✔️ Fix:

* Increase column size OR send smaller data

---

### ❌ NOT NULL constraint error

```
column contains null values
```

✔️ Fix:

* Existing DB data has NULL
* Update DB before applying constraint

---

## 🔗 Integration Flow

```
Order Service → Product Service → Kafka → Email Service
```

1. Order created
2. Product validated
3. Stock reduced
4. Event sent via Kafka
5. Email notification triggered

---

## 📩 Kafka Topics (Example)

* `order-created`
* `product-validated`

---

## 🔮 Future Enhancements

* ✅ Integration with User Service
* ✅ Add caching (Redis)
* ✅ Add API Gateway
* ✅ Add Security (JWT)
* ✅ Add Circuit Breaker (Resilience4j)

---

## 👨‍💻 Author

**Satyendra Chaurasiya**

---

## ⭐ Notes

* Ensure Kafka is running before starting service
* PostgreSQL must be up
* Use proper JSON format for API calls
* Keep field sizes aligned with DB schema
