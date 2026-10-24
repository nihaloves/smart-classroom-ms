# Smart Classroom Management System (SCMS)

A Java-based desktop application designed to simplify classroom management by providing separate interfaces for students and teachers. The system uses Java Swing for its graphical user interface and MySQL for persistent data storage.

## Team Members

- Steevo C S
- KrishnaDev
- Niharika K Thilak

## Features

### Authentication
- Separate student and teacher login
- Role-based dashboard access
- Password hashing using PBKDF2 with HMAC-SHA256
- Automatic migration of existing plaintext passwords after successful login

### Student Dashboard
- View personal profile details
- View attendance records
- View marks and results
- Browse assignments
- View available courses and syllabus modules
- View scheduled class sessions

### Teacher Dashboard
- View and refresh the student list
- Record, view, and delete attendance records
- Record, view, and delete marks
- Create, view, and delete assignments
- Schedule, view, and delete class sessions

### Database Management
- MySQL database integration through JDBC
- Parameterized SQL queries using `PreparedStatement`
- Separate data access classes for database operations

## Technologies Used

- **Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Database connectivity:** JDBC
- **Concepts:** Object-Oriented Programming, inheritance, abstraction, encapsulation, polymorphism, collections
- **Development tools:** Git, GitHub, Visual Studio Code / Java IDE

## Project Structure

```text
SCMS/
├── src/
│   ├── model/
│   ├── service/
│   ├── database/
│   ├── ui/
│   └── Main.java
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── bin/
└── README.md
```

- `model/` — represents entities such as students, teachers, courses, and assignments.
- `service/` — contains service-layer classes for application operations.
- `database/` — contains database connection and data access classes.
- `ui/` — contains the Swing login screen and student/teacher dashboards.

## Requirements

- Java JDK 21 or compatible version
- MySQL Server 8.x
- MySQL Connector/J
- Git (optional, for cloning the repository)

## Database Setup

1. Start your MySQL server.
2. Create a database named `scms`.
3. Create the required tables using the project's SQL schema.
4. Configure the database credentials through the environment variable `SCMS_DB_PASSWORD`.

Set the password in PowerShell before launching the application:

```powershell
$env:SCMS_DB_PASSWORD = "YOUR_MYSQL_PASSWORD"
```

Replace the placeholder with your local MySQL password. Do not commit passwords or other secrets to GitHub.

## Compile and Run

### 1. Configure the database

- Install and start MySQL Server.
- Create the `scms` database and its required tables.
- Set the `SCMS_DB_PASSWORD` environment variable to your MySQL password.

In PowerShell:

```powershell
$env:SCMS_DB_PASSWORD = "YOUR_MYSQL_PASSWORD"
```

Replace the placeholder with your actual local password. Do not commit credentials to GitHub.

### 2. Compile the project

Open PowerShell in the project root directory and run:

```powershell
$files = Get-ChildItem src -Recurse -Filter *.java |
    ForEach-Object { $_.FullName }

javac -cp "lib/*;bin" -d bin $files
```

### 3. Launch the application

Start the graphical login interface:

```powershell
java -cp "lib/*;bin" ui.LoginFrame
```

After launching, select the appropriate role and enter valid credentials.

**Note:** `Main.java` runs the original object-oriented programming demonstration. Use `ui.LoginFrame` to launch the interactive SCMS application.


## Security Notes

- Passwords for newly created accounts are stored as salted PBKDF2 hashes.
- Existing plaintext accounts are upgraded when they successfully log in.
- Database credentials should be supplied locally through environment variables.
- Never commit real credentials, private keys, or production account passwords.

## Current Scope and Limitations

- Student and teacher dashboards use database-backed records.
- Course enrollment relationships and student-specific filtering of assignments and class sessions are not yet implemented.
- The project is a desktop application intended for academic demonstration, not a production-ready school management platform.

## Future Improvements

- Add course enrollment and student-specific assignment/class filtering.
- Add an announcements module.
- Improve account creation and password reset workflows.
- Add stronger validation, testing, and database transaction handling.
- Add screenshots and a demonstration guide.

## Contributors

Developed as an academic Object-Oriented Programming project by the team listed above.
