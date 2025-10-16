# 🧩 Template for Spring Boot Project

This is a sample project created with **Spring Boot 3.5.6**, 
using **Java 21** and **Gradle (Groovy DSL)** as the build tool.

---

## 🚀 Technologies Used

- **Java 21**
- **Spring Boot 3.5.6**
- **Gradle (Groovy DSL)**
- **YAML** for configuration (`application.yaml`)

### Frameworks and Libraries

- **Lombok**
- **H2 Database**
- **MapStruct**

## 🔹 Prerequisites
- **Java 21** installed
- **Gradle 8+** (or use the included wrapper)
- Compatible IDE (IntelliJ IDEA, VS Code, Eclipse, etc.)

## 🧰 Steps to Run

1. **Clone the repository**
 - git clone https://github.com/seu-usuario/demo.git
 - cd demo

2. **Run with Gradle**
 - ./gradlew bootRun

3. **Access the application**
 - http://localhost:8080

### Run Tests

1. To execute automated tests:
 - ./gradlew test

### Build the Project

1. Generate the .jar file:
 - ./gradlew build

2. The final artifact will be created at:
 - build/libs/demo-0.0.1-SNAPSHOT.jar

### 💾 Access the H2 Console:
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)