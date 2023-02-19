FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle/ ./gradle/

RUN ./gradlew dependencies --no-daemon

COPY src ./src/

CMD ["./gradlew", "bootRun", "-x", "test"]