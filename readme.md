## Dev

### postgresql

sudo -i -u postgres 

psql

CREATE USER admin;

ALTER ROLE admin WITH CREATEDB;

CREATE DATABASE trace OWNER admin;

ALTER USER admin WITH ENCRYPTED PASSWORD 'admin';

### test Api

http://localhost:8080/swagger-ui.htm




## Deploiement

`sudo apt-get install openjdk-8-jdk maven tomcat8 postgresql`

CREATE DATABASE tracetest OWNER admin;
