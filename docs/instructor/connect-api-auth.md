# Instructor Connect API Authentication Guide

This guide provides step-by-step instructions for implementing login and registration functionality using the Instructor Connect API.

## Table of Contents

1. [Authentication Flow Overview](#authentication-flow-overview)
2. [Registration](#registration)
3. [Login](#login)
4. [Using JWT Tokens](#using-jwt-tokens)
5. [Error Handling](#error-handling)

## Authentication Flow Overview

The authentication flow consists of two main endpoints:

- `/register` - For creating new user accounts
- `/login` - For authenticating existing users and obtaining JWT tokens

Both endpoints accept JSON payloads and return JSON responses. Authentication is handled using JWT (JSON Web Tokens).

## Registration

### Endpoint

```http
POST /register
```

### Request Headers
```
Content-Type: application/json
```

### Request Body
```json
{
  "email": "user@example.com",
  "fullName": "John Doe",
  "password": "password123",
  "phoneNumber": "+1234567890"
}
```

### Field Descriptions
| Field | Type | Description | Required |
|-------|------|-------------|----------|
| email | String | User's email address | Yes |
| fullName | String | User's full name | Yes |
| password | String | User's password | Yes |
| phoneNumber | String | User's phone number | No |

### Response (Success - 201 Created)

```json
{
  "message": "User user@example.com registered successfully!"
}
```

### Example Code (JavaScript/Fetch)
```javascript
async function registerUser(userData) {
  try {
    const response = await fetch('https://your-api-base-url/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });
    
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Registration failed');
    }
    
    return await response.json();
  } catch (error) {
    console.error('Registration error:', error);
    throw error;
  }
}

// Usage example
const userData = {
  email: 'user@example.com',
  fullName: 'John Doe',
  password: 'password123',
  phoneNumber: '+1234567890'
};

registerUser(userData)
  .then(data => console.log('Registration successful:', data))
  .catch(error => console.error('Registration failed:', error));
```

### Example Code (Axios)
```javascript
import axios from 'axios';

async function registerUser(userData) {
  try {
    const response = await axios.post('https://your-api-base-url/register', userData);
    return response.data;
  } catch (error) {
    console.error('Registration error:', error.response?.data || error.message);
    throw error;
  }
}

// Usage example
const userData = {
  email: 'user@example.com',
  fullName: 'John Doe',
  password: 'password123',
  phoneNumber: '+1234567890'
};

registerUser(userData)
  .then(data => console.log('Registration successful:', data))
  .catch(error => console.error('Registration failed:', error));
```

## Login

### Endpoint
```
POST /login
```

### Request Headers
```
Content-Type: application/json
```

### Request Body
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

### Field Descriptions
| Field | Type | Description | Required |
|-------|------|-------------|----------|
| email | String | User's email address | Yes |
| password | String | User's password | Yes |

### Response (Success - 200 OK)
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "full_name": "John Doe",
    "email": "user@example.com",
    "phone_number": "+1234567890",
    "role": "USER"
  }
}
```

### Example Code (JavaScript/Fetch)
```javascript
async function loginUser(credentials) {
  try {
    const response = await fetch('https://your-api-base-url/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });
    
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Login failed');
    }
    
    return await response.json();
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
}

// Usage example
const credentials = {
  email: 'user@example.com',
  password: 'password123'
};

loginUser(credentials)
  .then(data => {
    console.log('Login successful:', data);
    // Store the token in localStorage or sessionStorage
    localStorage.setItem('authToken', data.token);
  })
  .catch(error => console.error('Login failed:', error));
```

### Example Code (Axios)
```javascript
import axios from 'axios';

async function loginUser(credentials) {
  try {
    const response = await axios.post('https://your-api-base-url/login', credentials);
    return response.data;
  } catch (error) {
    console.error('Login error:', error.response?.data || error.message);
    throw error;
  }
}

// Usage example
const credentials = {
  email: 'user@example.com',
  password: 'password123'
};

loginUser(credentials)
  .then(data => {
    console.log('Login successful:', data);
    // Store the token in localStorage or sessionStorage
    localStorage.setItem('authToken', data.token);
  })
  .catch(error => console.error('Login failed:', error));
```

## Using JWT Tokens

After successful login, you'll receive a JWT token. This token should be included in the Authorization header for all subsequent API requests that require authentication.

### Example of Making Authenticated Requests
```javascript
// Function to make authenticated API requests
async function fetchAuthenticatedData(url) {
  // Get the token from storage
  const token = localStorage.getItem('authToken');
  
  if (!token) {
    throw new Error('No authentication token found');
  }
  
  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });
    
    if (!response.ok) {
      // Handle token expiration or other auth errors
      if (response.status === 401) {
        // Token expired or invalid, redirect to login
        localStorage.removeItem('authToken');
        window.location.href = '/login';
        throw new Error('Authentication token expired');
      }
      
      const errorData = await response.json();
      throw new Error(errorData.message || 'Request failed');
    }
    
    return await response.json();
  } catch (error) {
    console.error('API request error:', error);
    throw error;
  }
}

// Usage example
fetchAuthenticatedData('https://your-api-base-url/protected-endpoint')
  .then(data => console.log('Data:', data))
  .catch(error => console.error('Error:', error));
```

## Error Handling

### Common Error Responses

#### Registration Errors (400 Bad Request)
```json
{
  "timestamp": "2023-01-01T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Email is already in use",
  "path": "/register"
}
```

#### Login Errors (401 Unauthorized)
```json
{
  "timestamp": "2023-01-01T12:00:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password",
  "path": "/login"
}
```

### Handling Errors in Your Application
```javascript
// Generic error handler for API requests
function handleApiError(error) {
  if (error.response) {
    // The request was made and the server responded with a status code
    // that falls out of the range of 2xx
    const { status, data } = error.response;
    
    switch (status) {
      case 400:
        // Handle validation errors
        console.error('Validation error:', data.message);
        // Display to user
        break;
      case 401:
        // Handle authentication errors
        console.error('Authentication error:', data.message);
        // Redirect to login
        localStorage.removeItem('authToken');
        window.location.href = '/login';
        break;
      case 403:
        // Handle authorization errors
        console.error('Authorization error:', data.message);
        // Display to user
        break;
      default:
        // Handle other errors
        console.error('API error:', data.message);
        // Display generic error
    }
  } else if (error.request) {
    // The request was made but no response was received
    console.error('Network error:', error.request);
    // Display network error
  } else {
    // Something happened in setting up the request that triggered an Error
    console.error('Error:', error.message);
    // Display generic error
  }
}
```

## Security Considerations

1. **HTTPS**: Always use HTTPS for all API communications to encrypt data in transit.
2. **Password Storage**: Passwords are hashed using BCrypt before storage.
3. **Token Expiration**: JWT tokens have an expiration time. Handle token refresh appropriately.
4. **CORS**: The API has CORS enabled to control which domains can access it.
5. **Input Validation**: All inputs are validated server-side to prevent injection attacks.
