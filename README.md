# chat-app-springboot

# Real-Time Chat Backend (Spring Boot + SSE)

This repository contains the backend for a scalable real-time chat application built using **Spring Boot**, **Server-Sent Events (SSE)**, and **MySQL**. The system is designed to support **group-based messaging with permission controls**, inspired by large-scale messaging platforms like WhatsApp.

## 🚀 Project Overview

The backend enables real-time message delivery to thousands of concurrent users with minimal latency. Instead of WebSockets, **Server-Sent Events (SSE)** are used for efficient server-to-client broadcasting, which is well-suited for chat systems dominated by outbound message streams.

Key features include:
- Group-based chat with admin approval
- Scalable real-time message broadcasting
- Reliable message delivery under concurrent load
- Optimized database schema for group permissions and message history

---

## 🧩 Architecture

- **Framework:** Spring Boot
- **Messaging:** Server-Sent Events (SSE)
- **Database:** MySQL
- **API Style:** REST + streaming endpoints
- **Concurrency Model:** Non-blocking message broadcasting

### Why SSE?
While AI-generated boilerplate initially suggested WebSockets, SSE was chosen after architectural evaluation because:
- It is ideal for **server → client broadcast-heavy workloads**
- Lower overhead compared to full-duplex WebSockets
- Simpler client management
- Better scalability for large group broadcasts

---

## 🔑 Core Features

- **Group Membership Workflow**
  - Users request to join groups
  - Admins approve or reject requests
  - Only approved members receive messages

- **Real-Time Messaging**
  - Messages streamed via SSE to authorized users
  - Optimized broadcast paths to prevent unnecessary message delivery

- **Reliability Under Load**
  - Acknowledgment tracking for message delivery
  - Controlled broadcast routing to avoid over-publishing

- **Data Modeling**
  - Efficient schema for messages, groups, users, and permissions
  - Indexed queries for fast retrieval under concurrency

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Server-Sent Events (SSE)
- MySQL
- JPA / Hibernate

---

## ⚙️ Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Moon-12/chat-app-springboot
   cd chat-app-springboot
