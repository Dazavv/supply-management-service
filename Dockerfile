FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY gradlew ./
COPY gradle gradle
COPY build.gradle* settings.gradle* gradle.properties* ./
RUN chmod +x ./gradlew

RUN ./gradlew --no-daemon dependencies
COPY src src
RUN ./gradlew --no-daemon clean bootJar && cp build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]