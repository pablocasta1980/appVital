# Controller Tests Documentation

This directory contains comprehensive JUnit and Mockito tests for the application's REST controllers.

## Test Files Created

### 1. AuthControllerTest.java
Tests for the authentication controller (`/api/auth`):
- **login()** - Tests successful login with valid credentials
- **login()** - Tests validation errors for invalid email formats
- **login()** - Tests validation errors for blank/null fields
- **login()** - Tests different user types (PACIENTE, MEDICO)
- **login()** - Tests error handling for malformed JSON

### 2. CitaControllerTest.java
Tests for the appointment controller (`/api/citas`):
- **agendarCita()** - Tests successful appointment scheduling
- **agendarCita()** - Tests validation errors for missing/invalid data
- **agendarCita()** - Tests different appointment types (PRESENCIAL, VIRTUAL)
- **cancelarCita()** - Tests successful appointment cancellation
- **obtenerCitasPorPaciente()** - Tests retrieving all patient appointments
- **obtenerCitasPorPacienteYEstado()** - Tests filtering appointments by status

### 3. ExamenControllerTest.java
Tests for the exam controller (`/api/examenes`):
- **obtenerExamenesPorPaciente()** - Tests retrieving all patient exams
- **obtenerExamenesPorPacienteYEstado()** - Tests filtering exams by status
- **obtenerExamenesPorPacienteYTipo()** - Tests filtering exams by type
- **obtenerExamenPorId()** - Tests retrieving specific exam details
- **obtenerExamenPorId()** - Tests urgent exam handling

## Test Coverage

Each test file covers:
- ✅ **Happy Path Scenarios** - Successful operations with valid data
- ✅ **Validation Errors** - Invalid input data handling
- ✅ **Edge Cases** - Empty lists, null values, boundary conditions
- ✅ **Different User Types** - Patient vs. Doctor scenarios
- ✅ **HTTP Status Codes** - Proper response codes (200, 400, etc.)
- ✅ **JSON Response Structure** - Correct response format validation
- ✅ **Mock Service Integration** - Proper service layer mocking

## Dependencies Used

- **JUnit 5** - Test framework
- **Mockito** - Mocking framework
- **Spring Test** - Spring MVC test support
- **Jackson** - JSON serialization/deserialization
- **JavaTimeModule** - LocalDateTime support

## Running the Tests

### Prerequisites
- Java 17+ installed and configured
- JAVA_HOME environment variable set
- Gradle wrapper available

### Command Line
```bash
# Run all controller tests
./gradlew test --tests "co.edu.uniquindio.controller.*Test"

# Run specific controller tests
./gradlew test --tests "co.edu.uniquindio.controller.AuthControllerTest"
./gradlew test --tests "co.edu.uniquindio.controller.CitaControllerTest"
./gradlew test --tests "co.edu.uniquindio.controller.ExamenControllerTest"

# Compile tests only
./gradlew compileTestJava
```

### Using Test Runner Scripts
- **Linux/Mac**: `./run-controller-tests.sh`
- **Windows**: `run-controller-tests.bat`

## Test Structure

Each test follows the **Arrange-Act-Assert** pattern:

```java
@Test
void methodName_Scenario_ExpectedResult() throws Exception {
    // Arrange - Set up test data and mocks
    String input = "test";
    when(mockService.method(any())).thenReturn(expectedResponse);
    
    // Act - Perform the action
    mockMvc.perform(get("/endpoint"))
    
    // Assert - Verify the results
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.field").value("expected"));
}
```

## Mock Configuration

Tests use Mockito to mock service dependencies:
- `@Mock` - Creates mock instances of service interfaces
- `@InjectMocks` - Injects mocks into the controller under test
- `when().thenReturn()` - Defines mock behavior
- `any()` - Matches any argument of the specified type

## JSON Testing

Tests verify JSON responses using Spring's JSON path assertions:
- `jsonPath("$.field")` - Access nested JSON fields
- `jsonPath("$.array[0].field")` - Access array elements
- `jsonPath("$.length()")` - Verify array length

## Validation Testing

Tests verify Jakarta Bean Validation constraints:
- `@NotBlank` - Required string fields
- `@Email` - Email format validation
- `@NotNull` - Required object fields

## Best Practices Implemented

1. **Descriptive Test Names** - Clear scenario description
2. **Single Responsibility** - Each test verifies one specific behavior
3. **Independent Tests** - Tests don't depend on each other
4. **Comprehensive Coverage** - Happy path, error cases, and edge cases
5. **Proper Mocking** - Isolated unit tests with mocked dependencies
6. **Clear Assertions** - Specific and meaningful assertions
7. **Test Data Factory** - Helper methods for creating test data

## Future Enhancements

Consider adding:
- Integration tests with embedded database
- Performance tests for high-load scenarios
- Security tests for authentication/authorization
- Contract tests for API compatibility
- Test coverage reports
