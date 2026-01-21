# **srsBrokerage Application Documentation**



### 1. Project overview

This project is a simplified brokerage application built with Spring Boot.
It features: user login form authentication, account balances and management,
cash transactions and basic asset trading.

### 2. Features

* user registration
* user authentication with Spring Security form login
* user-managed accounts
* cash transaction(deposit, withdraw, transfer)
* buying or selling of assets available
* position tracking per account
* basic validation and error handling

### 3. Tech stack

* Java 17
* Spring Boot
* Maven
* Spring Security
* Spring Data JPA/Hibernate
* PostgreSQL
* JUnit and Mockito

### 4. How to run

**Prerequisites**: Java 17, Maven, PostgreSQL.

**Configuration:**

DB credentials must be configured in application.properties:

* `spring.datasource.url=jdbc:postgresql://localhost:5432/brokerage `
* `spring.datasource.username=your_username`
* `spring.datasource.password=your_password`

* DB name: brokerage
* username/password are set locally
* default PostgreSQl port


**Run:**

* `git clone https://github.com/zoltanSeres/srsBrokerage`
* `cd srsBrokerage`
* `mvn spring-boot:run`
* `port: 8080`

### 5. Project status

Current focus: business logic correctness, improve validations, 
add new features used in real-life brokerage applications
and refactor the code for maintainability.
The long-term goal is to expand the project to include a front end and basic DevOps.

