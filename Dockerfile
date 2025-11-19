# --- ETAPA 1: BUILD (Construcción) ---
# Usamos la imagen de Gradle oficial que ya trae Java 17
# Esto es más seguro que usar JDK puro porque ya tiene el comando 'gradle' instalado
FROM gradle:8.5-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Limitamos la memoria para Render Free y compilamos
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx350m -Dorg.gradle.vfs.watch=false"
RUN gradle bootJar -x test --no-daemon

# --- ETAPA 2: RUN (Ejecución) ---
# AQUÍ aplicamos el consejo de tu compañero
# Usamos Eclipse Temurin JRE (versión ligera para correr, no JDK que pesa más)
FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]