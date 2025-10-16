#!/bin/bash
# Test runner script for controller tests
# This script can be used when Java environment is properly configured

echo "Running Controller Tests..."
echo "=========================="

# Compile test classes
echo "Compiling test classes..."
./gradlew compileTestJava

if [ $? -eq 0 ]; then
    echo "Test compilation successful!"
    
    # Run specific controller tests
    echo "Running AuthController tests..."
    ./gradlew test --tests "co.edu.uniquindio.controller.AuthControllerTest"
    
    echo "Running CitaController tests..."
    ./gradlew test --tests "co.edu.uniquindio.controller.CitaControllerTest"
    
    echo "Running ExamenController tests..."
    ./gradlew test --tests "co.edu.uniquindio.controller.ExamenControllerTest"
    
    echo "Running all controller tests..."
    ./gradlew test --tests "co.edu.uniquindio.controller.*Test"
    
    echo "All tests completed!"
else
    echo "Test compilation failed!"
    exit 1
fi
