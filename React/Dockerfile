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

# Declare ARG for server.url and JWT_SECRET
ARG SERVER_URL
ARG JWT_SECRET

# Set environment variables for the runtime stage
ENV JWT_SECRET=$JWT_SECRET
ENV SERVER_URL=$SERVER_URL