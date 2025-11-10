# Etapa 1: Construcción (Build)
# Usamos la imagen oficial de Java 21 JDK para construir el .jar
FROM eclipse-temurin:21-jdk-jammy AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /workspace/app

# Copiamos los archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# ¡Importante! Damos permisos de ejecución a gradlew (soluciona el error anterior)
RUN chmod +x ./gradlew

# Copiamos el código fuente de la aplicación
COPY src src

# Construimos la aplicación.
# El --no-daemon es recomendado para entornos de CI/CD
RUN ./gradlew build --no-daemon

# Etapa 2: Ejecución (Runtime)
# Usamos una imagen más ligera solo con el JRE (Java 21)
FROM eclipse-temurin:21-jre-jammy

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el .jar construido desde la etapa 'builder'
COPY --from=builder /workspace/app/build/libs/bibliotecaproyecto-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8080 (el puerto por defecto de Spring Boot)
EXPOSE 8080

# El comando para iniciar la aplicación
CMD ["java", "-jar", "app.jar"]