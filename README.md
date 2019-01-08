# RetailRestService<br /><br />

## About Project:<br />
This project is a spring java REST service which implements GET,PUT methods.This service provides product information for a particular product based on id.This Restful service constructs response from combining the product name information it receives from an external data service and product price information from NoSQL database.The PUT method helps to update the products price in NoSQL Database.

### Technologies used:<br />
Java 1.8<br />
Marklogic(NO SQL database) 9.0.4<br />
Spring 2.1.0<br />
IntelliJ Idea IDE<br />
Soap UI<br />

### About Marklogic No SQL Database:<br />
Marklogic is a transactional/operational NoSQL document database<br />

### Marklogic can be installed from below.Use free developers license<br /> 
https://developer.marklogic.com/products<br />

#### after installation<br />   
1) start marklogic server in local -> ./ml local start<br /> 
2) go to localhost:8001(admin interface).Set local username,password<br />  
3) localhost:8000 is a queryconsole where you can serach for documents in database(Either use xquery or javascript)<br />
4)./ml local bootstrap (creates forests,databases etc)<br />

### Starting the webservice:<br />
1)Clone the project repository<br />
2)Make sure Marklogic is installed and server is up and running<br />
3)Run the following command in command prompt <br />
		java -jar target\RetailRestService-1.0-SNAPSHOT.jar<br />
4)Install Soap UI to hit the service.(Other tools like Postman also work.Or just use browser)<br />
5)Please find screenshots below to understand types of methods,scenarios,error messages etc.<br />


#### How to insert a document into marklogic?<br />
We need price information for a product in dstabase.For that we need to insert a document into database first.This can be done using below query in localhost:8000 queryConsole.Please find below.<br />



#### The ingested document can be viewed using below query:<br />
fn:doc("/data/canonical/product/13860428/price.xml")

#### Each document in marklogic is associated with a unique uri and permissions.<br />
<img width="1228" alt="screen shot 2019-01-07 at 5 57 20 pm" src="https://user-images.githubusercontent.com/5736706/50801043-b57a2f00-12a8-11e9-9987-7cedfbe19f6f.png">


Now lets hit the service in SOAP UI with a GET.Please see below screen shot.It retrieves product name and product price.<br />
<img width="1232" alt="screen shot 2019-01-07 at 6 26 02 pm" src="https://user-images.githubusercontent.com/5736706/50801465-82d13600-12aa-11e9-81f3-618d6b195d50.png">

Lets see a scenario where product is not found.<br />
<img width="1233" alt="screen shot 2019-01-07 at 6 26 34 pm" src="https://user-images.githubusercontent.com/5736706/50801484-97153300-12aa-11e9-9cf1-24ee7d79bd51.png">

Lets try to update the price using a PUT request.<br />
<img width="1224" alt="screen shot 2019-01-07 at 6 27 35 pm" src="https://user-images.githubusercontent.com/5736706/50801535-d0e63980-12aa-11e9-947e-b26ca719b0ad.png">

Now lets go check in the database to see if price got updated.<br />
<img width="1049" alt="screen shot 2019-01-07 at 6 27 54 pm" src="https://user-images.githubusercontent.com/5736706/50801553-e2c7dc80-12aa-11e9-8e61-c30b7efd2f2a.png">

Awesome.Now lets see what happens if we are trying to update a product price and product doesnt exist in database.<br />
<img width="1229" alt="screen shot 2019-01-07 at 6 28 13 pm" src="https://user-images.githubusercontent.com/5736706/50801575-f3785280-12aa-11e9-906e-7bce1445fe11.png">

Lets see what happens when you give a PUT request but you dont provide price in request or price is null.
<img width="1234" alt="screen shot 2019-01-07 at 6 28 33 pm" src="https://user-images.githubusercontent.com/5736706/50801581-fa06ca00-12aa-11e9-8327-3a16d9e911b4.png">

Lets see if we can update both price as well as currency.<br />
<img width="1228" alt="screen shot 2019-01-07 at 6 30 10 pm" src="https://user-images.githubusercontent.com/5736706/50801586-fecb7e00-12aa-11e9-9ac7-25e66b5f1a07.png">

Awesome!We are able to update both.<br />
<img width="969" alt="screen shot 2019-01-07 at 6 30 23 pm" src="https://user-images.githubusercontent.com/5736706/50801589-0428c880-12ab-11e9-9ded-c29302e79c1f.png">























 

