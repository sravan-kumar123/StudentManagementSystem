# Student Management System

A desktop application for student registration and management, built with Java Swing and backed by an Oracle database. It follows the MVC pattern and supports role-based login for Admin and Faculty users.

## Features

- Add, update, delete and search student records
- Role-based authentication (Admin / Faculty)
  - Admin: full access — add, edit, delete and view students
  - Faculty: read-only access — view and search students only
- Form validation (email format, 10-digit phone numbers, marks range)
- MVC architecture with a separate DAO layer for all JDBC calls

## Tech Stack

- Java (Swing) for the UI
- JDBC for database connectivity
- Oracle Database
- MVC architecture

## Project Structure

```
StudentManagementSystem/
├── src/
│   ├── Main.java                  # Application entry point
│   ├── model/
│   │   ├── Student.java
│   │   └── User.java
│   ├── dao/
│   │   ├── StudentDAO.java        # CRUD for students table
│   │   └── UserDAO.java           # Login lookups
│   ├── controller/
│   │   ├── StudentController.java # Validation + student ops
│   │   └── LoginController.java   # Auth + session state
│   ├── view/
│   │   ├── LoginView.java
│   │   ├── DashboardView.java
│   │   ├── StudentListView.java
│   │   └── StudentFormView.java
│   └── util/
│       └── DBConnection.java      # JDBC connection helper
├── sql/
│   └── schema.sql                 # Table + sample data setup
├── lib/                           # put ojdbc jar here (not committed)
├── .gitignore
└── README.md
```

## Setup

### 1. Install prerequisites
- JDK 8 or higher
- Oracle Database (XE or any edition) running locally or remotely
- Oracle JDBC driver (`ojdbc8.jar` or later) — download from the
  [Oracle JDBC driver downloads page](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html)
  and drop it into the `lib/` folder

### 2. Set up the database
Run the script in `sql/schema.sql` against your Oracle instance to create the `students` and `users` tables, along with a couple of sample logins:

| Username | Password    | Role    |
|----------|-------------|---------|
| admin    | admin123    | ADMIN   |
| faculty1 | faculty123  | FACULTY |

> Change these credentials before using the app for anything beyond local testing.

### 3. Configure the connection
Edit `src/util/DBConnection.java` and update the URL, username and password to match your Oracle setup:

```java
private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
private static final String USERNAME = "system";
private static final String PASSWORD = "your_password";
```

### 4. Compile and run

**From the command line:**
```bash
javac -cp "lib/ojdbc8.jar" -d bin src/Main.java src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java src/util/*.java
java -cp "bin:lib/ojdbc8.jar" Main
```
(On Windows use `;` instead of `:` in the classpath.)

**From an IDE (IntelliJ / Eclipse / NetBeans):**
1. Open the project folder
2. Add `lib/ojdbc8.jar` to the project's build path / libraries
3. Run `Main.java`

## Notes

- Passwords in the `users` table are stored as plain text for simplicity. In a production system these should be hashed (e.g. with BCrypt).
- The connection details in `DBConnection.java` are hardcoded for ease of setup — for anything beyond a class project, move these to a config file or environment variables.
