# Skeleton Spring Boot

## Project Description


Skeleton Spring Boot is a starter project designed to help you quickly set up a new Spring Boot application. It includes basic CRUD (Create, Read, Update, Delete) functionality and various useful dependencies to save you time from setting up from scratch.

The project also contains global exception handling with custom exceptions, a base repository interface for flexible data access, and a message service to support internationalization (i18n).
## Installation

### Prerequisites

- Java 17
- Maven
- Docker

### Steps

1. **Clone the repository**

    ```bash
    git clone <repository-url>
    cd skeletonSpringBoot
    ```

2. **Build the project**

    ```bash
    mvn clean install
    ```

3. **Run the project**

    ```bash
    mvn spring-boot:run
    ```

4. **Using Docker**

   To start the project with Docker, you can use the provided `docker-compose.yml` file:

    ```bash
    docker-compose up -d
    ```

   This will start a MySQL database and a PhpMyAdmin service.

## Usage

### Running the application

Once the application is running, you can access it at `http://localhost:8080`.

### API Endpoints

#### User Endpoints

- **Create a new user**

    ```http
    POST /api/v1/users
    ```

  **Request Body**:
    ```json
    {
      "username": "john.doe",
      "password": "P@ssw0rd"
    }
    ```

  **Response**:
    ```json
    {
      "message": "User created successfully.",
      "data": {
        "id": 1,
        "username": "john.doe",
        "createdAt": "2024-07-22T16:51:12.650060Z",
        "updatedAt": "2024-07-22T16:51:12.650060Z",
        "createdBy": "unknown",
        "updatedBy": null
      }
    }
    ```

- **Update an existing user**

    ```http
    PUT /api/v1/users
    ```

  **Request Body**:
    ```json
    {
      "id": 1,
      "password": "P@ssw0rd"
    }
    ```

  **Response**:
    ```json
    {
      "message": "User updated successfully.",
      "data": {
        "id": 1,
        "username": "john.doe",
        "createdAt": "2024-07-22T16:51:12.650060Z",
        "updatedAt": "2024-07-22T16:51:12.650060Z",
        "createdBy": "unknown",
        "updatedBy": "unknown"
      }
    }
    ```

- **Delete a user**

    ```http
    DELETE /api/v1/users?id=1
    ```

  **Response**:
    ```json
    {
      "message": "User deleted successfully."
    }
    ```

- **Get users**

    ```http
    GET /api/v1/users
    ```

  **Query Parameters**:
   - `id` (optional): Filter by id
   - `username` (optional): Filter by username
   - `createdAt` (optional): Filter by createdAt
   - `updatedAt` (optional): Filter by updatedAt
   - `createdBy` (optional): Filter by createdBy
   - `updatedBy` (optional): Filter by updatedBy
   - `page` (optional): Page number
   - `limit` (optional): Page size
   - `orderBy` (optional): Order by field
   - `orderDirection` (optional): Order direction

  **Response**:
    ```json
    {
      "message": "Users retrieved successfully.",
      "data": {
        "content": [
          {
             "id": 1,
             "username": "john.doe",
             "createdAt": "2024-07-22T16:59:43.631651Z",
             "updatedAt": "2024-07-22T16:59:43.631651Z",
             "createdBy": "unknown",
             "updatedBy": "unknown"
           }
          ],
          "pageable": {
            "pageNumber": 0,
            "pageSize": 10,
            "sort": {
              "empty": true,
              "unsorted": true,
              "sorted": false
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 1,
        "first": true,
        "size": 10,
        "number": 0,
        "sort": {
          "empty": true,
          "unsorted": true,
          "sorted": false
        },
        "numberOfElements": 1,
        "empty": false
      }
    }
    ```

### Postman Collection

For ease of testing and exploring the API, a Postman Collection is included in the repository. You can find the collection file at `User.postman_collection.json` in the root directory of the project.

To import the collection into Postman:
1. Open Postman.
2. Go to the "Import" option.
3. Select the `User.postman_collection.json` file from the repository.
4. Import and use the pre-configured requests to test the API endpoints.

## Contact

If you have any questions, please feel free to connect on LinkedIn at https://www.linkedin.com/in/lakatos-bence-669a03279.

## Continuous Integration

This project uses GitHub Actions for continuous integration. The workflow is defined in `.github/workflows/ci.yml` and includes the following steps:

- **Spotless**: Checks code formatting.
- **Build**: Builds the project with Maven.
- **Unit Tests**: Runs unit tests.
- **Integration Tests**: Runs integration tests.
- **SpotBugs**: Runs static code analysis with SpotBugs.

## Dependencies

This project uses the following dependencies:

- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Test
- MySQL Connector/J
- H2 Database (for testing)
- Spring Boot DevTools
- Lombok
- Spring WebMVC
- MapStruct
- Mockito
- SpotBugs Annotations
- Spring Boot Starter Validation

## Plugins

This project uses the following Maven plugins:

- Spring Boot Maven Plugin
- SpotBugs Maven Plugin
- Spotless Maven Plugin
- Maven Failsafe Plugin
