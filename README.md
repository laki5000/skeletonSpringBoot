# Skeleton Spring Boot

## Project Description

This is a pre-configured Spring Boot skeleton application designed to streamline project setup and development. The primary focus is on establishing a robust structure through well-defined policies, utilizing interfaces and abstract classes. The project includes a message service that supports internationalization (i18n), a global exception handler with custom exceptions via RestControllerAdvice, and neatly organized constants for maintainability.

Additionally, the application features a powerful filtering mechanism using specifications for complex queries, along with comprehensive unit and integration tests to ensure code quality and reliability.

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

- **Get users**

    ```http
    POST /api/v1/users/get
    ```

  In this project, I use POST requests for retrieving data instead of the usual GET requests. This allows us to include a request body with complex filtering parameters and special operators.
  
  **Request Parameters**:

    - `page` (optional): The page number to retrieve (default: 0)
    - `limit` (optional): The number of elements per page (default: 10)
    - `orderBy` (optional): The field to order by (default: id)
    - `orderDirection` (optional): The direction of the ordering (default: asc)

  **The request body should be a list of DTOs (FilteringDTO) containing the following fields:**
    - field: The name of the field to filter.
    - operator: The filtering operator to apply.
    - value: The value to compare against.
    - otherValue: Required for the BETWEEN operator, representing the end value of the range.

  **The request body supports special operators for filtering:**
  - String fields: `EQUALS`, `CONTAINS`, `STARTS_WITH`, `ENDS_WITH`, `NOT_EQUAL`
  - Instant fields: `EQUALS`, `GREATER_THAN`, `LESS_THAN`, `BETWEEN`
  - Long fields: `EQUALS`, `GREATER_THAN`, `LESS_THAN`, `BETWEEN`

  **Request Body**:

    ```json
    [
      {
        "field": "id",
        "operator": "EQUALS",
        "value": "1"
      },
      {
        "field": "username",
        "operator": "EQUALS",
        "value": "john.doe"
      },
      {
        "field": "createdAt",
        "operator": "GREATER_THAN",
        "value": "2024-07-22T16:51:12.650060Z"
      },
      {
        "field": "updatedAt",
        "operator": "BETWEEN",
        "value": "2024-07-22T16:51:12.650060Z",
        "otherValue": "2024-08-22T16:51:12.650060Z"
      }
    ]
    ```

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
            "updatedBy": null
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

- **Update an existing user**

    ```http
    PATCH /api/v1/users/{id}
    ```

  **Path Parameters**:

    - `id` (mandatory): The id of the user to update

  **Request Body**:

    ```json
    {
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
    DELETE /api/v1/users/{id}
    ```

  **Path Parameters**:

    - `id` (mandatory): The id of the user to delete

  **Response**:

    ```json
    {
      "message": "User deleted successfully."
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

If you have any questions, please feel free to connect on LinkedIn at [https://www.linkedin.com/in/lakatos-bence-669a03279](https://www.linkedin.com/in/lakatos-bence-669a03279).

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
