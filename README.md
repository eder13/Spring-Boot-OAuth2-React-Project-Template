# Spring Boot React OAuth2 Template

> :warning: **This template is deprecated**: This repository exists for archive purposes only.

A template for quickly bootstrapping a Spring+React App with OAuth2 Authentication for prototyping.

## Features

-   Spring API with React frontend on a single domain
-   Google social login support
-   `.env` support

## Setup

1. Clone this template. Create a `.env` file in the root of the project and configure the project:

```dotenv
DOMAIN_URL = http://localhost:8080

DB_HOST = <your-host>
DB_NAME = <your-database-name>
DB_USER = <MySQL-user>
DB_PASSWORD = <MySQL-password>

GOOGLE_OAUTH2_CLIENT_ID = <your-client-id>
GOOGLE_OAUTH2_CLIENT_SECRET = <your-client-secret>
```

2. Create the aforementioned database (as you named it). You need a table called `user` where (server) user data is stored (user info).
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

3. Go to your OAuth Provider and register your Web App. For Google do the following:

-   Setup new credentials (_Create credentials_ > _OAuth-Client-ID_) in the [Google API Console](https://console.developers.google.com/)
    -   Name: MyApplication
    -   Authorized Redirect URIs: [http://localhost:8080/login/oauth2/code/google](http://localhost:8080/login/oauth2/code/google)

4. Copy your `cliendId` and `clientSecret` inside your `.env` file.

5. Start the Spring Boot Server:

```
./mvnw spring-boot:run --debug
```

Navigate to [http://localhost:8080](http://localhost:8080) to access the site.

## Demo

The To Do application [Taskify](https://github.com/eder13/taskify) was built using this template.
