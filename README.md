![](https://github.com/rimmelasghar/SpringBoot-boilerPlate/blob/main/imgs/springboot-boilerplate.jpg)

# Spring-Boot BoilerPlate

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Description

Spring Boot Boilerplate is an advanced foundation designed to facilitate the development of robust, production-ready Spring Boot applications. This comprehensive project encompasses a cutting-edge technology stack, featuring Spring Boot (version 3.1.2), Spring Data JPA, Spring Validation, Spring Security with JWT Token support, MySQL integration, Mapstruct for seamless data mapping, Lombok for concise code generation, and Swagger for streamlined API documentation.

## Table of Contents

- [Installation](#installation)
- [Features](#features)
- [Local Development](#local-development)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Installation
```
$ git clone https://github.com/rimmelasghar/SpringBoot-boilerPlate.git
$ cd SpringBoot-boilerPlate
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


## Features

1. **Spring Boot 3.1.2**: The application is built using the latest version of Spring Boot, providing a solid foundation for developing robust and efficient Spring applications.

2. **Spring Data JPA**: Spring Data JPA simplifies database access using the Java Persistence API (JPA) and provides easy-to-use repositories for interacting with the database.

3. **Spring Validation**: The application implements Spring Validation to ensure data integrity and validity, making it more reliable and secure.

4. **Spring Security + JWT Token**: Spring Security is integrated into the application to handle authentication and authorization. It uses JSON Web Tokens (JWT) for secure token-based authentication.

5. **MySQL**: The application is configured to use MySQL as the backend database, allowing for persistent data storage.

6. **Mapstruct**: Mapstruct is used to simplify the mapping between DTOs (Data Transfer Objects) and entities, reducing boilerplate code and enhancing maintainability.

7. **Lombok**: Lombok reduces the verbosity of Java code by providing annotations to automatically generate boilerplate code for getters, setters, constructors, etc.

8. **Swagger**: The application includes Swagger, a powerful tool for documenting and testing APIs. Swagger UI provides an interactive API documentation that makes it easy for developers to understand and use the API endpoints.

9. **Vehicle and Location Management**: The application includes a comprehensive API for managing vehicles and locations, with full CRUD operations for both resources.

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

   - Visit http://localhost:8081/test in your browser
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

- **Application**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui/index.html
- **Database Admin (Adminer)**: http://localhost:8082

## Documentation
- Swagger UI:
  ![](https://github.com/rimmelasghar/SpringBoot-boilerPlate/blob/main/imgs/swagger-1.jpg)
- Get Request:
  ![](https://github.com/rimmelasghar/SpringBoot-boilerPlate/blob/main/imgs/swagger-2.jpg)

## API Endpoints

### Vehicles API

- **Create Vehicle**: `POST /vehicles`
  - Request Body: `{ "name": "Model S", "brand": "Tesla", "type": "Electric", "license_plate": "ABC123", "status": "Available", "location_id": 1, "price_per_day": 100 }`

- **Get Vehicle**: `GET /vehicles/{vehicle_id}`

- **Update Vehicle**: `PUT /vehicles/{vehicle_id}`
  - Request Body: `{ "status": "Rented" }`

- **Delete Vehicle**: `DELETE /vehicles/{vehicle_id}`

### Locations API

- **Create Location**: `POST /locations`
  - Request Body: `{ "name": "Downtown", "address": "123 Main St" }`

- **Get Location**: `GET /locations/{location_id}`

- **Update Location**: `PUT /locations/{location_id}`
  - Request Body: `{ "name": "Uptown", "address": "456 Elm St" }`

- **Delete Location**: `DELETE /locations/{location_id}`
  
  
## Contributing

To contribute to this project, follow these steps:

1. Fork this repository.
2. Create a new branch: ```git checkout -b feature/your-feature```
3. Make your changes and commit them: ```git commit -m 'Add some feature'```
4. Push to the branch: ```git push origin feature/your-feature.```
5. Submit a pull request.

## License

This project is licensed under the MIT License.

## Contact


Reach out to me ```rimmelasghar4@gmail.com```

made by Rimmel Asghar with ❤️
