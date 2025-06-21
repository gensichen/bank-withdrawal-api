# Greeting API

A simple Spring Boot REST API that returns greeting messages.

## Endpoints

### Greeting Endpoint

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

```bash
curl http://localhost:8080/api/greeting/John
```
