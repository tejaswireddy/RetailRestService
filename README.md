# RetailRestService

About Project:
This project is a spring java REST service which implements GET,PUT methods.This service provides product information for a particular product based on id.This Restful service constructs response from combining the product name information it receives from an external data service and product price information from NoSQL database.The PUT method helps to update the products price in NoSQL Database.

Technologies used:
Java 1.8
Marklogic(NO SQL database) 9.0.4
Spring 2.1.0
IntelliJ Idea IDE
Soap UI

About Marklogic No SQL Database:
Marklogic is a transactional/operational NoSQL document database.

Marklogic can be installed from below.Use free developers license.
https://developer.marklogic.com/products 

after installation 
1) start marklogic server in local -> ./ml local start
2) go to localhost:8001(admin interface).Set local username,password.
3) localhost:8000 is a queryconsole where you can serach for documents in database(Either use xquery or javascript)
4)./ml local bootstrap (creates forests,databases etc)

Starting the webservice:
1)Clone the project repository
2)Make sure Marklogic is installed and server is up and running
3)Run the following command in command prompt 
		java -jar target\RetailRestService-1.0-SNAPSHOT.jar
4)Install Soap UI to hit the service.(Other tools like Postman also work.Or just use browser)
5)Please find screenshots below to understand types of methods,scenarios,error messages etc.


How to insert a document into marklogic?
We need price information for a product in dstabase.For that we need to insert a document into database first.This can be done using below query in localhost:8000 queryConsole.Please find below.






















 

