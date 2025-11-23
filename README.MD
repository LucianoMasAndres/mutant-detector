# 🧬 Mutant Detector API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![Tests](https://img.shields.io/badge/Tests-Passing-success.svg)]()
[![Coverage](https://img.shields.io/badge/Coverage-97%25-brightgreen.svg)]()

> 🚀 **API REST** de alto rendimiento para detectar mutantes analizando secuencias de ADN. Desarrollado como examen técnico de Backend Developer.

---

## 📋 Tabla de Contenidos

1. [Descripción del Proyecto](#-descripción-del-proyecto)
2. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
3. [Arquitectura](#-arquitectura)
4. [Instalación y Ejecución](#-instalación-y-ejecución)
5. [Uso de la API](#-uso-de-la-api)
6. [Algoritmo y Optimizaciones](#-algoritmo-y-optimizaciones)
7. [Testing y Cobertura](#-testing-y-cobertura)
8. [Despliegue (Docker & Render)](#-despliegue-docker--render)

---

## 🎯 Descripción del Proyecto

Magneto quiere reclutar la mayor cantidad de mutantes posible para su ejército. Este sistema permite detectar si un humano es mutante basándose en su secuencia de ADN.

**Regla de Negocio:**
Un humano es **mutante** si se encuentra **más de una secuencia** de cuatro letras iguales (`AAAA`, `TTTT`, `CCCC`, `GGGG`) de forma oblicua, horizontal o vertical en una matriz de ADN de (NxN).

---

## 🛠 Tecnologías Utilizadas

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.2.0
* **Base de Datos:** H2 Database (En memoria, optimizada para velocidad)
* **Build Tool:** Gradle
* **Testing:** JUnit 5, Mockito, MockMvc
* **Cobertura:** JaCoCo (97% de cobertura en Servicios)
* **Documentación:** SpringDoc OpenAPI (Swagger UI)
* **Contenedor:** Docker
* **Nube:** Render

---

## 🏗 Arquitectura

El proyecto sigue una **Arquitectura en Capas** (Layered Architecture) para asegurar la separación de responsabilidades:

```text
src/main/java/com/mercadolibre/mutant_detector/
├── config/       # Configuración de Beans (Swagger)
├── controller/   # Capa de entrada REST (Manejo de HTTP)
├── dto/          # Objetos de transferencia de datos (Request/Response)
├── entity/       # Entidades JPA (Mapeo a Base de Datos)
├── repository/   # Capa de acceso a datos (DAO/Repository Pattern)
└── service/      # Lógica de negocio y Algoritmos
🚀 Instalación y Ejecución
Prerrequisitos
Java 17 JDK

Gradle (o usar el wrapper incluido)

Ejecución Local
Clonar el repositorio:

Bash

git clone [https://github.com/LucianoMasAndres/mutant-detector.git](https://github.com/LucianoMasAndres/mutant-detector.git)
cd mutant-detector
Ejecutar con Gradle:

Bash

./gradlew bootRun
La API estará disponible en http://localhost:8080.

📡 Uso de la API
Documentación Interactiva (Swagger)
Una vez iniciada la aplicación, puedes probar todos los endpoints visualmente en: 👉 http://localhost:8080/swagger-ui.html

Endpoints Principales
1. Detectar Mutante
URL: POST /mutant

Descripción: Analiza una secuencia de ADN.

Body (JSON):

JSON

{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
Respuestas:

200 OK: Es Mutante.

403 Forbidden: Es Humano.

400 Bad Request: ADN inválido (caracteres erróneos, matriz no cuadrada, null).

2. Estadísticas
URL: GET /stats

Descripción: Devuelve el ratio de mutantes vs humanos analizados.

Respuesta (JSON):

JSON

{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
🧬 Algoritmo y Optimizaciones
El núcleo del proyecto es MutantDetector.java. Se han implementado optimizaciones críticas para manejar el tráfico masivo requerido por Magneto:

Early Termination (Fail-Fast): El algoritmo se detiene inmediatamente (return true) apenas encuentra la segunda secuencia mutante. No recorre el resto de la matriz innecesariamente.

Complejidad Espacial O(1): No se utilizan matrices auxiliares, listas ni hashmaps para la búsqueda. Se trabaja sobre la matriz original.

Acceso Directo: Se convierte el array de Strings a char[][] al inicio para evitar el overhead de String.charAt() en cada iteración.

Boundary Checking: Se verifican los límites de los índices antes de iniciar una búsqueda direccional, evitando excepciones y ciclos inútiles.

Persistencia con Hashing: Se utiliza SHA-256 para generar un hash único de cada ADN. Esto permite buscar en la base de datos (Índice Unique) si un ADN ya fue analizado antes de volver a ejecutar el algoritmo (Caché de Base de Datos).

🧪 Testing y Cobertura
El proyecto cuenta con una suite de tests exhaustiva (>35 tests) utilizando JUnit 5 y Mockito.

Unit Tests: Prueban el algoritmo MutantDetector con matrices de todos los tamaños, casos bordes, mutantes cruzados, etc.

Integration Tests: Prueban el MutantController y la conexión completa usando MockMvc.

Service Tests: Prueban la lógica de negocio y el guardado en BD usando Mocks.

Reporte de Cobertura (JaCoCo)
Para generar el reporte visual de cobertura:

Bash

./gradlew test jacocoTestReport
El reporte estará en: build/reports/jacoco/test/html/index.html. Cobertura actual: >97% en Servicios.

🐳 Despliegue (Docker & Render)
La aplicación está "Dockerizada" y lista para la nube.

Dockerfile Optimizado
El proyecto utiliza un Multi-Stage Build para generar una imagen final ligera (~200MB) basada en eclipse-temurin:17-jre-alpine.

Ejecutar con Docker
Bash

docker build -t mutant-detector .
docker run -p 8080:8080 mutant-detector
Enlace de Producción (Render)
La API está desplegada públicamente en: 🔗 https://mutant-detector-fq4y.onrender.com/swagger-ui.html

(Nota: Al estar en el plan gratuito de Render, el servidor puede tardar unos 50 segundos en "despertar" si lleva tiempo inactivo).

Desarrollado por Luciano Mas Andres