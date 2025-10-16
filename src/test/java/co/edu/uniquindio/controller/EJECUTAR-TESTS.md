# Ejecutar Tests Directamente desde las Clases

Ahora puedes ejecutar los tests directamente desde las clases de test sin necesidad de usar Gradle o JUnit Platform. Cada clase de test es completamente autónoma y se puede ejecutar independientemente.

## 🚀 Formas de Ejecutar los Tests

### 1. Desde el IDE (IntelliJ IDEA, Eclipse, VS Code)

**Opción A: Ejecutar método main**
1. Abre cualquiera de las clases de test:
   - `AuthControllerTest.java`
   - `CitaControllerTest.java` 
   - `ExamenControllerTest.java`
   - `AllControllerTests.java` (ejecuta todos)
2. Haz clic derecho en el método `main`
3. Selecciona "Run 'main()'"

**Opción B: Ejecutar como aplicación**
1. Abre la clase de test
2. Presiona `Ctrl+Shift+F10` (IntelliJ) o `Ctrl+F11` (Eclipse)
3. Selecciona "Run as Java Application"

### 2. Desde la Línea de Comandos

```bash
# Compilar primero (si es necesario)
javac -cp "classpath" src/test/java/co/edu/uniquindio/controller/AuthControllerTest.java

# Ejecutar tests individuales
java -cp "classpath" co.edu.uniquindio.controller.AuthControllerTest
java -cp "classpath" co.edu.uniquindio.controller.CitaControllerTest
java -cp "classpath" co.edu.uniquindio.controller.ExamenControllerTest

# Ejecutar TODOS los tests de controladores
java -cp "classpath" co.edu.uniquindio.controller.AllControllerTests
```

### 3. Usando Gradle (método tradicional)

```bash
# Ejecutar tests individuales
./gradlew test --tests "co.edu.uniquindio.controller.AuthControllerTest"
./gradlew test --tests "co.edu.uniquindio.controller.CitaControllerTest"
./gradlew test --tests "co.edu.uniquindio.controller.ExamenControllerTest"

# Ejecutar todos los tests de controladores
./gradlew test --tests "co.edu.uniquindio.controller.*Test"
```

## 📊 Salida de los Tests

Cuando ejecutes los tests, verás una salida como esta:

```
=== Ejecutando AuthControllerTest ===

=== Resumen de Tests ===
Tests ejecutados: 8
Tests exitosos: 8
Tests fallidos: 0
Tests ignorados: 0

✅ Todos los tests pasaron exitosamente!
```

## 🎯 Ventajas de esta Implementación

1. **✅ Completamente Autónoma**: No requiere JUnit Platform ni dependencias externas
2. **✅ Ejecución Directa**: No necesitas configurar Gradle o Maven
3. **✅ IDE Friendly**: Funciona perfectamente con cualquier IDE
4. **✅ Resumen Detallado**: Muestra estadísticas completas de ejecución
5. **✅ Manejo de Errores**: Reporta errores específicos si algún test falla
6. **✅ Flexibilidad**: Puedes ejecutar tests individuales o todos juntos
7. **✅ Reflexión Java**: Usa reflexión para ejecutar métodos de test automáticamente
8. **✅ Sin Dependencias**: Solo requiere las clases de test compiladas

## 🔧 Clases Disponibles

| Clase | Descripción | Tests |
|-------|-------------|-------|
| `AuthControllerTest` | Tests de autenticación | 8 tests |
| `CitaControllerTest` | Tests de gestión de citas | 10 tests |
| `ExamenControllerTest` | Tests de gestión de exámenes | 10 tests |
| `AllControllerTests` | Ejecuta todos los tests | 28 tests |

## 📝 Notas Importantes

- **Dependencias**: Solo necesitas las clases compiladas (no requiere JUnit Platform)
- **Java Version**: Requiere Java 17+ (como especificado en build.gradle)
- **Reflexión**: Los tests usan reflexión Java para ejecutarse automáticamente
- **Mockito**: Los tests usan Mockito para mocking de servicios
- **Autónomo**: Cada clase de test es completamente independiente

## 🐛 Solución de Problemas

Si encuentras errores al ejecutar:

1. **ClassNotFoundException**: Verifica que todas las clases estén compiladas
2. **NoSuchMethodError**: Asegúrate de que los métodos de test existan
3. **Compilation Error**: Verifica que todas las clases compilen correctamente
4. **Reflection Error**: Verifica que los métodos de test sean accesibles

## 💡 Consejos de Uso

- Usa `AllControllerTests` para ejecutar todos los tests de una vez
- Ejecuta tests individuales cuando estés desarrollando una funcionalidad específica
- Los tests se ejecutan más rápido desde el IDE que desde la línea de comandos
- Usa el resumen de resultados para identificar rápidamente qué tests fallan
