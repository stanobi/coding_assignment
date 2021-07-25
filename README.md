# Coding Assignment
This is an application which reads displays all parents and it's child.

## Content
View Project Structure description and Instruction.

```
.
├── /src/
├──── /main/
├────── /java/
├──────── /com/daofab/coding_assignment/
├────────── /config                                 -> contains swaggers configuration class
├────────── /controller                             -> contains all restful api controller
├────────── /dto                                    -> contains all data transfer objects
├────────── /exception                              -> contain exception handling class
├────────── /model                                  -> contains all database entities
├────────── /repo                                   -> contains all jpa repositories which is used to access database tables
├────────── /service                                -> contains classes containing the app business logic
├────────── /util                                   -> contains classes containing common operations and values
├────────── CodingAssignmentApplication             -> Main Class
├────── /resources
├──────── /static
├──────── /templates
├──────── application.properties                    -> contains all external configurations
├──────── Child.json                                -> json file containing all child transactions
├──────── Parent.json                               -> json file containing all parent transactions
├──── /test                                         -> contains all test classes
├── /target                                         -> contains auto generated file
├── .gitignore                                      -> contains all files and folder that shoulnt be pushed to git
├── pom.xml                                         -> file used to manage dependencies 
└── README.md                                       -> application structure description and startup/setup guide
    
Instructions 

    1. To setup the application,  
        a. Ensure that jdk8 is installed
        b. Ensure that maven is installed 
        c. You will have to clone the project. https://github.com/stanobi/coding_assignment.git
        d. Application uses h2 database. To visit the database, After Starting up the Application, please visit http://localhost:9988/h2-console, username is sa , password is password
    
    2. To Start up the application by running the command on the terminal 'mvn spring-boot:run' in the root directory
    
    3. Considering that we have the swagger-ui configured for the service. To visiting the application on swagger-ui 
        a. Visit the link 'http://localhost:9988/swagger-ui.html' on the browser
    
    4. This is important : Load Parent and Child data from json into the database by visiting the api GET request : 'http://localhost:9988/api/v1/load-json'

    5. To Test: we can test via swagger-ui by visiting any of the links provided in the step 3 and providing the parameter as required. 

    6. To run unit test, you can run the command on the terminal 'mvn test'

```