#!/bin/bash

# Display banner
echo "====================================================="
echo "  Spring Boot Boilerplate Application Starter"
echo "====================================================="
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Error: Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is available
if command -v docker-compose &> /dev/null; then
    DOCKER_COMPOSE_CMD="docker-compose"
elif command -v docker &> /dev/null && docker compose version &> /dev/null; then
    DOCKER_COMPOSE_CMD="docker compose"
else
    echo "Error: Neither docker-compose nor docker compose plugin is available."
    echo "Please install Docker Compose or the Docker Compose plugin."
    exit 1
fi

# Check if .env file exists
if [ ! -f .env ]; then
    echo "Warning: .env file not found. Creating a sample .env file..."
    cat > .env << EOF
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
EOF
    echo "Created sample .env file. Please modify it with your settings if needed."
    echo ""
fi

# Function to check if containers are running
check_containers_running() {
    if [ "$($DOCKER_COMPOSE_CMD -f local-docker-compose.yml ps -q)" ]; then
        return 0
    else
        return 1
    fi
}

# Main menu
show_menu() {
    echo "Please select an option:"
    echo "1. Start application"
    echo "2. Stop application"
    echo "3. Restart application"
    echo "4. View logs"
    echo "5. Rebuild and start application"
    echo "6. Access app container shell"
    echo "7. Rebuild app inside container"
    echo "8. Exit"
    echo ""
    read -p "Enter your choice (1-8): " choice
    
    case $choice in
        1)
            start_application
            ;;
        2)
            stop_application
            ;;
        3)
            restart_application
            ;;
        4)
            view_logs
            ;;
        5)
            rebuild_application
            ;;
        6)
            access_container_shell
            ;;
        7)
            rebuild_inside_container
            ;;
        8)
            echo "Exiting..."
            exit 0
            ;;
        *)
            echo "Invalid option. Please try again."
            show_menu
            ;;
    esac
}

# Start the application
start_application() {
    echo "Starting the application..."
    if check_containers_running; then
        echo "Application is already running."
    else
        $DOCKER_COMPOSE_CMD -f local-docker-compose.yml up -d
        echo "Application is starting. This may take a moment..."
        
        # Wait for the application to be ready
        echo "Waiting for the application to be ready..."
        attempt=1
        max_attempts=30
        
        while [ $attempt -le $max_attempts ]; do
            if $DOCKER_COMPOSE_CMD -f local-docker-compose.yml logs app | grep -q "Started SpringBootBoilerplateApplication"; then
                echo "Application is now running!"
                echo "Access it at: http://localhost:8081"
                echo "Swagger UI: http://localhost:8081/swagger-ui/index.html"
                break
            fi
            
            echo "Still starting... (Attempt $attempt/$max_attempts)"
            sleep 5
            ((attempt++))
        done
        
        if [ $attempt -gt $max_attempts ]; then
            echo "Application may not have started properly. Please check the logs."
        fi
    fi
    
    echo ""
    show_menu
}

# Stop the application
stop_application() {
    echo "Stopping the application..."
    $DOCKER_COMPOSE_CMD -f local-docker-compose.yml down
    echo "Application stopped."
    echo ""
    show_menu
}

# Restart the application
restart_application() {
    echo "Restarting the application..."
    $DOCKER_COMPOSE_CMD -f local-docker-compose.yml restart
    echo "Application restarted."
    echo ""
    show_menu
}

# View logs
view_logs() {
    echo "Viewing logs (press Ctrl+C to exit logs)..."
    $DOCKER_COMPOSE_CMD -f local-docker-compose.yml logs -f
    echo ""
    show_menu
}

# Rebuild and start the application
rebuild_application() {
    echo "Rebuilding and starting the application..."
    $DOCKER_COMPOSE_CMD -f local-docker-compose.yml up -d --build
    echo "Application rebuilt and started."
    echo ""
    show_menu
}

# Access the app container shell
access_container_shell() {
    echo "Accessing app container shell..."
    
    # Check if the app container is running
    if ! $DOCKER_COMPOSE_CMD -f local-docker-compose.yml ps | grep -q "app.*Up"; then
        echo "Error: App container is not running. Start the application first."
        echo ""
        show_menu
        return
    fi
    
    # Get the container ID
    CONTAINER_ID=$($DOCKER_COMPOSE_CMD -f local-docker-compose.yml ps -q app)
    
    if [ -z "$CONTAINER_ID" ]; then
        echo "Error: Could not find the app container ID."
    else
        echo "Entering container shell. Type 'exit' to return to this menu."
        echo "----------------------------------------"
        echo "TIPS: To rebuild and restart the app after making changes:"
        echo "1. Run 'cd /app' to go to the app directory"
        echo "2. Run 'mvn clean package -DskipTests' to rebuild"
        echo "3. Run 'pkill -f "java -jar" && java -jar target/*.jar &' to restart"
        echo "----------------------------------------"
        docker exec -it $CONTAINER_ID /bin/bash || docker exec -it $CONTAINER_ID /bin/sh
        echo "----------------------------------------"
        echo "Returned from container shell."
    fi
    
    echo ""
    show_menu
}

# Rebuild the application inside the container
rebuild_inside_container() {
    echo "Rebuilding application inside container..."
    
    # Check if the app container is running
    if ! $DOCKER_COMPOSE_CMD -f local-docker-compose.yml ps | grep -q "app.*Up"; then
        echo "Error: App container is not running. Start the application first."
        echo ""
        show_menu
        return
    fi
    
    # Get the container ID
    CONTAINER_ID=$($DOCKER_COMPOSE_CMD -f local-docker-compose.yml ps -q app)
    
    if [ -z "$CONTAINER_ID" ]; then
        echo "Error: Could not find the app container ID."
    else
        echo "Rebuilding and restarting Spring Boot application inside container..."
        
        # Execute Maven commands inside the container to rebuild and restart
        # First, we'll stop the running application (if any)
        docker exec $CONTAINER_ID bash -c "pkill -f 'java -jar' || echo 'No Java process running'"
        
        # Then rebuild the application with Maven
        docker exec $CONTAINER_ID bash -c "cd /app && mvn clean package -DskipTests"
        
        # Check if the build was successful
        if [ $? -eq 0 ]; then
            echo "Build successful! Starting the application..."
            
            # Start the application in the background
            docker exec -d $CONTAINER_ID bash -c "cd /app && java -jar target/*.jar &"
            
            echo "Application restarted successfully!"
            echo "You can access it at: http://localhost:8081"
            echo "Swagger UI: http://localhost:8081/swagger-ui/index.html"
        else
            echo "Build failed. Please check the logs for errors."
        fi
    fi
    
    echo ""
    show_menu
}

# Check if script is being run with arguments
if [ $# -gt 0 ]; then
    case "$1" in
        start)
            start_application
            exit 0
            ;;
        stop)
            stop_application
            exit 0
            ;;
        restart)
            restart_application
            exit 0
            ;;
        logs)
            view_logs
            exit 0
            ;;
        rebuild)
            rebuild_application
            exit 0
            ;;
        shell)
            access_container_shell
            exit 0
            ;;
        rebuild-inside)
            rebuild_inside_container
            exit 0
            ;;
        *)
            echo "Unknown command: $1"
            echo "Available commands: start, stop, restart, logs, rebuild, shell, rebuild-inside"
            exit 1
            ;;
    esac
fi

# Show the menu if no arguments provided
show_menu
