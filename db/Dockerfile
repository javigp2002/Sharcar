FROM ubuntu:20.04

RUN apt-get update && apt-get install -y mariadb-server && apt-get clean

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=sharcardb
ENV MYSQL_USER=sharcar
ENV MYSQL_PASSWORD=sharcar

EXPOSE 3306

LABEL version="1.0"
LABEL description="MariaDB Server"

COPY ./docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

HEALTHCHECK --start-period=1m \
  CMD mariadb -e 'SELECT @@datadir;' || exit 1

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["mariadbd"]
