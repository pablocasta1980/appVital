@echo off
REM Test runner script for controller tests (Windows)
REM This script can be used when Java environment is properly configured

echo Running Controller Tests...
echo ==========================

REM Compile test classes
echo Compiling test classes...
gradlew.bat compileTestJava

if %errorlevel% equ 0 (
    echo Test compilation successful!
    
    REM Run specific controller tests
    echo Running AuthController tests...
    gradlew.bat test --tests "co.edu.uniquindio.controller.AuthControllerTest"
    
    echo Running CitaController tests...
    gradlew.bat test --tests "co.edu.uniquindio.controller.CitaControllerTest"
    
    echo Running ExamenController tests...
    gradlew.bat test --tests "co.edu.uniquindio.controller.ExamenControllerTest"
    
    echo Running all controller tests...
    gradlew.bat test --tests "co.edu.uniquindio.controller.*Test"
    
    echo All tests completed!
) else (
    echo Test compilation failed!
    exit /b 1
)
