FROM openjdk:14-jdk-alpine
ARG FILES
COPY ${FILES} /home/app/
RUN mv /home/app/*.jar /home/app/app.jar
WORKDIR /home/app
ENTRYPOINT ["java","-jar","app.jar"]
