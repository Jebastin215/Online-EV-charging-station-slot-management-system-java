# ⚡ EV Charging Station Management System

A full-stack web application for managing Electric Vehicle (EV) charging infrastructure. The platform connects **EV owners** with **charging station providers**, enabling real-time slot booking, charging session management, multi-mode payments, and administrative oversight.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Database Schema](#-database-schema)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [🚀 Getting Started Guide](#-getting-started-guide)
- [⚡ Quick Start (Automated)](#-quick-start-automated)
- [User Roles & Workflows](#-user-roles--workflows)
- [API / Servlet Mapping](#-api--servlet-mapping)
- [Screenshots](#-screenshots)
- [Future Enhancements](#-future-enhancements)
- [Author](#-author)

---

## ✨ Features

### 🚗 For EV Users (Customers)

- **Station Discovery** — Search charging stations by location with distance calculation
- **Real-time Slot Availability** — View free/occupied slots with live status indicators
- **Advance Booking** — Reserve specific slots for a chosen date and time
- **Multiple Charging Plans** — Choose from Level 1, Level 2, or DC Fast Charging
- **Dual Payment Modes** — Pay via Bank (OTP-verified) or Cash
- **Booking History** — Track all past and current bookings with status badges

### 🏪 For Station Owners

- **Station Registration** — Register with location, charger count, and contact details
- **Slot Management** — Visual grid showing real-time slot occupancy
- **Charging Control** — Start/stop charging sessions for vehicles
- **Cash Payment Verification** — Manually confirm cash payments received
- **Revenue Reports** — View detailed booking and revenue reports

### 🛡️ For Administrators

- **Station Approval** — Approve or reject new station registrations
- **Platform Monitoring** — Oversee all stations, bookings, and users
- **System Management** — Manage station listings and configurations

### 🎨 UI/UX Highlights

- **Electric Dark Theme** — Premium neon cyan/green glassmorphism design
- **Responsive Design** — Bootstrap 4 grid, mobile-friendly layout
- **Smooth Animations** — GPU-accelerated fade-in transitions and hover effects
- **Interactive Plan Cards** — Clickable charging plan selection with visual feedback
- **Status Badges** — Color-coded indicators (🟢 Charged, 🔵 Charging, 🟡 Waiting, 🔴 Not Charging)
- **Password Security** — Visibility toggle + strong password validation (8+ chars, uppercase, lowercase, number, special char)

---

## 🛠️ Tech Stack

| Layer                  | Technology                          | Version  |
| ---------------------- | ----------------------------------- | -------- |
| **Language**           | Java                                | 17 (LTS) |
| **Backend**            | Jakarta Servlet API                 | 6.0.0    |
| **Frontend Views**     | Jakarta Server Pages (JSP)          | 3.1.1    |
| **Template Engine**    | JSTL (Jakarta Standard Tag Library) | 3.0.0    |
| **CSS Framework**      | Bootstrap                           | 4.x      |
| **Custom Styling**     | Vanilla CSS (Electric Theme)        | —        |
| **Database**           | MySQL                               | 8.x      |
| **JDBC Driver**        | MySQL Connector/J                   | 8.3.0    |
| **Application Server** | Apache Tomcat (via Cargo Plugin)    | 10.1.16  |
| **Build Tool**         | Apache Maven                        | 3.9.6    |
| **Packaging**          | WAR (Web Application Archive)       | —        |

### Frameworks & Libraries Used

| Library                     | Purpose                                           |
| --------------------------- | ------------------------------------------------- |
| **Jakarta Servlet 6.0**     | Request handling, session management, URL routing |
| **Jakarta JSP 3.1**         | Server-side HTML rendering with embedded Java     |
| **JSTL 3.0**                | Template logic (`c:forEach`, `c:if`, `c:choose`)  |
| **Bootstrap 4**             | Responsive grid, components, navigation           |
| **jQuery 3.x**              | DOM manipulation, AJAX, event handling            |
| **AOS (Animate On Scroll)** | Scroll-triggered animations                       |
| **Owl Carousel**            | Image carousels and sliders                       |
| **Stellar.js**              | Parallax scrolling effects                        |
| **Open Iconic**             | Icon font set                                     |
| **Magnific Popup**          | Lightbox and popup dialogs                        |
| **Google Maps API**         | Station location mapping                          |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                      CLIENT (Browser)                   │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐              │
│  │   JSP    │  │Bootstrap │  │  jQuery  │              │
│  │  Views   │  │  4 CSS   │  │   + JS   │              │
│  └────┬─────┘  └──────────┘  └──────────┘              │
│       │  HTTP Request/Response                          │
├───────┼─────────────────────────────────────────────────┤
│       ▼                                                 │
│  ┌─────────────────────────────────────┐                │
│  │     Apache Tomcat 10.1.16           │                │
│  │  ┌──────────────────────────────┐   │                │
│  │  │    Java Servlets (29)        │   │                │
│  │  │  ┌────────────────────────┐  │   │                │
│  │  │  │   DBConnection.java   │  │   │  APPLICATION   │
│  │  │  │    (JDBC Utility)     │  │   │    SERVER      │
│  │  │  └────────┬───────────┘  │  │   │                │
│  │  └───────────┼──────────────┘  │   │                │
│  └──────────────┼─────────────────┘   │                │
│                 │  JDBC                                  │
├─────────────────┼───────────────────────────────────────┤
│                 ▼                                        │
│  ┌──────────────────────────┐                           │
│  │       MySQL 8.x          │                           │
│  │  ┌────────────────────┐  │                           │
│  │  │  ev_charging (DB)  │  │       DATABASE            │
│  │  │  • ev_register     │  │                           │
│  │  │  • ev_station      │  │                           │
│  │  │  • ev_booking      │  │                           │
│  │  │  • ev_slot          │  │                           │
│  │  │  • ev_admin         │  │                           │
│  │  └────────────────────┘  │                           │
│  └──────────────────────────┘                           │
└─────────────────────────────────────────────────────────┘
```

### Design Pattern: **MVC (Model-View-Controller)**

| Component      | Implementation                                             |
| -------------- | ---------------------------------------------------------- |
| **Model**      | JDBC queries within Servlets + `DBConnection.java` utility |
| **View**       | JSP pages with JSTL tags (`/webapp/jsp/`)                  |
| **Controller** | Java Servlets handling HTTP requests (`/servlet/`)         |

---

## 🗄️ Database Schema

**Database:** `ev_charging` (MySQL 8.x, InnoDB Engine, UTF-8)

### Tables

#### `ev_register` — EV User Accounts

| Column      | Type         | Description             |
| ----------- | ------------ | ----------------------- |
| `id`        | INT (PK, AI) | Unique user ID          |
| `name`      | VARCHAR(255) | Full name               |
| `address`   | VARCHAR(255) | Residential address     |
| `mobile`    | VARCHAR(255) | Phone number            |
| `email`     | VARCHAR(255) | Email address           |
| `account`   | VARCHAR(255) | Bank account number     |
| `card`      | VARCHAR(255) | Card number             |
| `bank`      | VARCHAR(255) | Bank name               |
| `amount`    | DOUBLE       | Wallet balance          |
| `uname`     | VARCHAR(255) | Login username          |
| `pass`      | VARCHAR(255) | Login password          |
| `latitude`  | VARCHAR(255) | User location latitude  |
| `longitude` | VARCHAR(255) | User location longitude |

#### `ev_station` — Charging Station Details

| Column           | Type         | Description                                 |
| ---------------- | ------------ | ------------------------------------------- |
| `id`             | INT (PK, AI) | Unique station ID                           |
| `name`           | VARCHAR(255) | Station name                                |
| `stype`          | VARCHAR(255) | Station type (AC/DC)                        |
| `num_charger`    | INT          | Number of charging slots                    |
| `area`           | VARCHAR(255) | Station area                                |
| `city`           | VARCHAR(255) | City                                        |
| `lat` / `lon`    | VARCHAR(255) | GPS coordinates                             |
| `uname` / `pass` | VARCHAR(255) | Login credentials                           |
| `status`         | INT          | Approval status (0 = Pending, 1 = Approved) |
| `distance`       | DOUBLE       | Computed distance from user                 |

#### `ev_booking` — Booking & Charging Records

| Column            | Type         | Description                                     |
| ----------------- | ------------ | ----------------------------------------------- |
| `id`              | INT (PK, AI) | Unique booking ID                               |
| `uname`           | VARCHAR(255) | Booked by (username)                            |
| `station`         | VARCHAR(255) | Station name                                    |
| `carno`           | VARCHAR(255) | Vehicle registration number                     |
| `slot`            | INT          | Assigned slot number                            |
| `plan`            | INT          | Charging plan (1 / 2 / 3)                       |
| `amount`          | DOUBLE       | Payment amount                                  |
| `rdate` / `rtime` | VARCHAR      | Reservation date & time                         |
| `edate` / `etime` | VARCHAR      | End date & time                                 |
| `otp`             | VARCHAR(255) | Generated OTP for bank payment                  |
| `charge_st`       | INT          | Charging status (1=Pending, 2=Charging, 3=Done) |
| `pay_mode`        | VARCHAR(255) | Payment mode (Bank / Cash)                      |
| `pay_st`          | INT          | Payment status (1=Unpaid, 2=Paid)               |
| `status`          | INT          | Booking status (1=Active, 3=History)            |

#### `ev_slot` — Slot Availability Tracker

| Column    | Type         | Description           |
| --------- | ------------ | --------------------- |
| `id`      | INT (PK, AI) | Unique slot record ID |
| `station` | VARCHAR(255) | Parent station name   |
| `slot`    | INT          | Slot number           |

#### `ev_admin` — Admin Credentials

| Column     | Type              | Description    |
| ---------- | ----------------- | -------------- |
| `username` | VARCHAR(255) (PK) | Admin username |
| `password` | VARCHAR(255)      | Admin password |

---

## 📁 Project Structure

```
ev_charging_java/
├── pom.xml                          # Maven build configuration
├── database.sql                     # Database schema & seed data
├── setup_maven.ps1                  # PowerShell setup script
│
├── src/main/
│   ├── java/com/evcharging/
│   │   ├── servlet/                 # 29 Servlet Controllers
│   │   │   ├── IndexServlet.java        # Landing page
│   │   │   ├── LoginServlet.java        # User authentication
│   │   │   ├── Login2Servlet.java       # Station owner login
│   │   │   ├── LoginAdminServlet.java   # Admin login
│   │   │   ├── RegisterServlet.java     # User registration
│   │   │   ├── RegStationServlet.java   # Station registration
│   │   │   ├── UserHomeServlet.java     # User dashboard
│   │   │   ├── StationServlet.java      # Station search by location
│   │   │   ├── SlotServlet.java         # Slot availability check
│   │   │   ├── BookServlet.java         # Slot booking
│   │   │   ├── SelectServlet.java       # Charging plan selection
│   │   │   ├── PaymentServlet.java      # Payment processing
│   │   │   ├── VerifyOtpServlet.java    # OTP verification
│   │   │   ├── Charge1Servlet.java      # Start charging session
│   │   │   ├── Charge2Servlet.java      # Stop charging session
│   │   │   ├── HistoryServlet.java      # Booking history
│   │   │   ├── ReportServlet.java       # Station reports
│   │   │   ├── ViewServlet.java         # View bookings (owner)
│   │   │   ├── AdminServlet.java        # Admin panel
│   │   │   ├── HomeServlet.java         # Station owner home
│   │   │   ├── TariffServlet.java       # Tariff display
│   │   │   ├── MapServlet.java          # Map view
│   │   │   ├── DeleteServlet.java       # Delete booking
│   │   │   ├── Page2Servlet.java        # Reschedule handler
│   │   │   ├── Page3Servlet.java        # Post-reschedule redirect
│   │   │   ├── PageServlet.java         # Generic page handler
│   │   │   ├── BlogServlet.java         # Blog page
│   │   │   ├── ContactServlet.java      # Contact page
│   │   │   └── LogoutServlet.java       # Session invalidation
│   │   │
│   │   └── util/
│   │       └── DBConnection.java        # JDBC connection utility
│   │
│   └── webapp/
│       ├── WEB-INF/web.xml              # Deployment descriptor
│       ├── css/
│       │   └── style.css                # Main stylesheet (Electric Theme)
│       ├── js/                          # JavaScript libraries
│       ├── images/                      # Static images
│       └── jsp/                         # 29 JSP View pages
│           ├── index.jsp                    # Landing page
│           ├── login.jsp                    # User login form
│           ├── login2.jsp                   # Station login form
│           ├── login_admin.jsp              # Admin login form
│           ├── register.jsp                 # User registration form
│           ├── reg_station.jsp              # Station registration form
│           ├── userhome.jsp                 # User dashboard
│           ├── station.jsp                  # Station search results
│           ├── slot.jsp                     # Slot grid view
│           ├── book.jsp                     # Booking form
│           ├── book1.jsp                    # Booking confirmation
│           ├── select.jsp                   # Charging plan cards
│           ├── payment.jsp                  # Payment gateway
│           ├── verify_otp.jsp               # OTP verification
│           ├── paid.jsp                     # Payment success
│           ├── history.jsp                  # Booking history
│           ├── report.jsp                   # Station reports
│           ├── tariff.jsp                   # Tariff pricing table
│           ├── admin.jsp                    # Admin panel
│           ├── home.jsp                     # Station owner home
│           ├── view.jsp                     # Booking viewer
│           ├── charge1.jsp / charge2.jsp    # Charging controls
│           ├── page2.jsp                    # Reschedule modal
│           └── map.jsp / contact.jsp        # Other pages
│
└── tools/
    └── apache-maven-3.9.6/              # Bundled Maven installation
```

---

## 📦 Prerequisites

Before running this project, ensure you have the following installed:

| Requirement      | Version         | Download                                                                                             |
| ---------------- | --------------- | ---------------------------------------------------------------------------------------------------- |
| **Java JDK**     | 17 or higher    | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) / [OpenJDK](https://adoptium.net/) |
| **MySQL Server** | 8.0+            | [MySQL Downloads](https://dev.mysql.com/downloads/mysql/)                                            |
| **Apache Maven** | 3.9.x (bundled) | Included in `tools/` directory                                                                       |
| **Git**          | Latest          | [Git Downloads](https://git-scm.com/downloads)                                                       |

### Environment Variables

Ensure the following are set in your system:

```bash
JAVA_HOME = C:\Program Files\Java\jdk-17    # or your JDK path
PATH += %JAVA_HOME%\bin
```

---

## 🚀 Getting Started Guide

Follow these steps to set up and run the project locally on your machine.

### Step 1: Database Setup

1.  Open your MySQL terminal or a GUI tool (like MySQL Workbench).
2.  Run the following command to import the database schema and seed data:
    ```bash
    mysql -u root -p < database.sql
    ```
    _This creates the `ev_charging` database and the default admin account._

### Step 2: Database Configuration

Update the JDBC connection details to match your MySQL setup.
Edit `src/main/java/com/evcharging/util/DBConnection.java`:

```java
// Line 10-11
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password";
```

### Step 3: Build the Project

Use the bundled Maven to package the application into a WAR file.

```powershell
# Windows (using bundled Maven)
.\tools\apache-maven-3.9.6\bin\mvn.cmd clean package -DskipTests

# Using system Maven (if installed)
mvn clean package -DskipTests
```

### Step 4: Run the Application

Start the embedded Tomcat server using the Cargo plugin.

```powershell
# Windows (using bundled Maven)
.\tools\apache-maven-3.9.6\bin\mvn.cmd cargo:run

# Using system Maven (if installed)
mvn cargo:run
```

Once the server starts, access the application at:
👉 **[http://localhost:8080/ev-charging/](http://localhost:8080/ev-charging/)**

---

## ⚡ Quick Start (Automated)

For Windows users, you can use the provided PowerShell script to automate the entire process (Download Maven -> Build -> Run):

```powershell
.\setup_maven.ps1
```

---

## Default Login Credentials

| Role          | Username                 | Password |
| ------------- | ------------------------ | -------- |
| Admin         | `admin`                  | `admin`  |
| User          | _(Register via the app)_ | —        |
| Station Owner | _(Register via the app)_ | —        |

## cmd to change admin pw:INSERT IGNORE INTO `ev_admin` (`username`, `password`) VALUES ('admin', 'admin');

## 👥 User Roles & Workflows

### 🚗 EV User Flow

```
Register → Login → Search Station → View Slots → Book Slot
→ Select Plan (Level 1/2/3) → Arrive at Station → Charging Completes
→ Pay (Bank+OTP / Cash) → View History
```

Note:(Registration pw should be more than 8 letters with one number and one alphabet and one special character)

### 🏪 Station Owner Flow

```
Register → Wait for Admin Approval → Login → View Slot Grid
→ Start Charge (when car arrives) → Stop Charge (when done)
→ Verify Cash Payment (if applicable) → View Reports
```

### 🛡️ Admin Flow

```
Login → View Pending Stations → Approve/Reject → Monitor Bookings
→ Delete Invalid Entries
```

---

## 🔗 API / Servlet Mapping

| URL Pattern    | Servlet             | Method   | Description           |
| -------------- | ------------------- | -------- | --------------------- |
| `/`            | `IndexServlet`      | GET      | Landing page          |
| `/login`       | `LoginServlet`      | GET/POST | User authentication   |
| `/login2`      | `Login2Servlet`     | GET/POST | Station owner login   |
| `/login_admin` | `LoginAdminServlet` | GET/POST | Admin login           |
| `/register`    | `RegisterServlet`   | GET/POST | User registration     |
| `/reg_station` | `RegStationServlet` | GET/POST | Station registration  |
| `/userhome`    | `UserHomeServlet`   | GET      | User dashboard        |
| `/station`     | `StationServlet`    | GET/POST | Station search        |
| `/slot`        | `SlotServlet`       | GET      | View available slots  |
| `/book`        | `BookServlet`       | GET/POST | Create booking        |
| `/select`      | `SelectServlet`     | GET/POST | Choose charging plan  |
| `/payment`     | `PaymentServlet`    | GET/POST | Process payment       |
| `/verify_otp`  | `VerifyOtpServlet`  | GET/POST | OTP verification      |
| `/history`     | `HistoryServlet`    | GET      | Booking history       |
| `/home`        | `HomeServlet`       | GET      | Station owner home    |
| `/view`        | `ViewServlet`       | GET      | View bookings (owner) |
| `/report`      | `ReportServlet`     | GET      | Station reports       |
| `/charge1`     | `Charge1Servlet`    | GET      | Start charging        |
| `/charge2`     | `Charge2Servlet`    | GET      | Stop charging         |
| `/tariff`      | `TariffServlet`     | GET      | Tariff plans          |
| `/admin`       | `AdminServlet`      | GET/POST | Admin panel           |
| `/logout`      | `LogoutServlet`     | GET      | Session logout        |

---

## 🎨 Screenshots

> _After running the application, visit the following pages to see the Electric Theme in action:_

| Page            | URL                                         |
| --------------- | ------------------------------------------- |
| Landing Page    | `http://localhost:8080/ev-charging/`        |
| User Login      | `http://localhost:8080/ev-charging/login`   |
| Station Search  | `http://localhost:8080/ev-charging/station` |
| Tariff Plans    | `http://localhost:8080/ev-charging/tariff`  |
| Booking History | `http://localhost:8080/ev-charging/history` |

---

## 🔮 Future Enhancements

- [ ] **Real SMS Integration** — Replace dev-mode OTP with Twilio/MSG91 SMS gateway
- [ ] **Google Maps Integration** — Interactive station map with markers
- [ ] **Email Notifications** — Booking confirmations and payment receipts
- [ ] **Admin Analytics Dashboard** — Charts for revenue, usage trends
- [ ] **Mobile App** — Android/iOS companion app
- [ ] **QR Code Check-in** — Scan to start charging
- [ ] **Rate Limiting & Security** — BCrypt password hashing, CSRF tokens
- [ ] **Docker Support** — Containerized deployment with Docker Compose
- [ ] **REST API Layer** — JSON endpoints for mobile/frontend integration

---

## 🧪 Interview Q&A Cheat Sheet

<details>
<summary><strong>Click to expand common interview questions about this project</strong></summary>

### Q: What is the architecture of this project?

**A:** It follows the **MVC (Model-View-Controller)** pattern. Servlets act as Controllers handling HTTP requests, JSP pages serve as the View layer for rendering HTML, and the Model layer uses direct JDBC calls to a MySQL database through a centralized `DBConnection` utility class.

### Q: Why did you choose Servlets over Spring Boot?

**A:** This project demonstrates a deep understanding of how web frameworks work at the fundamental level — raw HTTP request handling, session management, JDBC connections, and URL routing — without relying on Spring Boot's auto-configuration. This shows core Java competency.

### Q: How does the payment system work?

**A:** The system supports two payment modes: **Bank** (OTP-verified) and **Cash**. For bank payments, a random 4-digit OTP is generated server-side and stored in the database. The user enters the OTP to complete the transaction. For cash, the station owner manually verifies receipt.

### Q: How do you handle session management?

**A:** Using Jakarta Servlet's `HttpSession`. After successful login, user data is stored in the session. Each request checks for valid session attributes to enforce authentication. Logout invalidates the session.

### Q: How is slot availability calculated?

**A:** The `SlotServlet` queries `ev_booking` for active bookings on the current date, aggregates occupied slots, and compares against the station's total `num_charger` count to determine free slots.

### Q: What security measures are implemented?

**A:** Client-side strong password validation (min 8 chars, uppercase, lowercase, number, special character), password visibility toggle, OTP-based payment verification, and session-based authentication.

### Q: How is the UI designed?

**A:** Custom **"Electric Theme"** built with Vanilla CSS featuring a dark mode base, neon cyan/green accents, glassmorphism cards, GPU-accelerated animations, and Bootstrap 4 responsive grid.

</details>

---

## 👨‍💻 Author

**Jebastin Augustin Ponraj**

- 📧 Email: [jebastin215@gmail.com](mailto:jebastin215@gmail.com)
- 📱 Phone: +91 9600715505

---

## 📄 License

This project is developed for educational purposes. The frontend template is based on [Flavor Theme by Colorlib](https://colorlib.com) (CC BY 3.0 License).

---

<p align="center">
  <strong>⚡ Built with Java • Powered by Jakarta EE • Styled with Electric Theme ⚡</strong>
</p>
