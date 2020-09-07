FROM elementalconcept/java8-mysql
MAINTAINER janezmejias.09@gmail.com
VOLUME /tmp
EXPOSE 8080
ARG target/code-challenge-0.0.1-SNAPSHOT.jar
ADD target/code-challenge-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
