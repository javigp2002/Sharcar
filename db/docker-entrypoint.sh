#!/bin/bash
set -e

# Start MariaDB service
service mariadb start

# Wait for MariaDB to be ready
until mariadb -e "SELECT 1"; do
  echo "MariaDB is starting up"
  sleep 1
done

# Create database and user
mariadb -e "CREATE DATABASE IF NOT EXISTS $MYSQL_DATABASE;"
mariadb -e "CREATE USER IF NOT EXISTS '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';"
mariadb -e "GRANT ALL PRIVILEGES ON $MYSQL_DATABASE.* TO '$MYSQL_USER'@'%';"
mariadb -e "FLUSH PRIVILEGES;"

# Create tables
mariadb $MYSQL_DATABASE <<EOF
CREATE TABLE IF NOT EXISTS \`Enterprise\` (
  \`id\` int(11) NOT NULL,
  \`name\` varchar(60) NOT NULL,
  PRIMARY KEY (\`id\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS \`User\` (
  \`email\` varchar(100) NOT NULL,
  \`name\` varchar(100) NOT NULL,
  \`surname\` varchar(100) NOT NULL,
  \`password\` varchar(100) NOT NULL,
  \`enterprise\` int(11) DEFAULT NULL,
  PRIMARY KEY (\`email\`),
  KEY \`user_Enterprise_id_fk\` (\`enterprise\`),
  CONSTRAINT \`user_Enterprise_id_fk\` FOREIGN KEY (\`enterprise\`) REFERENCES \`Enterprise\` (\`id\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS \`Vehicle\` (
  \`id\` int(11) NOT NULL AUTO_INCREMENT,
  \`name\` varchar(50) NOT NULL,
  \`model\` varchar(50) NOT NULL,
  \`brand\` varchar(50) NOT NULL,
  \`year\` int(11) DEFAULT NULL,
  \`maxPassenger\` int(11) NOT NULL,
  \`userEmail\` varchar(100) NOT NULL,
  PRIMARY KEY (\`id\`),
  KEY \`Vehicle_User_email_fk\` (\`userEmail\`),
  CONSTRAINT \`Vehicle_User_email_fk\` FOREIGN KEY (\`userEmail\`) REFERENCES \`User\` (\`email\`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS \`Locations\` (
  \`id\` int(11) NOT NULL AUTO_INCREMENT,
  \`city\` varchar(100) NOT NULL,
  \`street\` varchar(100) NOT NULL,
  \`name\` varchar(100) NOT NULL,
  \`enterprise\` int(11) NOT NULL,
  PRIMARY KEY (\`id\`),
  KEY \`Locations_Enterprise_id_fk\` (\`enterprise\`),
  CONSTRAINT \`Locations_Enterprise_id_fk\` FOREIGN KEY (\`enterprise\`) REFERENCES \`Enterprise\` (\`id\`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

CREATE TABLE IF NOT EXISTS \`Inscription\` (
  \`id\` int(11) NOT
