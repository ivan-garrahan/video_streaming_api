# Video Streaming App API

By Ivan Garrahan
<p>ivan.garrahan@gmail.com</p>

## Overview

This API provides endpoints to manage users and process payments for a video streaming application.
Users can register, update their profiles, and make payments.

All source code is located in src/main/java, and all tests are found in src/tests

## Framework
- **Java Spring Framework:** This API was created using Java Spring. The framework was used to develop the RESTful endpoints and handle data validation.
- **JUnit** - This framework is used for conducting Unit tests, which can be found in the src/test folder.
### How to run

Included in the 'target' folder are the compiled java files, along with a **VideoStreamingApp-0.0.1-SNAPSHOT.jar.** file.

To run this jar file, use the following command (from this project folder):
- java -jar VideoStreamingApp-0.0.1-SNAPSHOT.jar

Additionally, to communicate with the API, the url is **http:/localhost:8080/users** or **http:/localhost:8080/payments** . 

Below are some cURL commands that can be used to interact with the API.

## Example cURL Requests

1. Create User:
  ```sh
  curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"user123","password":"Password123","email":"user@example.com","dob":"2000-01-01"}'
  ```

2. Get User by ID:
  ```sh
  curl -X GET http://localhost:8080/users/1
  ```

3. Process Payment:
  ```sh
  curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"creditCardNumber":"1234567812345678","amount":"100"}'
  ```


## Endpoints

### User Endpoints

#### 1. Create User
- Endpoint:POST /users
- Description:Creates a new user.
- Request Body:
  ```json
  {
    "username": "string",
    "password": "string",
    "email": "string",
    "dob": "YYYY-MM-DD",
    "ccn": "string (optional)"
  }
  ```
- Validation Rules:
  - `username`: Alphanumeric, no spaces.
  - `password`: Minimum 8 characters, at least one uppercase letter and one number.
  - `email`: Valid email format.
  - `dob`: Date in ISO 8601 format. User must be at least 18 years old.
  - `ccn`: Optional. If provided, must be exactly 16 digits.

#### 2. Get User by ID
- Endpoint:GET /api/users/{id}
- Description:Retrieves user details by ID.
- Response:
  ```json
  {
    "id": 1,
    "username": "string",
    "password": "string",
    "email": "string",
    "dob": "YYYY-MM-DD",
    "ccn": "string (optional)"
  }
  ```

#### 3. Update User
- Endpoint:PUT /api/users/{id}
- Description:Updates an existing user's details.
- Request Body:
  ```json
  {
    "username": "string",
    "password": "string",
    "email": "string",
    "dob": "YYYY-MM-DD",
    "ccn": "string (optional)"
  }
  ```

#### 4. Delete User
- Endpoint:DELETE /api/users/{id}
- Description:Deletes a user by ID.
- Response:Status code 204 (No Content) on success.

#### 5. Get All Users
- Endpoint:GET /api/users
- Description:Retrieves all users. Optionally filter by credit card status.
- Query Parameters:
  - `creditCard`: "Yes" to filter users with a credit card, "No" for those without a credit card, or omit for all users.
- Response:
  ```json
  [
    {
      "id": 1,
      "username": "string",
      "password": "string",
      "email": "string",
      "dob": "YYYY-MM-DD",
      "ccn": "string (optional)"
    }
  ]
  ```

### Payment Endpoints

#### 1. Process Payment
- Endpoint:POST /payments
- Description:Processes a payment request.
- Request Body:
  ```json
  {
    "creditCardNumber": "string",
    "amount": "string"
  }
  ```
- Validation Rules:
  - `creditCardNumber`: Must be 16 digits long. Must be registered.
  - `amount`: Must be between 1 and 3 digits.
- Responses:
  - 201 Created:Payment successfully processed.
  - 400 Bad Request:Validation error in request body.
  - 404 Not Found:Credit card number not registered.

## Error Handling
- 400 Bad Request:Returned when the request body fails validation.
- 404 Not Found:Returned when a requested resource (e.g., user or credit card) does not exist.
- 409 Conflict:Returned when attempting to create a user with a username that already exists.

## Notes
- Ensure you have the appropriate validation annotations in place to enforce the rules described.
- For security reasons, sensitive information like passwords should be hashed before storage in a production environment.