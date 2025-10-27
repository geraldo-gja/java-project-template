# Build stage
FROM gradle:8-jdk21 AS builder
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
RUN apk update && apk upgrade
RUN apk add --no-cache tzdata
ENV TZ=Europe/Lisbon

WORKDIR /opt/app
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# Expose application port
EXPOSE 8080

# JVM options
ENV JAVA_OPTS="-Xms256m -Xmx1024m -XX:+UseG1GC"

# Start application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /opt/app/app.jar"]