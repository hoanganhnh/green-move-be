#!/bin/bash

# Display banner
echo "====================================================="
echo "  Spring Boot App Rebuild and Restart Helper"
echo "====================================================="
echo ""

# Function to rebuild and restart inside container
rebuild_inside_container() {
  echo "Accessing app container to rebuild and restart..."
  
  # Check if the app container is running
  if ! docker compose ps | grep -q "spring-boot-app.*Up"; then
    echo "Error: App container is not running. Start the application first."
    exit 1
  fi
  
  # Get the container ID
  CONTAINER_ID=$(docker compose ps -q app)
  
  if [ -z "$CONTAINER_ID" ]; then
    echo "Error: Could not find the app container ID."
    exit 1
  fi
  
  echo "Found container ID: $CONTAINER_ID"
  echo "Rebuilding and restarting Spring Boot application inside container..."
  
  # Execute Maven commands inside the container to rebuild and restart
  # First, we'll stop the running application (if any)
  docker exec $CONTAINER_ID bash -c "pkill -f 'spring-boot:run' || echo 'No Java process running'"
  
  # Then rebuild the application with Maven
  docker exec $CONTAINER_ID bash -c "cd /app && mvn clean compile"
  
  # Check if the build was successful
  if [ $? -eq 0 ]; then
    echo "Build successful! Starting the application..."
    
    # Start the application in the background with DevTools enabled
    docker exec -d $CONTAINER_ID bash -c "cd /app && mvn spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments=\"-Dspring.devtools.restart.enabled=true -Dspring.devtools.livereload.enabled=true -Dspring.devtools.restart.poll-interval=2s -Dspring.devtools.restart.quiet-period=1s\" &"
    
    echo "Application restarted successfully!"
    echo "You can access it at: http://localhost:8081"
    echo "Swagger UI: http://localhost:8081/swagger-ui/index.html"
    echo "Database admin: http://localhost:8082"
  else
    echo "Build failed. Please check the logs for errors."
  fi
}

# Function to trigger a DevTools restart
trigger_devtools_restart() {
  echo "Triggering DevTools restart..."
  
  # Check if the app container is running
  if ! docker compose ps | grep -q "spring-boot-app.*Up"; then
    echo "Error: App container is not running. Start the application first."
    exit 1
  fi
  
  # Get the container ID
  CONTAINER_ID=$(docker compose ps -q app)
  
  if [ -z "$CONTAINER_ID" ]; then
    echo "Error: Could not find the app container ID."
    exit 1
  fi
  
  # Touch a Java file to trigger DevTools restart
  docker exec $CONTAINER_ID bash -c "touch /app/src/main/java/com/rimmelasghar/boilerplate/springboot/SpringBootBoilerplateApplication.java"
  
  echo "DevTools restart triggered. The application should restart automatically."
  echo "You can access it at: http://localhost:8081"
  echo "Swagger UI: http://localhost:8081/swagger-ui/index.html"
}

# Main function
main() {
  echo "Choose an option:"
  echo "1. Full rebuild and restart"
  echo "2. Trigger DevTools restart (faster)"
  read -p "Enter your choice (1-2): " choice
  
  case $choice in
    1)
      rebuild_inside_container
      ;;
    2)
      trigger_devtools_restart
      ;;
    *)
      echo "Invalid choice. Exiting."
      exit 1
      ;;
  esac
}

# Run the main function
main
