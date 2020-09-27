# REST API
This Rest Api has been developed for The Agile Monkeys Api Test.The objective of the project is to manage customer data for a small shop.

##Technologies and libraries used
- Spring Boot 2.3.4
- Spring Data JPA
- Spring Boot Test
- PostgreSQL
- Docker
- OAuth2 authentication

##Features
This Rest Api can perform:
- User CRUD operations for Admins
- Customer CRUD operations for Users
- File upload management for Users

##Prerequisites
- Java 14
- Gradle
- Docker

##Installing

1. Clone Repository
    ```
    git clone https://github.com/Moneyba/api.git
    cd RestApi
    ```
   
2. Build the project
   ```
   SPRING_PROFILES_ACTIVE=dev ./gradlew clean build 
   ```
2. Create postgres with docker
    ```
    cd docker 
    docker-compose -f docker-compose-dev.yml up -d
    ```  
3. Create the schema

    Using your IDE, create a new schema named postgres in postgres database.
    This is the datasource URL: 
    
    `jdbc:postgresql://localhost:5432/postgres Username/Password: postgres/postgres` 

5. Run the project
    ```
    cd ..
    java -jar -Dspring.profiles.active=dev ./build/libs/RestApi-0.0.1-SNAPSHOT.jar 
    ```


#### For Production environment:
1. Build the project
    ```
    ./gradlew clean build 
    ```
2. Create postgres with docker
    ```
   cd docker 
   docker-compose up -d
   ```  
   
##OAuth2 authentication
To access to a private API method, it's necesarry to get a access token first.
To acheive that, wue use the OAuth 2.0 Password Grant authentication doing the following request
To authenticate to access the private API method:

To be able to use the REST API it's necessary to be authenticated. The authorization is made
through OAuth 2.0 Password Grant, through which a JWT access token is obtained after making the following request:

   ```  
    curl -X POST \
    http://localhost:8080/oauth/token \
    -H "Authorization: Basic `echo -n client:secret | base64`" \
    --form 'grant_type=password' \
    --form 'username=user@example.com' \
    --form 'password=password' 
   ```  
    
The POST parameters in this request are explained below.

- grant_type=password - This tells the server we’re using the Password grant type
- username= - The username of the user who wants to enter the application
- password= - The password of the user who wants to enter the application
- client_id= - The public identifier of the application.
- client_secret= - The public identifier secret.

The server replies with an access token in the following format:

   ```  
  {
      "access_token": "*** JWT token ***"
      "token_type": "bearer",
      "expires_in": 86399,
      "scope": "user_info",
      "jti": "d1991214-4ad2-4ef5-a8c1-4b2cc5247d16"
  }
   ```  

