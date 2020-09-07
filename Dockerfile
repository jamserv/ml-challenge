FROM elementalconcept/java8-mysql
MAINTAINER janezmejias.09@gmail.com
VOLUME /tmp
EXPOSE 8080
ARG target/code-challenge-0.0.1-SNAPSHOT.jar
ADD target/code-challenge-0.0.1-SNAPSHOT.jar app.jar

ENV MYSQL_ROOT_PASSWORD root

RUN service mysql start && /bin/bash && \ 
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE DATABASE ml"

ENTRYPOINT ["java","-jar","app.jar"]

# docker build -t mlchallenge .

# docker run -p 5000:8080 mlchallenge
