# Job Application Tracker API

A backend REST API for tracking job applications, interview stages, and application status during a job search.

This project is built using **Java and Spring Boot** and provides a simple system for storing, searching, and managing
job applications through a RESTful API.

⚠️ **Status: Work in Progress**

This project is currently under active development. Additional features and improvements are planned.

---

## Features (Current)

- Create job applications
- Retrieve all applications
- Retrieve a specific application by ID
- Filter applications by:
    - company
    - role title
    - location
    - status
- Update application status
- Custom 404 handling for missing resources
- Database schema migrations using **Flyway**

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 / PostgreSQL
- Flyway
- Maven

---

## Project Structure

src/main/java/com/example/jobtracker

api → REST controllers
domain → entity classes
repository → JPA repositories
exception → custom exceptions
dto → request and response objects


---

## Running the Application

### Clone the repository

git clone https://github.com/amirhossein-alizad/job-tracker.git

cd job-tracker

### Build the project

mvn clean install

### Run the application

./mvnw spring-boot:run

The API will start on:

http://localhost:8080


---

## Example API Requests

### Create Application

`POST /applications`

Example request body:

```json
{
  "company": "Nutanix",
  "roleTitle": "Software Engineer",
  "location": "Vancouver, BC",
  "status": "APPLIED"
}
```

### Get All Applications

`GET /applications`

### Filter Applications

`GET /applications?company=Nutanix`
`GET /applications?status=INTERVIEW`
`GET /applications?location=Toronto`

Multiple filters can be combined:

`GET /applications?company=Nutanix&status=INTERVIEW`

---

## Database Migrations

Database schema changes are managed with Flyway.

Migration files are located in:

`src/main/resources/db/migration`

---

## Planned Features

The following improvements are planned:

```
Interview tracking
Application notes
Pagination and sorting
Authentication
Docker support
API documentation
Integration tests
Frountend interface
```

---

## Purpose

This project was created as a backend learning and portfolio project to practice:

REST API design

Spring Boot architecture

Database schema management

Backend system development