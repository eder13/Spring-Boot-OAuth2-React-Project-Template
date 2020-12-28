# Spring Boot OAuth2 React Template

A full stack Spring Boot + React template with integrated OAuth2 (Github) support.

The index.html is served on `localhost:8081/` using Thymeleaf. The api is served on `localhost:8081/api` endpoint (as specified in `application.yml`).

## How it works

The React frontend is served under `src/main/ui`, after building the whole project, a production built
of the frontend is copied to `src/main/resources/built.js`. This is all done using the frontend-maven-plugin, which basically installs Node on Spring Boot. 

It's not necessary to build them separately. In fact, you just need to run 

    ./mvnw spring-boot:run   
    
If you just wanna build the project, run

    ./mvnw clean install

to generate a production build. In the background it will run the specified
npm commands (see `pom.xml`) and build the spring boot project.

## Requirements

* MySQL (community server is fine)
* JDK 8 or above
* (optional) maven
* (optional) Node.js

## Setup
0. Download/clone this template. Adjust username, password and port number in `application.yml` wrt your MySQL setup.


1. Create a Database called `db_springreact`. You need a table called `users` where (server) user data is stored (user info). 
   This boilerplate expects the following schema:

    ```txt
    +------------+------------------+------+-----+---------+----------------+
    | Field      | Type             | Null | Key | Default | Extra          |
    +------------+------------------+------+-----+---------+----------------+
    | userID     | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
    | name       | varchar(255)     | YES  |     | NULL    |                |
    | email      | varchar(320)     | YES  |     | NULL    |                |
    | newsletter | tinyint(1)       | NO   |     | 0       |                |
    +------------+------------------+------+-----+---------+----------------+
    ```

    To create this schema, run the following command.

    ```sql
    CREATE TABLE users (userID INT UNSIGNED NOT NULL AUTO_INCREMENT, name VARCHAR(255), email VARCHAR(320), newsletter BOOLEAN NOT NULL DEFAULT FALSE, PRIMARY KEY (userID));
    ```

    ***NOTE:*** If you want a different schema don't forget to adjust the configuration inside `Users.java` and `UsersRepository.java`


2. Go to your OAuth Provider and register your Web App. For GitHub use e.g.

* Application Name: MyApplication
* Homepage URL: http://localhost:8081
* Authorization callback URL: http://localhost:8081/login/oauth2/code/github

3. Copy your `cliendId` and `clientSecret` and edit your `application.yml` file with those.



## Installing other npm packages (optional)

There is a `package.json` inside the $ROOT of the project. You can simply add your additional npm packages here. If you 
have also NodeJS/npm installed on your local machine, you can also run `npm install <package>`, which will include the dependencies 
inside the `package.json`. When you build the project, the frontend-maven-plugin will then install all packages on the server side automatically.

## Working on the frontend only (optional)

As said, when the Spring Boot project is building, it will also build the frontend. There is also the possibility to 
watch the React source files, if you are altering this only:

    npm run-script watch

## A note to maven (optional)

This template comes with a maven wrapper so that you do not need to install maven. If you have maven on your system and want to 
run your own version of maven, you can build your own wrapper as described [here](https://www.baeldung.com/maven-wrapper):

    mvn -N io.takari:maven:wrapper

