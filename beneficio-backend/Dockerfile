FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /build

RUN apk add --no-cache maven

# 1. Compila o EJB
COPY beneficio-ejb/pom.xml /ejb/pom.xml
COPY beneficio-ejb/src /ejb/src
RUN cd /ejb && mvn clean package -DskipTests -q

# 2. Cria o JAR client e coloca direto na pasta lib
RUN mkdir -p lib && \
    jar -cf lib/beneficio-ejb-client.jar \
        -C /ejb/target/classes br/com/beneficio/ejb/service \
        -C /ejb/target/classes br/com/beneficio/ejb/to \
        -C /ejb/target/classes br/com/beneficio/ejb/exception \
        2>/dev/null || true

# 3. Copia o projeto Spring Boot
COPY beneficio-backend/pom.xml .
COPY beneficio-backend/src ./src

# 4. Compila (agora o lib/ existe e o pom tem scope system!)
RUN mvn clean package -DskipTests -B

# Imagem final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/beneficio-backend-*.jar app.jar

EXPOSE 8090
ENV WILDFLY_HOST=wildfly-ejb WILDFLY_PORT=8080 EJB_USERNAME=ejbuser EJB_PASSWORD=ejbpass123

HEALTHCHECK --interval=20s --timeout=5s --start-period=60s --retries=5 \
    CMD wget -q --spider http://localhost:8090/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]