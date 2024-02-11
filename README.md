# Spring-boot-Ecommerce-RestApi
This project builds the backend of an e-commerce application using the Spring Boot framework. It exposes many functional endpoints. You will see the details below. The project utilizes a relational database. Here's how to run it step by step:

Clone the project to your own repository.\
Then create a MySQL connection.\
Create a MySQL schema.\
Run the ECOMMERCESQL.sql file with MySQL Workbench.\
After that, modify the application.properties file under the spring folders as follows.

```
logging.level.root=warn

server.port=3000
server.servlet.contextPath=/api

spring.main.allow-circular-references=true
spring.datasource.url=jdbc:mysql://localhost:3306/[<Your Schema name>]
spring.datasource.username=[<Your Connection username>]
spring.datasource.password=[<Your Connection password>]
```


After that your project is ready to run. You can run it using the SchoolprojectApplication.java file.

![erdiagram](UML_DIAGRAM.jpg)

EndPoints;


[GET /teachers](http://localhost:3000/api/users) \