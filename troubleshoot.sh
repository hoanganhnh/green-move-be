#!/bin/bash

echo "====================================================="
echo "  Spring Boot Application Connectivity Troubleshooter"
echo "====================================================="
echo ""

# Check if Docker is running
echo "1. Checking if Docker is running..."
if ! docker info >/dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
else
    echo "✅ Docker is running."
fi

# Check if containers are running
echo ""
echo "2. Checking container status..."
if [ "$(docker compose -f local-docker-compose.yml ps -q app)" ]; then
    echo "✅ App container is running."
else
    echo "❌ App container is not running."
    echo "   Starting containers..."
    docker compose -f local-docker-compose.yml up -d
fi

# Wait for containers to be ready
echo ""
echo "3. Waiting for containers to be ready..."
sleep 10

# Check container logs for errors
echo ""
echo "4. Checking app container logs for errors..."
docker compose -f local-docker-compose.yml logs app | grep -i "error\|exception" | tail -n 10

# Check network connectivity
echo ""
echo "5. Checking network connectivity..."
echo "   Container IP addresses:"
docker inspect -f '{{.Name}} - {{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps -q)

# Try to access the app from inside the container
echo ""
echo "6. Trying to access the app from inside the container..."
# Install curl in the container if it doesn't exist
docker exec app sh -c "if ! command -v curl &> /dev/null; then apt-get update && apt-get install -y curl; fi"
docker exec app sh -c "curl -I http://localhost:8080/actuator/health || echo 'Failed to connect to localhost:8080'"

# Check if the port is actually listening
echo ""
echo "7. Checking if the application is listening on port 8080..."
docker exec app sh -c "apt-get update && apt-get install -y net-tools && netstat -tuln | grep 8080 || echo 'No process is listening on port 8080'"

# Try to access from host
echo ""
echo "8. Trying to access the app from host..."
curl -I http://localhost:8080/actuator/health || echo "Failed to connect to localhost:8080"

# Check if there's any firewall blocking
echo ""
echo "9. Checking for firewall issues..."
if command -v ufw &> /dev/null; then
    ufw status
elif command -v firewall-cmd &> /dev/null; then
    firewall-cmd --list-all
else
    echo "No common firewall tool found."
fi

# Modify Docker Compose file to use different port
echo ""
echo "10. Modifying Docker Compose file to use port 8081 instead of 8080..."
sed -i 's/8080:8080/8081:8080/g' local-docker-compose.yml
echo "✅ Modified Docker Compose file to use port 8081."

# Restart containers with new configuration
echo ""
echo "11. Restarting containers with new configuration..."
docker compose -f local-docker-compose.yml down
docker compose -f local-docker-compose.yml up -d

# Wait for containers to be ready
echo ""
echo "12. Waiting for containers to be ready..."
sleep 15

# Try to access with new port
echo ""
echo "13. Trying to access the app on the new port 8081..."
curl -I http://localhost:8081/actuator/health || echo "Failed to connect to localhost:8081"

echo ""
echo "====================================================="
echo "  Troubleshooting Complete"
echo "====================================================="
echo ""
echo "If you're still having issues, try the following:"
echo "1. Check if your environment has any network restrictions"
echo "2. Try accessing the application from a different browser"
echo "3. Check if there's any proxy configuration in your environment"
echo "4. Try using the IP address of your machine instead of localhost"
echo ""
echo "You can also try running the application without Docker:"
echo "1. Stop the Docker containers: docker compose -f local-docker-compose.yml down"
echo "2. Install Maven and Java locally"
echo "3. Run the application with: ./mvnw spring-boot:run"
