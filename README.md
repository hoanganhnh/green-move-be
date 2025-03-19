# Green move backend

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Description

Spring Boot Boilerplate is an advanced foundation designed to facilitate the development of robust, production-ready Spring Boot applications. This comprehensive project encompasses a cutting-edge technology stack, featuring Spring Boot (version 3.1.2), Spring Data JPA, Spring Validation, Spring Security with JWT Token support, MySQL integration, Mapstruct for seamless data mapping, Lombok for concise code generation, and Swagger for streamlined API documentation.

## Table of Contents

- [Installation](#installation)
- [Technology Stack](#technology-stack)
- [Local Development](#local-development)
- [Documentation](#documentation)
- [Application Architecture](#application-architecture-and-flow-logic)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Installation
```
$ git clone https://github.com/...
$ cd /app
```

Make sure you have docker and docker-compose installed [docker installation guide](https://docs.docker.com/compose/install/)
## Step 1: Configuration Setup
create ```.env``` file in root folder.
```
DB_ROOT_PASSWORD=mySecretRootPass
MYSQL_DATABASE=yourdbname
MYSQL_USER=yourdbusername
MYSQL_PASSWORD=dbpasswword
JWT_ISSUER=https://github.com/rimmelasghar
SWAGGER_CONTACT_MAIL=youremail
SWAGGER_CONTACT_URL=yourwebsite
SWAGGER_APP_NAME=Spring Boot Boilerplate Project
SWAGGER_APP_VERSION=2.0.0
SWAGGER_APP_LICENSE_URL=https://www.apache.org/licenses/LICENSE-2.0.html
SWAGGER_APP_LICENSE=Apache 2.0
```
This .env file contains the essential environment variables needed for your application to run.

## Step 2: Build Docker Images
Open a terminal or command prompt, navigate to your project's root folder, and run the following command to build the Docker images:
```
docker-compose build
```
This command will create Docker images based on the configurations defined in your docker-compose.yml file.
## Step 3: Start Application
After the Docker images are built, run the following command to start your application:
```
docker-compose up
```
Now, your application will be up and running. You can access it in your web browser at http://localhost:8000.


## Technology Stack

### Core Technologies

1. **Spring Boot 3.1.2**: The application is built using the latest version of Spring Boot, providing a solid foundation for developing robust and efficient Spring applications.

2. **Spring Data JPA**: Simplifies database access using the Java Persistence API (JPA) and provides easy-to-use repositories for interacting with the database.

3. **Spring Validation**: Implements data validation to ensure integrity and validity, making the application more reliable and secure.

4. **Spring Security + JWT Token**: Handles authentication and authorization using JSON Web Tokens (JWT) for secure token-based authentication.

5. **MySQL**: Configured as the backend database for persistent data storage.

### Development Tools

1. **Mapstruct**: Simplifies the mapping between DTOs (Data Transfer Objects) and entities, reducing boilerplate code and enhancing maintainability.

2. **Lombok**: Reduces Java code verbosity by providing annotations to automatically generate boilerplate code for getters, setters, constructors, etc.

3. **Swagger/OpenAPI**: Provides interactive API documentation that makes it easy for developers to understand and use the API endpoints.

4. **Docker & Docker Compose**: Containerizes the application and its dependencies for consistent development and deployment environments.

### Application Modules

1. **User Management**: Complete user authentication and authorization system with role-based access control.

2. **Vehicle Management**: Comprehensive API for managing vehicles with full CRUD operations.

3. **Location Management**: API for managing locations where vehicles are available.

4. **Rental Management**: System for managing vehicle rentals with status tracking.

5. **Review System**: Allows users to leave reviews for rentals they've completed.

6. **Payment Processing**: Handles payment transactions related to rentals.

These features collectively form a strong foundation for developing production-ready Spring Boot applications, saving development time and effort and ensuring best practices are followed throughout the development process.

## Local Development

This project supports hot reloading for a smoother development experience. Follow these steps to set up and use hot reloading:

### Setting Up Hot Reloading

1. **Start the Application in Development Mode**

   Use the provided script to start the application with hot reloading enabled:
   ```bash
   ./restart-dev.sh
   ```
   This will build and start your Docker containers with the proper configuration for hot reloading.

2. **Making Changes**

   When you make changes to your Java files in the `src` directory, Spring Boot DevTools will automatically detect these changes and restart the application. You don't need to manually rebuild or restart the container.

3. **Testing Hot Reloading**

   - Visit [http://localhost:8081/test](http://localhost:8081/test) in your browser
   - Modify the message in `DevToolsTestController.java`
   - Save the file
   - Refresh your browser to see the changes

### Troubleshooting Hot Reloading

If automatic hot reloading isn't working, you can use the `rebuild-app.sh` script:

```bash
./rebuild-app.sh
```

This script offers two options:

1. **Full rebuild and restart** - Completely rebuilds and restarts the application
2. **Trigger DevTools restart** - Triggers a quick restart without a full rebuild

### Accessing the Container

To access the container shell for debugging or running commands:

```bash
./start-app.sh shell
```

Inside the container, you can:
- Run Maven commands: `mvn clean compile`
- Restart the application: `pkill -f 'spring-boot:run' && mvn spring-boot:run`

### Important URLs

- **Application**: [http://localhost:8081](http://localhost:8081)
- **Swagger UI**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- **Database Admin (Adminer)**: [http://localhost:8082](http://localhost:8082)

## Documentation

### Swagger UI Interface

### API Request Example

## Application Architecture and Flow Logic

### Module Flow Logic

#### 1. Authentication Flow

- **Registration**: User submits registration details → Validation → User creation → JWT token generation → Response
- **Login**: User submits credentials → Authentication → JWT token generation → Response
- **Authorization**: JWT token validation → Role-based access control → Resource access

#### 2. Vehicle Management Flow

- **Create**: Admin submits vehicle details → Validation → Database insertion → Response
- **Retrieve**: Request received → Database query → Response with vehicle details
- **Update**: Admin submits updated details → Validation → Database update → Response
- **Delete**: Admin requests deletion → Database removal → Response

#### 3. Location Management Flow

- **Create/Update/Delete**: Similar flow to Vehicle Management, focused on location data
- **Retrieval**: Supports filtering by various location attributes

#### 4. Rental Management Flow

- **Create Rental**: User selects vehicle → Availability check → Rental creation → Payment initiation → Response
- **Update Rental Status**: Admin/System updates rental status → Database update → Notification
- **Complete Rental**: User returns vehicle → Status update → Payment completion → Review prompt

#### 5. Review System Flow

- **Submit Review**: User submits review for completed rental → Validation → Database insertion → Response
- **Retrieve Reviews**: Query by rental/user/vehicle → Database query → Response with review details

#### 6. Payment Processing Flow

- **Create Payment**: Initiated during rental creation → Payment method validation → Transaction processing → Status update
- **Update Payment Status**: System updates payment status → Database update → Rental status update if needed

### Data Flow Architecture

```text
Client Request → Controller → DTO Validation → Service Layer → Mapper → Repository → Database
                                                   ↑
                                                   ↓
Client Response ← Response DTO ← Mapper ← Service Layer
```

## API Endpoints

### Authentication API

- **Register**: `POST /register`
- **Login**: `POST /login`

### Users API

- **Get/Update/Delete User**: `GET/PUT/DELETE /users/{user_id}`

### Vehicles API

- **Create Vehicle**: `POST /vehicles`
  - Request Body: `{ "name": "Model S", "brand": "Tesla", "type": "Electric", "license_plate": "ABC123", "status": "Available", "location_id": 1, "price_per_day": 100 }`
- **Get Vehicle**: `GET /vehicles/{vehicle_id}`
- **Update Vehicle**: `PUT /vehicles/{vehicle_id}`
- **Delete Vehicle**: `DELETE /vehicles/{vehicle_id}`

### Locations API

- **Create Location**: `POST /locations`
  - Request Body: `{ "name": "Downtown", "address": "123 Main St" }`
- **Get Location**: `GET /locations/{location_id}`
- **Update Location**: `PUT /locations/{location_id}`
- **Delete Location**: `DELETE /locations/{location_id}`

### Rentals API

- **Create Rental**: `POST /rentals`
- **Get Rental**: `GET /rentals/{rental_id}`
- **Update Rental**: `PUT /rentals/{rental_id}`
- **Delete Rental**: `DELETE /rentals/{rental_id}`

### Reviews API

- **Create Review**: `POST /reviews`
- **Get Review**: `GET /reviews/{review_id}`
- **Update Review**: `PUT /reviews/{review_id}`
- **Delete Review**: `DELETE /reviews/{review_id}`

### Payments API

- **Create Payment**: `POST /payments`
- **Get Payment**: `GET /payments/{payment_id}`
- **Update Payment**: `PUT /payments/{payment_id}`
- **Delete Payment**: `DELETE /payments/{payment_id}`

## Contributing

To contribute to this project, follow these steps:

1. Fork this repository.
2. Create a new branch: `git checkout -b feature/your-feature`
3. Make your changes and commit them: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Submit a pull request.

## License

This project is licensed under the MIT License.

