# --- ETAPA 1: BUILD ---
FROM gradle:8.5-jdk17 AS build

# Copiar código
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# TRUCO: Limitamos la memoria de Gradle a 350MB para que no explote en Render Free
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx350m -Dorg.gradle.vfs.watch=false"

# Compilar (Saltando tests para ahorrar memoria y tiempo)
RUN gradle bootJar -x test --no-daemon

# --- ETAPA 2: RUN ---
FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]