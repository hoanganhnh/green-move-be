#!/bin/bash

echo "Stopping existing containers..."
docker-compose down

echo "Building and starting containers with hot reload enabled..."
docker-compose up --build -d

echo "Containers started in development mode with hot reload."
echo "Your application is running at: http://localhost:8081"
echo "Swagger UI: http://localhost:8081/swagger-ui/index.html"
echo "Database admin: http://localhost:8082"

echo ""
echo "To view logs, run: docker-compose logs -f app"
echo "To test hot reload, make changes to your Java files and they will be automatically reloaded."
