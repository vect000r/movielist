# Movielist

A Spring Boot application for tracking watched movies and their ratings. Built as a learning project to understand Spring Boot fundamentals, JPA/Hibernate ORM, and containerized database integration.

## Table of Contents

- [Functionality](#functionality)
- [Technical Stack](#technical-stack)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Learning Outcomes](#learning-outcomes)

## Functionality

Movielist provides a simple interface for managing a personal movie collection:

- Add movies with titles and ratings to your watchlist
- View all tracked movies in your collection
- Remove movies from your collection
- Persistent storage using PostgreSQL database
- RESTful API for movie operations

## Technical Stack

**Backend:**
- Java
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL (Dockerized)

**Frontend:**
- HTML5
- CSS3
- Vanilla JavaScript

**Testing:**
- JUnit

**DevOps:**
- Docker (for PostgreSQL containerization)



**Key Design Decisions:**

- **JPA/Hibernate ORM**: Provides abstraction over raw SQL, enabling object-oriented database interactions
- **Docker for PostgreSQL**: Ensures consistent database environment across development setups
- **RESTful API Design**: Follows REST principles for predictable endpoint behavior
- **Static Frontend**: Keeps the focus on backend learning while providing functional UI

## Requirements

- Java 11 or higher
- Maven
- Docker and Docker Compose
- Latest PostgreSQL (via Docker)

## Installation

1. **Clone the repository**
```bash
git clone https://github.com/vect000r/movielist.git
cd movielist
```

2. **Start PostgreSQL with Docker**
```bash
docker-compose up -d
```

3. **Configure database connection**

Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/movielist
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Usage

1. Open your browser and navigate to `http://localhost:8080`
2. Use the web interface to:
    - Add new movies with ratings
    - View your movie collection
    - Remove movies you no longer want to track

## Testing

The project includes JUnit tests for core functionality.

**Run tests:**
```bash
mvn test
```

Tests cover:
- Movie entity validation
- Service layer operations
- Repository interactions

## Project Structure
```
movielist/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/vect0r/movielist/
│   │   │       ├── Movie.java                    # Entity model
│   │   │       ├── MovieController.java          # REST endpoints
│   │   │       ├── MovieRepository.java          # Data access
│   │   │       ├── MovieService.java             # Business logic
│   │   │       └── MovielistApplication.java     # Main application
│   │   └── resources/
│   │       ├── static/                           # HTML/CSS/JS
│   │       └── application.properties            # Config
│   └── test/
│       └── java/
│           └── com/vect0r/movielist/
│               └── MovielistApplicationTests.java
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Learning Outcomes

This project was built to gain hands-on experience with:

**Spring Boot Framework**: Understanding dependency injection, auto-configuration, and the Spring ecosystem  
**Spring Data JPA**: Working with repositories, entities, and ORM abstractions  
**Hibernate**: Understanding object-relational mapping and persistence context  
**RESTful API Design**: Implementing CRUD operations following REST principles  
**Docker**: Containerizing PostgreSQL for consistent development environments  
**Database Design**: Modeling entities and relationships in a relational database  
**Testing**: Writing unit tests with JUnit for Java applications

---

**Note**: This is an educational project built to learn Spring Boot fundamentals. It prioritizes learning core concepts over production-ready features like authentication, advanced validation, or comprehensive error handling.