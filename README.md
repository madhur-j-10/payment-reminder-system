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

##  Cron Job Setup

```java
@Scheduled(cron = "0 0 9 * * ?") // Runs every day at 9 AM
public void sendDailyReminders() {
    paymentService.sendPaymentReminders();
}
