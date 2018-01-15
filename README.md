# myRetail RESTful service

RESTful service that can retrieve product and price details by ID. Prices can also be updated.

The products are retrieved by ID from an external service. The prices are stored in myRetail internal database
with key being the product id.

The service is hosted in Heroku which is a cloud Platform as a Service (PaaS). Heroku was chosen as it is good
for spinning up proof-of-concepts quickly without having to focus on the deployment details.

## Framework used

Spring Boot was chosen as the framework because it enables rapid development of robust Java based applications.
Making use of the `spring-boot-starter-data` libraries makes it easy to build a RESTful application and
have the application plug in to a database of the developer's choosing.

Spring Boot is also well-documented and there are many tutorials and examples out on the web.


## Database used

Cassandra, a NoSQL database is used to store the prices for products. The product id is they key.

Cassandra was chosen as it was designed to handle large amounts of data across many servers. This is
appropriate for a multi-national corporation such as myRetail. The fetching of data is very fast and there
is not a single point of failure. Furthermore, Cassandra scales well. New servers can be added as more capacity
is needed.

The database is provided by Instaclustr Heroku plugin which hosts the database in AWS.

## Get the code

git clone https://github.com/rstruthers1/price-is-right


## Build instructions - local

Run the following command:

```
./gradlew clean build
```

## Run the unit tests and generate test coverage reports - local

Run the following command:

```
./gradlew clean test jacocoTestReport
```

If there is a test failure, check the test results in `build/reports/tests/test/index.html`

The test coverage metrics are provided by the JaCoCo plugin. The minimum coverage currently is
set at 80% for every coverage category.

If the coverage is below 80% for any  coverage category, then the build will fail. Please add
more unit tests. To check the coverage report, see: `build/reports/jacoco/test/html/index.html`  

If the tests pass and the coverage is above 80%, a coverage report will be printed out at the 
console. Example:

 ```
 - instruction coverage rate is: 96.91%, minimum is 80%
 - branch coverage rate is: 100.0%, minimum is 80%
 - line coverage rate is: 95.38%, minimum is 80%
 - complexity coverage rate is: 85.0%, minimum is 80%
 - method coverage rate is: 82.35%, minimum is 80%
 - class coverage rate is: 83.33%, minimum is 80%
Passed Code Coverage Checks
```


## To Run - local

Run the following command:

```
./gradlew bootRun
```

## Deploy to heroku

Deploy will happen to heroku upon any push to master in github

## Endpoints - local

*Get product price*

GET http://localhost:8080/products/{id}

Headers:

```
Accept:application/json
```

*Update product price*

POST http://localhost:8080/products/{id}

Headers:

```
Accept:application/json
Content-Type:application/json
```

*request body*

```
{
    "value": 12.29,
    "currency_code": "USD"
}
```


## Endpoints - remote

*Get product price*

GET http://price-is-right-rstruthers.herokuapp.com/products/{id}

Headers:

```
Accept:application/json
```

*Update product price*

PUT http://price-is-right-rstruthers.herokuapp.com/products/{id}

Headers:

```
Accept:application/json
Content-Type:application/json
```

*request body*

```
{
    "value": 12.29,
    "currency_code": "USD"
}
```

## Example product ids

```
15117729, 16483589, 16696652, 16752456, 15643793
```












