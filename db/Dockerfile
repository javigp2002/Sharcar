FROM mariadb:latest

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=sharcardb
ENV MYSQL_USER=sharcar
ENV MYSQL_PASSWORD=sharcar

COPY init.sql /docker-entrypoint-initdb.d/

EXPOSE 3306