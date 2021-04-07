# Spring Boot OAuth2 React Template

A full stack Spring Boot + React template with integrated OAuth2 (Google) support.

The index.html is served on [localhost:8081](http://localhost:8081/) using Thymeleaf. The api is served on [localhost:8081/api](http://localhost:8081/api) endpoint (as specified in `application.yml`).

## Requirements

- MySQL (community server is fine)
- JDK 8 or above
- (optional) maven
- (optional) Node.js

## Environment Variables

Create a `.env` file inside the root of the project. The environment variables can be used in React (`process.env.ENV_VAR_NAME`) and
also referenced inside `application.yml` like `${ENV_VAR_NAME}` thanks to the [dotenv-java addon](https://github.com/cdimascio/dotenv-java).

## Quick Start

0. Clone this template. Create a `.env` file in the root of the project and configure the database and OAuth2 google settings:

```dotenv
DB_NAME = <your-database-name>
DB_USER = <MySQL-user>
DB_PASSWORD = <MySQL-password>

GOOGLE_OAUTH2_CLIENT_ID = <your-client-id>
GOOGLE_OAUTH2_CLIENT_SECRET = <your-client-secret>
```

1. Create the aforementioned database (as you named it). You need a table called `user` where (server) user data is stored (user info).
   This boilerplate expects the following schema:

   ```txt
   +------------+------------------+------+-----+---------+----------------+
   | Field      | Type             | Null | Key | Default | Extra          |
   +------------+------------------+------+-----+---------+----------------+
   | id         | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
   | name       | varchar(255)     | YES  |     | NULL    |                |
   | email      | varchar(320)     | YES  |     | NULL    |                |
   +------------+------------------+------+-----+---------+----------------+
   ```

   To create this schema, run the following command.

   ```sql
   CREATE TABLE user (id INT UNSIGNED NOT NULL AUTO_INCREMENT, name VARCHAR(255), email VARCHAR(320), PRIMARY KEY (id));
   ```

   **_NOTE:_** If you want a different schema don't forget to adjust the configuration inside `User.java` and `UserRepository.java`

2. Go to your OAuth Provider and register your Web App. For Google do the following:

- Setup new credentials (_Create credentials_ > _OAuth-Client-ID_) in the [Google API Console](https://console.developers.google.com/)
  - Name: MyApplication
  - Authorized Redirect URIs: [http://localhost:8081/login/oauth2/code/google](http://localhost:8081/login/oauth2/code/google)

3. Copy your `cliendId` and `clientSecret` inside your `.env` file.

4. Start the server using `./mvnw spring-boot:run` and navigate to [http://localhost:8081/](http://localhost:8081/).
   You should now be able to login with Google. After a successful login you are presented with the (protected) content.

## How it works

The React frontend is served under `src/main/frontend`, after building the whole project, a production built
of the frontend is copied to `src/main/resources/static/built/bundle.js`. This is all done using the frontend-maven-plugin, which basically installs Node on Spring Boot.

To build the backend + frontend and start the server, run

    ./mvnw spring-boot:run

There is also a npm script for building the frontend if you just work on that (refresh page afterwards).

    npm run watch

**Side note**: Default white label errors have been disabled since otherwise React's router wouldn't kick in. This means
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
