FROM openjdk:8u212-jdk

COPY ./target/todo.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch todo.jar'

ENTRYPOINT ["java","-jar","todo.jar"]
