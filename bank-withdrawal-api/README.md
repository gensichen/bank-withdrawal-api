# Greeting API

A simple Spring Boot REST API that returns greeting messages.

## Endpoints

### Greeting Endpoints

#### GET Endpoint
```
GET /api/greeting/{name}
```

Returns a greeting message for the provided name.

**Parameters:**
- `name` (path variable): The name of the person to greet

**Response Example:**
```json
{
  "message": "Hello John"
}
```

#### POST Endpoint
```
POST /api/greeting
```

Returns a greeting message for the provided name in the request body.

**Request Body:**
- Plain text containing the name of the person to greet

**Response Example:**
```json
{
  "message": "Hello John"
}
```

## Running the Application

```bash
./gradlew bootRun
```

The API will be available at http://localhost:8080

## Building the Application

```bash
./gradlew build
```

## Running Tests

```bash
./gradlew test
```

## Example Usage

### GET Example
```bash
curl http://localhost:8080/api/greeting/John
```

### POST Example
```bash
curl -X POST -H "Content-Type: text/plain" -d "John" http://localhost:8080/api/greeting
```
