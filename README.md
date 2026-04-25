# Core API & Guardrails System 🛡️

A high-performance, stateless Spring Boot 3 microservice built as a central API gateway with Redis-powered guardrails for concurrency control, real-time scoring, and scheduled notifications.

This project was designed to handle high-concurrency scenarios, protect system limits using atomic Redis operations, and maintain data integrity with PostgreSQL as the source of truth.

---

# 🚀 Tech Stack

- Java 21  
- Spring Boot 3  
- PostgreSQL  
- Redis  
- Spring Data JPA  
- Docker Compose  

---

# 🏗️ System Architecture

The application follows a stateless backend design:

- **PostgreSQL** → Persistent source of truth for posts, comments, users, and bots  
- **Redis** → Distributed state manager for counters, cooldown locks, virality scores, and pending notifications  
- **REST APIs** → Clean service endpoints for posts, comments, likes, and scoring  
- **Scheduler** → Background sweeper for summarized notifications  

---

# 🛠️ Key Features

## 1. Atomic Guardrails (Concurrency Protection)

Implemented Redis atomic operations to handle concurrent traffic safely.

### Horizontal Cap

Limits a single post to a maximum of **100 bot comments** using Redis atomic increment operations.

### Cooldown Cap

Prevents a specific bot from interacting with the same user more than once every **10 minutes** using Redis keys with TTL.

### Vertical Cap

Restricts nested comment depth to **20 levels maximum**.

---

## 2. Virality Engine

Real-time Redis-based scoring system:

- Bot Reply = +1  
- Human Like = +20  
- Human Comment = +50  

Scores are updated instantly using Redis counters.

---

## 3. Notification Engine

To reduce spam notifications:

- If a user recently received a notification, new bot interactions are queued in Redis Lists.
- If not, an immediate notification event is triggered.

### Scheduled Sweeper

A Spring `@Scheduled` job runs every 5 minutes:

- Reads pending notifications
- Summarizes interactions
- Clears processed queue data

---

## 4. DTO-Based API Design

Used Java Records / DTOs to separate internal entities from external API contracts.

This improves:

- cleaner request/response models  
- maintainability  
- safer entity abstraction  

---

# 📌 API Endpoints

## Posts

| Method | Endpoint | Description |
|-------|----------|-------------|
| POST | `/api/post` | Create a new post |
| POST | `/api/post/like` | Like a post |
| GET | `/api/posts/getlikes` | Get total likes |
| GET | `/api/posts/getVirality` | Get virality score |

## Comments

| Method | Endpoint | Description |
|-------|----------|-------------|
| POST | `/api/posts/comments` | Add comment after guardrail checks |

---

# 📦 Run Locally

```bash
docker compose up --build -d
