# Spring Boot React Template

A template for quickly bootstrapping a Spring+React App.

## Features 

* Spring API with React frontend on a single domain
* Hot Module Replacement
* Google social login support
* `.env` support

## Setup

0. Clone this template. Create a `.env` file in the root of the project and configure the project:

```dotenv
DOMAIN_URL = http://localhost:8080

DB_HOST = <your-host>
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

2. Go to your OAuth Provider and register your Web App. For Google do the following:

- Setup new credentials (_Create credentials_ > _OAuth-Client-ID_) in the [Google API Console](https://console.developers.google.com/)
  - Name: MyApplication
  - Authorized Redirect URIs: [http://localhost:8080/login/oauth2/code/google](http://localhost:8080/login/oauth2/code/google)

3. Copy your `cliendId` and `clientSecret` inside your `.env` file.


4. Start the Spring Boot Server: 

```
./mvwn spring-boot:run
```

5. Start the Webpack Dev Server:

```
npm start
```

Navigate to [http://localhost:8080](http://localhost:8080) and enjoy react hot reloading :)

# Deployment

See prod branch instructions (configuration without HMR and React Built set to production).
