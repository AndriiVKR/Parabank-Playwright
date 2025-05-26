FROM mcr.microsoft.com/playwright:v1.48.0-jammy
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://github.com/adoptium/temurin23-binaries/releases/download/jdk-23.0.1+11/OpenJDK23U-jdk_x64_linux_hotspot_23.0.1_11.tar.gz && \
    tar -xzf OpenJDK23U-jdk_x64_linux_hotspot_23.0.1_11.tar.gz -C /opt && \
    rm OpenJDK23U-jdk_x64_linux_hotspot_23.0.1_11.tar.gz && \
    apt-get install -y maven && \
    apt-get clean
ENV JAVA_HOME=/opt/jdk-23.0.1+11
ENV PATH=$JAVA_HOME/bin:$PATH
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY testng.xml .
COPY test-artifacts ./test-artifacts
COPY src/test/resources/registration-data.json ./src/test/resources/registration-data.json
COPY src/test/resources/credentials.json ./src/test/resources/credentials.json
RUN mvn clean install -DskipTests
CMD ["mvn", "test"]