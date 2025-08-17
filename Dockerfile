# ====== build args는 반드시 FROM보다 위에 선언 ======
ARG BUILDER_IMAGE=gradle:7.6.0-jdk17
ARG RUNTIME_IMAGE=amazoncorretto:17.0.7-alpine

# ============ (1) Builder ============
FROM ${BUILDER_IMAGE} AS builder

USER root
WORKDIR /app
ENV GRADLE_USER_HOME=/home/gradle/.gradle
RUN mkdir -p $GRADLE_USER_HOME && chown -R gradle:gradle /home/gradle /app
USER gradle

COPY --chown=gradle:gradle gradlew ./
COPY --chown=gradle:gradle gradle ./gradle
COPY --chown=gradle:gradle build.gradle settings.gradle ./
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon --refresh-dependencies dependencies || true

COPY --chown=gradle:gradle src ./src
RUN ./gradlew clean build --no-daemon --no-parallel -x test

# ============ (2) Runtime ============
FROM ${RUNTIME_IMAGE}
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java","-jar","app.jar"]
