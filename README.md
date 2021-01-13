# Spring Boot OAuth2 React Template

A full stack Spring Boot + React template with integrated OAuth2 (Github) support.

The index.html is served on `localhost:8081/` using Thymeleaf. The api is served on `localhost:8081/api` endpoint (as specified in `application.yml`).
## Requirements

* MySQL (community server is fine)
* JDK 8 or above
* (optional) maven
* (optional) Node.js

## Quick Start
0. Clone this template. Adjust username, password and port number in `application.yml` wrt your MySQL setup.


1. Create a Database called `db_springreact`. You need a table called `user` where (server) user data is stored (user info).
   This boilerplate expects the following schema:

    ```txt
    +------------+------------------+------+-----+---------+----------------+
    | Field      | Type             | Null | Key | Default | Extra          |
    +------------+------------------+------+-----+---------+----------------+
    | id         | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
    | name       | varchar(255)     | YES  |     | NULL    |                |
    | email      | varchar(320)     | YES  |     | NULL    |                |
    | newsletter | tinyint(1)       | NO   |     | 0       |                |
    +------------+------------------+------+-----+---------+----------------+
    ```

   To create this schema, run the following command.

    ```sql
    CREATE TABLE user (id INT UNSIGNED NOT NULL AUTO_INCREMENT, name VARCHAR(255), email VARCHAR(320), newsletter BOOLEAN NOT NULL DEFAULT FALSE, PRIMARY KEY (id));
    ```

   ***NOTE:*** If you want a different schema don't forget to adjust the configuration inside `User.java` and `UserRepository.java`


2. Go to your OAuth Provider and register your Web App. For GitHub use e.g.

* Application Name: MyApplication
* Homepage URL: http://localhost:8081
* Authorization callback URL: http://localhost:8081/login/oauth2/code/github

3. Copy your `cliendId` and `clientSecret` and edit your `application.yml` file with those. The stuff that needs 
customization is also marked with TODOs.


4. Start the server using `./mvnw spring-boot:run` and navigate to [http://localhost:8081/](http://localhost:8081/)
## How it works

The React frontend is served under `src/main/frontend`, after building the whole project, a production built
of the frontend is copied to `src/main/resources/built.js`. This is all done using the frontend-maven-plugin, which basically installs Node on Spring Boot.

To build the backend + frontend and start the server, run

    ./mvnw spring-boot:run   

There is also a npm script for building the frontend if you just work on that

    npm run watch

__Side note__: Default white label errors have been disabled since otherwise React's router wouldn't kick in. This means
you have to handle 404 Errors on the client (inside React). For custom errors (authentication errors), instead of the
`/error` endpoint, the endpoint `/error?message=true` is used.

## Installing other npm packages (optional)

There is a `package.json` inside the $ROOT of the project. You can simply add your additional npm packages here. If you
have also NodeJS/npm installed on your local machine, you can also run `npm install <package>`, which will include the dependencies
inside the `package.json`. When you build the project, the frontend-maven-plugin will then install all packages on the server side automatically.

## A note to maven (optional)

This template comes with a maven wrapper so that you do not need to install maven. If you have maven on your system and want to
run your own version of maven, you can build your own wrapper as described [here](https://www.baeldung.com/maven-wrapper):

    mvn -N io.takari:maven:wrapper

### TODO
* Protect api endpoints from other users so that a user can only access his/her endpoint
* Add different roles (default is ROLE_USER) e.g. admin group in OAuth2