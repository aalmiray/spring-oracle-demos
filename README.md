# Spring + Oracle DEMOS

## Requirements

1. Java 11 as a minimum
2. Maven 3.8.4 (or use the included wrapper)
3. An AJD (Autonomous JSON Database). Register for free at https://cloud.oracle.com/free
4. A database wallet

## Demos

### spring-jdbc-demo

Showcases a relational table with a single JSON column.

You must update `spring-jdbc-demo/src/main/resources/application.properties` with the correct settings for your AJD:

```
spring.datasource.url=jdbc:oracle:thin:@<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

A copy of the wallet should be placed in a directory named `wallet`, adjacent to the `pom.xml` file.

### spring-soda-demo

Showcases a JSON collection via SODA.
https://docs.oracle.com/en/database/oracle/simple-oracle-document-access/

You must update `spring-soda-demo/src/main/resources/application.properties` with the correct settings for your AJD:

```
spring.datasource.url=jdbc:oracle:thin:@<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

A copy of the wallet should be placed in a directory named `wallet`, adjacent to the `pom.xml` file.

Run the `DemoApplication` class from your IDE or invoke `/.mvnw spring-boot:run` on the command line.

### spring-mongo-demo

Showcases the Oracle Mongo API via Spring-Data

You must update `spring-mongo-demo/src/main/resources/application.properties` with the correct settings for your AJD:

```
spring.datasource.url=jdbc:oracle:thin:@<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.data.mongodb.uri=mongodb://<username>:password>@<database>.<oci_region>.oraclecloudapps.com:27017/<username>?authMechanism=PLAIN&authSource=$external&ssl=true&retryWrites=false&loadBalanced=true
```

A copy of the wallet should be placed in a directory named `wallet`, adjacent to the `pom.xml` file.

Run the `DemoApplication` class from your IDE or invoke `/.mvnw spring-boot:run` on the command line.
