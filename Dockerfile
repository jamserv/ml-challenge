#new file
FROM ubuntu:20.10

VOLUME /tmp

EXPOSE 9595

RUN apt-get update

#============================== JAVA_&_MAVEN ===============================

RUN apt install openjdk-8-jdk -y

#============================== MY_SQL =====================================
ENV MYSQL_ROOT_PASSWORD root

RUN apt-get install mysql-server -y

RUN service mysql start && /bin/bash && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE DATABASE ml"

RUN service mysql start && /bin/bash && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE USER 'janezmejias'@'localhost' IDENTIFIED BY 'Usradm9984-*'"

RUN service mysql start && /bin/bash && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "GRANT ALL PRIVILEGES ON *.* TO 'janezmejias'@'localhost'"
#===========================================================================

ARG target/code-challenge-0.0.1-SNAPSHOT.jar
ADD target/code-challenge-0.0.1-SNAPSHOT.jar app.jar
ADD run.sh run.sh

#============================================================================

COPY . .
RUN chmod +x run.sh
ENTRYPOINT ["/run.sh"]