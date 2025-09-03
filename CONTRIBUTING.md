# Guía de Contribución y Normas de Desarrollo

Este documento establece las reglas para trabajar en el proyecto **Gestión de Formularios**.  
Todas las modificaciones deberán seguir estas pautas para asegurar un código mantenible, seguro y ampliable.

---

## 🔹 Control de dependencias
- El proyecto usará **Maven** (o Gradle si se migra en el futuro) como gestor de dependencias.
- Todas las librerías externas (ejemplo: **PDFBox**, **SQLite**, **BCrypt/Argon2**) deben declararse en el `pom.xml` (o `build.gradle`).
- No incluir JARs manualmente en el repositorio.

---

## 🔹 Estilo de código
- Seguir **Google Java Style** (o Sun/Oracle si se acuerda).
- Código uniforme: sangría, llaves, nombres de variables y clases consistentes.
- Prohibido usar elipsis (…) al mostrar código: siempre el archivo completo.

---

## 🔹 Flujo de trabajo en cada archivo
1. **Corregir errores de compilación** (Java 21+).
2. **Optimizar el código** (Streams, switch mejorado, buenas prácticas).
3. **Implementar nuevas funciones** siguiendo las instrucciones del proyecto.
4. **Añadir Javadoc completo** a clases, métodos y atributos relevantes.
5. **Proponer un mensaje de commit** claro y conciso para GitHub.

---

## 🔹 Pruebas unitarias
- Toda clase de **lógica de negocio** (no JavaFX de interfaz) debe ir acompañada de pruebas unitarias en **JUnit 5**.
- Los tests deben residir en `src/test/java`.
- Usar mocks cuando sea necesario para aislar dependencias.

---

## 🔹 Gestión de excepciones
- Ninguna excepción debe silenciarse.
- Siempre se debe **loguear** (SLF4J o `java.util.logging`) o **propagar** con contexto claro.
- Evitar `printStackTrace()` en código final.

---

## 🔹 Internacionalización (i18n)
- El proyecto es multilenguaje.
- Todos los textos visibles en la **UI JavaFX** deben cargarse desde un **ResourceBundle**.
- Nunca hardcodear cadenas en el código.

---

## 🔹 Seguridad en la autenticación
- Nunca almacenar contraseñas en texto plano.
- Se deben guardar usando un **hash seguro** (BCrypt o Argon2).
- El flujo de login debe protegerse contra ataques de fuerza bruta y SQL Injection.
- Siempre usar consultas preparadas con SQLite.

---

## 🔹 Propósito y objetivos
- Entregar un proyecto **funcional, mantenible y ampliable**.
- Requisitos:
    - Gestión de elecciones en empresas.
    - Interfaz con **JavaFX**.
    - Manipulación de PDF con **PDFBox**.
    - Persistencia con **SQLite** (CRUD completo).
    - **Multilenguaje**.
    - Autenticación y seguridad.

---

## 🔹 Mensajes de commit
- Usar formato descriptivo y breve:
    - `fix: corrige error en conexión SQLite`
    - `feat: añade exportación de PDF con PDFBox`
    - `refactor: optimiza clase ControladorElecciones`
    - `test: agrega pruebas unitarias para LoginService`

---

## 🔹 Comportamiento esperado
- Preguntar siempre qué funcionalidad implementar primero.
- Entregar el archivo completo tras cada modificación.
- Mantener la estructura de paquetes.
- No inventar código no relacionado.
- Sugerir mejoras cuando sea necesario.

---
