# Stage 1: Build the React application
FROM --platform=$BUILDPLATFORM node:lts AS react-build
WORKDIR /workspace/frontend

# Define build arguments for Vite environment variables
ARG VITE_API_BASE_URL

# Set environment variables for the build stage
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL

COPY React/package*.json ./
RUN npm install
COPY React/ ./
RUN npm run build

# Stage 2: Build the Spring Boot application
FROM eclipse-temurin:21-jdk-jammy AS spring-boot-build
WORKDIR /workspace/app

# Set environment variables for the runtime stage
ENV JWT_SECRET=$JWT_SECRET
ENV server.url=$server.url

# Install Maven and other necessary tools
RUN apt-get update \
  && apt-get install -y ca-certificates curl git --no-install-recommends \
  && rm -rf /var/lib/apt/lists/*

# Set Maven environment variables
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "/root/.m2"

# Copy Maven from the official Maven Docker image
COPY --from=maven:3.9.7-eclipse-temurin-11 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.7-eclipse-temurin-11 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.7-eclipse-temurin-11 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

# Link Maven to the bin directory for easy execution
RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

# Copy the Spring Boot application's pom.xml and source code
COPY ./postcode/pom.xml .
COPY ./postcode/src src
# Copy the built React application to the Spring Boot static resources directory
COPY --from=react-build /workspace/frontend/build /workspace/app/src/main/resources/static

# Build the Spring Boot application without running tests
RUN mvn install -DskipTests -P dev -q

# Stage 3: Run the Spring Boot application
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=spring-boot-build /workspace/app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]