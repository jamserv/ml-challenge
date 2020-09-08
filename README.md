# Mercado Libre - Challenge
![Java CI with Maven](https://github.com/cafetochallengeusr09/nephrologists-back/workflows/Java%20CI%20with%20Maven/badge.svg)
[![CodeFactor](https://www.codefactor.io/repository/github/jamserv/ml-challenge/badge)](https://www.codefactor.io/repository/github/jamserv/ml-challenge)

## Description

Se pide construir una API (Java Groovi / Go) que cumpla con los siguientes requerimientos:

#### Request que debe responder:
```batch
➜  ~ curl -X GET 'http://localhost:9595/items/$ITEM_ID'
```
#### Respuesta
Debe responder la informacion unificada consultando a las siguientes APIs:
1. Api: https://api.mercadolibre.com/items/$ITEM_ID
   Descripcion: Informacion del Item
2. Api: https://api.mercadolibre.com/items/$ITEM_ID/children
   Descripcion: Informacion de los items hijos

## Data Base Design
![](https://github.com/jamserv/ml-challenge/blob/master/docs/img/1.png)
```batch
DROP TABLE IF EXISTS ml.childen;
    
DROP TABLE IF EXISTS ml.item;
    
CREATE TABLE ml.childen (
	item_id VARCHAR(255) NOT NULL,
	stop_time DATETIME,
	item VARCHAR(255),
	primary key (item_id)
) engine=InnoDB;
    
CREATE TABLE ml.item (
	item_id VARCHAR(255) NOT NULL,
	title VARCHAR(255),
	category_id VARCHAR(255),
	price DECIMAL(19,2),
	start_time DATETIME,
	stop_time DATETIME,
	primary key (item_id)
) engine=InnoDB;
    
ALTER TABLE ml.childen 
	ADD constraint 
	foreign key (item) 
	references item (item_id);
```

## Database Connection

### Dev Environment
* Please open file **application.integration.properties** locate in **/src/main/resources**
* Edit the next values:
```batch
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/ml?useSSL=true
spring.datasource.username=janezmejias
spring.datasource.password=Usradm9984-*
```

### Live Environment
* Please open file **application.properties** locate in **/src/main/resources**
* Edit the next values:
```batch
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/ml?useSSL=true
spring.datasource.username=janezmejias
spring.datasource.password=Usradm9984-*
```

## Run

### Run Server (local-dev)
```batch
mvn spring-boot:run
```

### Run Server - With Docker
1. To generate image
```batch
docker build -t mlchallenge . 
```
2. Run
```batch
docker run -it mlchallenge:latest /bin/bash
```
3. Docker hub
```batch
$ docker pull janezmejias09/mlchallenge:1.0
$ docker run -it janezmejias09/mlchallenge:1.0 /bin/bash/
```

### Reponse JSON
```batch
➜  ~ curl -X GET 'http://localhost:9595/items/MLU460998489'
{
   "item_id":"MLU460998489",
   "title":"Google Pixel 32gb Silver - Impecable!",
   "category_id":"MLU1055",
   "price":350.00,
   "start_time":"2019-03-02T20:31:02.000+0000",
   "stop_time":"2019-10-25T23:28:35.000+0000",
   "children":[
      {
         "item_id":"MLU468887129",
         "stop_time":"2020-04-25T22:10:52.000+0000"
      }
   ]
}
```   

## Unit Test
### Run All Test
```batch
mvn surefire:test -Dtest=GlobalTest
```
![](https://github.com/jamserv/ml-challenge/blob/master/docs/img/2.png)

## Logs
```shell
2020-09-06 12:27:04.712  INFO 3876 --- [           main] o.a.c.c.C.[.[.[/]                        : Initializing Spring TestDispatcherServlet ''
2020-09-06 12:27:04.713  INFO 3876 --- [           main] o.s.t.w.s.TestDispatcherServlet          : Initializing Servlet ''
2020-09-06 12:27:05.793  INFO 3876 --- [           main] o.s.b.a.e.w.EndpointLinksResolver        : Exposing 2 endpoint(s) beneath base path ''
2020-09-06 12:27:05.879  INFO 3876 --- [           main] o.s.s.c.ThreadPoolTaskExecutor           : Initializing ExecutorService 'applicationTaskExecutor'
2020-09-06 12:27:05.999  INFO 3876 --- [           main] o.s.t.w.s.TestDispatcherServlet          : Completed initialization in 1286 ms
2020-09-06 12:27:06.355  INFO 3876 --- [           main] o.s.b.w.e.t.TomcatWebServer              : Tomcat started on port(s): 36185 (http) with context path ''
2020-09-06 12:27:06.355  INFO 3876 --- [           main] c.c.m.ItemTest                           : Started ItemTest in 2.898 seconds (JVM running for 14.128)
2020-09-06 12:27:06.457  INFO 3876 --- [           main] c.c.a.c.ControllerBase                   : GET ONE FROM -> com.challenge.item.service.ItemServiceHandler$$EnhancerBySpringCGLIB$$a236b1ba WITH ID=MLU460998489
Hibernate: 
    select
        item0_.item_id as item_id1_1_0_,
        item0_.category_id as category2_1_0_,
        item0_.price as price3_1_0_,
        item0_.start_time as start_ti4_1_0_,
        item0_.stop_time as stop_tim5_1_0_,
        item0_.title as title6_1_0_ 
    from
        item item0_ 
    where
        item0_.item_id=?
Hibernate: 
    select
        item0_.item_id as item_id1_1_0_,
        item0_.category_id as category2_1_0_,
        item0_.price as price3_1_0_,
        item0_.start_time as start_ti4_1_0_,
        item0_.stop_time as stop_tim5_1_0_,
        item0_.title as title6_1_0_ 
    from
        item item0_ 
    where
        item0_.item_id=?
Hibernate: 
    select
        children0_.item_id as item_id1_0_0_,
        children0_.item as item3_0_0_,
        children0_.stop_time as stop_tim2_0_0_ 
    from
        childen children0_ 
    where
        children0_.item_id=?
Hibernate: 
    insert 
    into
        item
        (category_id, price, start_time, stop_time, title, item_id) 
    values
        (?, ?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        childen
        (item, stop_time, item_id) 
    values
        (?, ?, ?)
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 14.436 sec - in com.challenge.GlobalTest
2020-09-06 12:27:07.114  INFO 3876 --- [extShutdownHook] o.s.s.c.ThreadPoolTaskExecutor           : Shutting down ExecutorService 'applicationTaskExecutor'
2020-09-06 12:27:07.116  INFO 3876 --- [extShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2020-09-06 12:27:07.117  INFO 3876 --- [extShutdownHook] o.s.s.c.ThreadPoolTaskExecutor           : Shutting down ExecutorService 'applicationTaskExecutor'
2020-09-06 12:27:07.120  INFO 3876 --- [extShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2020-09-06 12:27:07.124  INFO 3876 --- [extShutdownHook] c.z.h.HikariDataSource                   : HikariPool-2 - Shutdown initiated...
2020-09-06 12:27:07.134  INFO 3876 --- [extShutdownHook] c.z.h.HikariDataSource                   : HikariPool-1 - Shutdown initiated...
2020-09-06 12:27:07.159  INFO 3876 --- [extShutdownHook] c.z.h.HikariDataSource                   : HikariPool-1 - Shutdown completed.
2020-09-06 12:27:07.159  INFO 3876 --- [extShutdownHook] c.z.h.HikariDataSource                   : HikariPool-2 - Shutdown completed.

Results :

Tests run: 2, Failures: 0, Errors: 0, Skipped: 0

```
## Project Folder Structure
```shell
➜  src git:(master) ✗ tree
.
├── main
│   ├── java
│   │   └── com
│   │       └── challenge
│   │           ├── api
│   │           │   └── commons
│   │           │       ├── BaseConnector.java
│   │           │       ├── Constants.java
│   │           │       ├── ControllerBase.java
│   │           │       ├── Messages.java
│   │           │       ├── ResponseBase.java
│   │           │       └── ServiceBase.java
│   │           ├── ChallengeApplication.java
│   │           ├── children
│   │           │   ├── model
│   │           │   │   └── Children.java
│   │           │   ├── repository
│   │           │   │   └── ChildrenRepository.java
│   │           │   └── service
│   │           │       ├── ChildrenServiceHandler.java
│   │           │       └── ChildrenService.java
│   │           └── item
│   │               ├── controller
│   │               │   └── ItemController.java
│   │               ├── model
│   │               │   ├── ItemBase.java
│   │               │   └── Item.java
│   │               ├── repository
│   │               │   └── ItemRepository.java
│   │               └── service
│   │                   ├── ItemServiceHandler.java
│   │                   └── ItemService.java
│   └── resources
│       ├── application.integration.properties
│       └── application.properties
└── test
    └── java
        └── com
            └── challenge
                ├── GlobalTest.java
                ├── helper
                │   ├── ChallengeMockBase.java
                │   ├── CleanUpTest.java
                │   ├── HttpMockMvcComponent.java
                │   └── PerformMockMvcComponent.java
                └── modularTest
                    └── ItemTest.java

22 directories, 25 files

```

## Services URL
See all services URI in [SWAGGER](http://localhost:9595/api-docs/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/)

## Cover Analysis
![](https://github.com/jamserv/ml-challenge/blob/master/docs/img/3.png)
