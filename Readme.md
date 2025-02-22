# User Management API

## Overview
The **User Management API** provides endpoints for managing user data, including retrieving users, filtering by role, sorting by age, and fetching users by ID or SSN. The API is built using **Spring Boot** and stores user data in an **H2 in-memory database**.

## Features
- Load users from an external API into the database
- Retrieve all users
- Get users by role
- Sort users by age
- Fetch user details by ID or SSN

## Technologies Used
- Java Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- OpenAPI (Swagger)

## API Endpoints
### 1. Home
**GET /api/users/home**  
Returns a welcome message.

### 2. Load Users
**POST /api/users/load**  
Fetches user data from an external API and loads it into the H2 database.

### 3. Get All Users
**GET /api/users**  
Retrieves a list of all users.

### 4. Get Users by Role
**GET /api/users/role/{role}**  
Retrieves users filtered by role/department.

### 5. Get Users Sorted by Age
**GET /api/users/sort?direction={asc|desc}**  
Retrieves users sorted by age in ascending or descending order.

### 6. Get User by ID
**GET /api/users/{id}**  
Fetches a user by their unique ID.

### 7. Get User by SSN
**GET /api/users/ssn/{ssn}**  
Fetches a user by their Social Security Number (SSN).

## Setup and Running the API

### Prerequisites
Ensure you have the following installed:
- Java 17 or later
- Maven
- Postman (optional, for testing API endpoints)

### Steps to Build and Run
1. **Clone the Repository**
   ```sh
   git clone https://github.com/kame-style/PS_H2DB_APIs_CRUD
   cd <project-directory>
   ```
2. **Build the Project**
   ```sh
   mvn clean install
   ```
3. **Run the Application**
   ```sh
   mvn spring-boot:run
   ```
4. **Access API Documentation**
   Open your browser and navigate to:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## Database Access
The application uses an in-memory H2 database. To access the database console:
1. Start the application.
2. Open a browser and go to:
   ```
   http://localhost:8080/h2-console
   ```
3. Enter the following details:
    - **JDBC URL:** `jdbc:h2:mem:testdb`
    - **Username:** `sa`
    - **Password:** *(leave blank)*

## Testing the API
You can test the API using:
- **Postman** by sending requests to `http://localhost:8080/api/users`
- **Swagger UI** for an interactive API documentation interface

## License
This project is open-source under the MIT License.

