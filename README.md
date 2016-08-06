# Fibonacci Web Application
1. Write a Java library that implements a Fibonacci calculator. 
2. Make sure that you have unit tests that are covering the edge cases.
3. Create a Web application that hosts the functionality of the library with
    * Java back-end
    * JavaScript (React or Angular)  front-end
4. The service must have user authentication against an arbitrary type user database.
5. Make it possible to somehow control the biggest number a certain user can call the service on. 
   Display a meaningful error message if the user entered a bigger number than the allowed.
6. Make sure we can run your application easily and quickly. For example 
    * using Docker, ./gradlew bootRun, ...
    * or deploy it to a publicly available site

# Usage
To execute the application:

1. Generate project distribution using `mvn clean install` from project root
2. Execute 
   * `cd fibonacci-webapp; mvn spring-boot:run` or 
   * `java -jar fibonacci-webapp/target/fibonacci-webapp-<version>.jar`
3. Open browser `http://localhost:8080`
4. Login with username and password : `barry:password`
