# Movie Reservation System

A RESTful API for movie ticket booking built with Spring Boot 3 and Java 21.

## Tech Stack

- **Java 21** + **Spring Boot 3.5**
- **PostgreSQL** — primary database with Flyway migrations
- **Redis** — session / caching layer
- **Spring Security** + **JWT** — stateless authentication
- **Testcontainers** — integration testing with real database containers
- **Swagger UI** — interactive API documentation

## Features

- User registration and login with JWT
- Browse movies and showtimes
- Book seats with pessimistic locking (prevents double-booking)
- Temporary seat hold — auto-released after 10 minutes via `@Scheduled`
- Cancel tickets
- Admin reports: bookings, capacity, revenue

## Prerequisites

- Java 21
- Docker (for PostgreSQL and Redis)

## Getting Started

**1. Start dependencies**
```bash
docker compose up -d
```

**2. Run the application**
```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`

**3. Open API docs**
```
http://localhost:8080/swagger-ui/index.html
```

## Running Tests

```bash
./mvnw test
```

Integration tests use Testcontainers — Docker must be running.

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register new user | Public |
| POST | `/api/auth/login` | Login and get JWT token | Public |

### Movies
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/movies` | List all movies | Public |
| GET | `/api/movies/{id}` | Get movie by ID | Public |

### Schedules
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/schedules` | List all schedules | Public |
| GET | `/api/schedules/{id}` | Get schedule by ID | Public |

### Seats
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/seats/**` | List seats by schedule | Public |

### Tickets
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/tickets/my` | Get my tickets | User/Admin |
| POST | `/api/tickets/book` | Book a seat | User/Admin |
| POST | `/api/tickets/hold` | Temporarily hold a seat | User/Admin |
| PATCH | `/api/tickets/{id}/cancel` | Cancel a ticket | User/Admin |
| DELETE | `/api/tickets/{id}` | Delete a ticket | User/Admin |

### Admin Reports
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/admin/reports/bookings` | Booking count per schedule | Admin |
| GET | `/api/admin/reports/capacity` | Seat capacity per schedule | Admin |
| GET | `/api/admin/reports/revenue` | Total revenue | Admin |

## Project Structure

```
src/
├── main/java/com/project/movie/
│   ├── controller/     # REST controllers
│   ├── service/        # Business logic
│   ├── repository/     # JPA repositories
│   ├── model/          # JPA entities
│   ├── dto/            # Request / Response DTOs
│   ├── security/       # JWT filter, UserDetails
│   ├── exception/      # Custom exceptions + GlobalExceptionHandler
│   └── config/         # SecurityConfig
└── main/resources/
    └── db/migration/   # Flyway SQL migrations
```
