# Virtual College Space

> A web platform for a virtual college community, built with Spring Boot.

Virtual College Space is a full-stack Spring Boot web application that gives a college community a shared online space. Users sign in with their GitHub account, and the app serves server-rendered pages backed by a MySQL database, with Spring Security handling authentication and access.

## Features

- **GitHub sign-in** — OAuth2 login, so there's no separate password to manage.
- **Secure, role-aware pages** — access controlled with Spring Security and security-aware Thymeleaf templates.
- **Persistent data** — stored in MySQL through Spring Data JPA (schema created automatically on startup).
- **Server-rendered web UI** — built with Spring MVC and Thymeleaf.

## Built with

- **Java 11** · **Spring Boot 2.6**
- **Spring MVC + Thymeleaf** — server-rendered UI
- **Spring Security + OAuth2 Client** — GitHub authentication
- **Spring Data JPA / Hibernate**
- **MySQL**

## Getting started

### Prerequisites
- Java 11+
- Maven
- MySQL
- A GitHub OAuth app

### 1. Create the database
Create a MySQL database named `vcs1`. Tables are created automatically on first run (JPA `ddl-auto: update`).

### 2. Register a GitHub OAuth app
Create one at **GitHub → Settings → Developer settings → OAuth Apps**, and set the authorization callback URL to:

```
http://localhost:8080/login/oauth2/code/github
```

Note the generated **client ID** and **client secret**.

### 3. Configure
In `src/main/resources/application.yml`, set your MySQL `username` / `password` and your GitHub OAuth `client-id` / `client-secret`.

### 4. Run

```bash
git clone https://github.com/aadeshwagh/Virtual-College-Space.git
cd Virtual-College-Space
mvn spring-boot:run
```

Then open **http://localhost:8080** and sign in with GitHub.
