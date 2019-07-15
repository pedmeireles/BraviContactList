# BraviContactList
This repository is to implemment a simple contact list using spring mvc as an RESTful API. 

# Requirements

All the application was dockerized. So, to run it, the user requires to have:

* Docker
* Docker-compose

Inside the application, the following packages were added in Maven:

* MySQL Integration;
* Flyway Migration
* Spring Data JPA
* Lombok
* Spring Web Starter
* Spring REST Docs
* Rest Repositories.


# How to run

1. Clone the repository.
2. Go to the **contact-person** directory.
3. Run the following command:

```
docker-compose up

```

That's it. 

# What the container is doing ?

The docker-compose will :
1. Download the maven and MySql Images;
2. Set a network for MySQL And Spring communicate. Either way, the ports were mirrored for the host if the user wants to edit or review something.
3. In the MySQL container, it will create a volume that is mirrored inside the data folder of the project. So, if the container is clean up, the data still persists inside the application.
4. The maven image will get the Spring Project and run it.

! Warning: In the first run, it may have some run condition between the images, and it can happen that the spring will set before the flyway configures the database. If that happens, the application will warn an error message. But don't worry: If the spring applicatino wen't down or requires an start, it is automatically done by the docker-compose, and eventually, the first configuration will run smoothy (in about a minute or something like that). 

Done! The application is running in docker!

# Endpoints implemented.

The endpoints were documented using the postman application (https://www.getpostman.com/), inside the **postman directory** in the contact-person folder. 

But the following table describes hoe the REST MVC endpoints works:

| Protocol + URL                                                             	| operation                              	| request body                                                                    	| response body                                        	|
|----------------------------------------------------------------------------	|----------------------------------------	|---------------------------------------------------------------------------------	|------------------------------------------------------	|
| GET http://localhost:8080/api/person                                       	| Get All People (not any contact)       	| Don't require                                                                   	| json                                                 	|
| GET http://localhost:8080/api/person/{{personID}}                          	| Get specific person (Based on it's id) 	| Don't require                                                                   	| json                                                 	|
| POST http://localhost:8080/api/person                                      	| Create a person (Without contact)      	| {"name": "String"}                                                              	| json                                                 	|
| PUT http://localhost:8080/api/person/{{personID}}                          	| Update specific person                 	| {"name": "Stirng"}                                                              	| json                                                     	|
| DELETE http://localhost:8080/api/person/{{personID}}                       	| Delete a person and all it's contacts  	| Don't require                                                                   	| {"Delete": True} if deletes. Error message otherwise 	|
| GET http://localhost:8080/api/person/{{personID}}/contact                  	| Get All contacts from person           	| Don't require                                                                   	| json                                                 	|
| GET http://localhost:8080/api/person/{{personID}}/contact/{{contactID}}    	| Get Specific contact from person       	| Don't require                                                                   	| json                                                 	|
| POST http://localhost:8080/api/person/{{personID}}/contact                 	| Create contact for person              	|  {"type": String(email, whatsup, website), "value": String(the value of type)}  	| json                                                 	|
| PUT http://localhost:8080/api/person/{{personID}}/contact/{{contactID}}    	| Update a specific contact from person  	| {"type": String(email, whatsup, website), "value": String(the value of type)}   	| json                                                 	|
| DELETE http://localhost:8080/api/person/{{personID}}/contact/{{contactID}} 	| Delete a contact                       	| Don't require                                                                   	| {"Delete": True} if deletes. Error message otherwise 	|



# Useful terminal commands.

If you need to clean all the containers used by this application, run:

```
 docker-compose down -v --rmi all --remove-orphans

```

If you want to check the mysql container, run:

```
docker exec -it bravi-mysql mysql -p
```
The password can be found in the docker-compose.yml: services -> bravi-mysql -> environment -> MYSQL_ROOT_PASSWORD

