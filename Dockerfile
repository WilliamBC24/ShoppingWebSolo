# Use an official Java runtime as a parent image
FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application source code to the container
COPY . .

# Install Tomcat
RUN apt-get update && \
    apt-get install -y wget && \
    wget -q https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.62/bin/apache-tomcat-9.0.62.tar.gz && \
    tar xzf apache-tomcat-9.0.62.tar.gz && \
    mv apache-tomcat-9.0.62 tomcat && \
    rm apache-tomcat-9.0.62.tar.gz && \
    apt-get clean

RUN chmod +x /app/tomcat/bin/catalina.sh

COPY ./dist/stbcStore.war /app/tomcat/webapps/

# Expose the port the app runs on
EXPOSE 8080

# Start Tomcat
CMD ["/app/tomcat/bin/catalina.sh", "run"]
