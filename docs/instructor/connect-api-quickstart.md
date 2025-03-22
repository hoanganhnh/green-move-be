# Instructor Connect API - Authentication Quickstart

This guide provides a quick step-by-step tutorial for implementing authentication with the Instructor Connect API.

## Prerequisites
- Basic knowledge of HTTP requests
- Familiarity with JSON
- A frontend application (web or mobile)

## Step 1: Register a New User

To create a new user account, send a POST request to the `/register` endpoint.

### Request
```http
POST /register
Content-Type: application/json

{
  "email": "instructor@example.com",
  "fullName": "Instructor Name",
  "password": "securePassword123"
}
```

### Response
```json
{
  "message": "User instructor@example.com registered successfully!"
}
```

## Step 2: Login with Credentials

After registration, users can login by sending a POST request to the `/login` endpoint.

### Request
```http
POST /login
Content-Type: application/json

{
  "email": "instructor@example.com",
  "password": "securePassword123"
}
```

### Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## Step 3: Use the JWT Token for Authentication

Include the JWT token in the Authorization header for all subsequent API requests.

### Example Request with Authentication
```http
GET /api/protected-resource
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## Implementation Examples

### JavaScript (Fetch API)

```javascript
// Registration
async function register() {
  const userData = {
    email: 'instructor@example.com',
    fullName: 'Instructor Name',
    password: 'securePassword123'
  };
  
  const response = await fetch('https://api-base-url/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(userData)
  });
  
  const data = await response.json();
  console.log(data.message);
}

// Login
async function login() {
  const credentials = {
    email: 'instructor@example.com',
    password: 'securePassword123'
  };
  
  const response = await fetch('https://api-base-url/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(credentials)
  });
  
  const data = await response.json();
  // Store token for later use
  localStorage.setItem('authToken', data.token);
}

// Authenticated request
async function fetchProtectedData() {
  const token = localStorage.getItem('authToken');
  
  const response = await fetch('https://api-base-url/api/protected-resource', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  
  const data = await response.json();
  return data;
}
```

### React Example

```jsx
import React, { useState } from 'react';
import axios from 'axios';

function AuthExample() {
  const [email, setEmail] = useState('');
  const [fullName, setFullName] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  
  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('https://api-base-url/register', {
        email,
        fullName,
        password
      });
      setMessage(response.data.message);
    } catch (error) {
      setMessage(error.response?.data?.message || 'Registration failed');
    }
  };
  
  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('https://api-base-url/login', {
        email,
        password
      });
      localStorage.setItem('authToken', response.data.token);
      setMessage('Login successful!');
    } catch (error) {
      setMessage(error.response?.data?.message || 'Login failed');
    }
  };
  
  return (
    <div>
      <h2>Authentication Example</h2>
      {message && <div className="message">{message}</div>}
      
      <form onSubmit={handleRegister}>
        <h3>Register</h3>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Full Name"
          value={fullName}
          onChange={(e) => setFullName(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Register</button>
      </form>
      
      <form onSubmit={handleLogin}>
        <h3>Login</h3>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default AuthExample;
```

## Common Errors and Troubleshooting

1. **Registration Failed (400)**: Email might already be in use or validation failed
2. **Login Failed (401)**: Incorrect email or password
3. **Unauthorized (401)**: Invalid or expired JWT token
4. **Forbidden (403)**: Valid token but insufficient permissions

## Security Best Practices

1. Always use HTTPS
2. Store tokens securely (localStorage for web apps, secure storage for mobile)
3. Implement token refresh mechanisms for long-lived sessions
4. Log out users by removing the token from storage
5. Implement proper error handling for authentication failures
