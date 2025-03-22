# Instructor Connect API Documentation

Welcome to the Instructor Connect API documentation. This directory contains guides and examples to help you integrate with our API.

## Available Resources

1. [Authentication Quickstart](./connect-api-quickstart.md) - A concise step-by-step guide for implementing login and registration
2. [Detailed Authentication Guide](./connect-api-auth.md) - Comprehensive documentation on authentication

## Getting Started

If you're new to the API, we recommend starting with the [Authentication Quickstart](./connect-api-quickstart.md) guide, which provides a brief overview of the authentication process.

For more detailed information and implementation guidance, refer to the [Detailed Authentication Guide](./connect-api-auth.md).

## API Endpoints

### Authentication

- `POST /register` - Create a new user account
- `POST /login` - Authenticate and receive a JWT token

### Protected Resources

All protected resources require a valid JWT token in the Authorization header:

```http
Authorization: Bearer your-jwt-token
```

### User Profile

- `GET /profile/me` - Get the current authenticated user's profile information

## Support

If you encounter any issues or have questions about the API, please contact our support team at [support@example.com](mailto:support@example.com).
