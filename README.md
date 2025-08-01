#  Payment Reminder System

A Spring Boot-based web application that helps users keep track of their upcoming, due, and overdue payments. It features OTP-based authentication via email and uses JWT for securing API endpoints.

## Features

- **User Registration** using email and username  
-  **OTP-based Login** (No passwords)
- **OTP Verification** via Email
-  **JWT Authentication** for secure APIs
-  **Automated Payment Reminders** sent via Email:
  - 2 days before due date
  - On the due date
  - After due date (overdue)
-  Intelligent backend scheduling using Spring's cron jobs
-  Professional HTML email templates for reminders

---

##  Tech Stack

- **Backend**: Spring Boot, Java
- **Authentication**: OTP via Email, JWT (JSON Web Token)
- **Database**: MySQL  (configurable)
- **Scheduler**: Spring `@Scheduled` with Cron jobs
- **Email**: JavaMailSender (SMTP)

---

##  Authentication Flow

1. **Register** with your **email** and **username**
2. System sends an **OTP** to your registered email
3. Enter the **OTP** to **verify and login**
4. On successful login, a **JWT token** is generated and returned
5. Include the JWT token in the browser Cookie (HttpOnly) for all protected endpoints

---

##  Email Reminder Logic

The system sends emails at 9 AM daily for:

- **Upcoming Payments**: 2 days before the due date
- **Due Today Payments**: On the due date
- **Overdue Payments**: Past due date

This is done using a Spring `@Scheduled` cron job.

---

## Getting Started

Prerequisites
- **Java 17 or later**
- **Maven**
- **MySQL installed and running**
- **SMTP-enabled email (e.g. Gmail)**



First Clone the repository

1. Set up the MySQL database:

```bash
CREATE DATABASE payment_db;
```

2. Update application.yml:

- **Add your MySQL credentials**
- **Add your email SMTP details**
- **Add a secure JWT secret key**

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/payment_db
    username: root
    password: <your_sql_password>
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update

  mail:
    host: smtp.gmail.com
    port: 587
    username: <from_email_id>
    password: <app_password>
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
JWT_SECRET: <your secret key>
```
3. Run the project:
 ```bash
./mvnw spring-boot:run
```
4. Access the app:
 ```bash
http://localhost:8080/auth/register
```
## Testing Paymemt Reminder without Waiting for 9AM 
visit this after login

```bash
http://localhost:8080/test/reminder
```
