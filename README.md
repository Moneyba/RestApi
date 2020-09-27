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
  
##Guidelines
###OAuth2 authentication

To be able to use the REST API you need to be authenticated. The authorization is made
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

- grant_type=password - This tells the server you’re using the Password grant type
- username=user@example.com - The username of a registered user who wants to enter the application
- password=password - The password of the user who wants to enter the application
- client_id=client - The public identifier of the application. 
- client_secret=secret - The public identifier secret.

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

###Use HTTP methods to operate on collections and entities

You can operate on resources using HTTP methods such as `POST`, `GET`, `PUT`, and `DELETE`.

You can perform these operations with both users and customers.

| Resource / HTTP method | POST (create)    | GET (read)  | PUT (update)           | DELETE (delete)    |
| ---------------------- | ---------------- | ----------- | ---------------------- | ------------------ |
| /user                  | Create new user  | List users  | Error                  | Error              |
| /user/{userId}         | Error            | Get user    | Update user if exists  | Delete user        |


###Upload and load images
+ To upload and image you need to use the method `POST` and the endpoint `http://localhost:8080/api/file`

    The valid formats for the image are BMP, GIF, JPG or PNG and the maximum size allowed is 5 MB.

    You will receive a successful message with a file name in UUID format.

   ```  
    {
        "message": "Uploaded the file successfully: 5b192c03-ecd4-4c73-b102-7be4ee91cbde.png"
    }
   ```  
+ To load an image you only need to use the method `GET` and the endpoint `http://localhost:8080/api/file/{fileName:.+}`
    
    The file name must be in UUID format.