# demoDB

Prerequisites:
- JDK 21+
- Docker
- Docker Compose

Run `docker compose up` - it creates `MySQL` and `PostgreSQL` DB containers with `Adminer` tool


## DB data sources

### Hibernate configuration
### `jdbc:mysql://localhost:3306/employees`
### `jdbc:mysql://localhost:3306/sakila`

#### [RunSessionWithConfig](src/main/java/hibernate/session/RunSessionWithConfig.java)
- demonstrates how to create DB connection using `hibernate.cfg.xml` and `hibernate.properties`
- used databases: `Sakila` and `Employees`

## Transaction isolation levels
> TODO
### `jdbc:mysql://localhost:3306/my-examples` - ISOLATION LEVELS // TODO use another db, say `sakila`


## Simultaneous DB access

### `jdbc:mysql://localhost:3306/employees`

#### [Employees.java](src/main/java/jdbc/Employees.java)
- creates ans starts 100 threads
- each thread runs SQL query: fetch Employees


## 🦾 High load DB Simulation:

### `jdbc:postgresql://localhost:5432/postgres`

#### [DbHighLoadSimulator](src/main/java/db_high_load/DbHighLoadSimulator.java)
- executes db migration script [V1__create_high_load_schema](src/main/resources/db/migration/V1__create_high_load_schema.sql)
    - creates schema `high_load`, if not exists
    - creates table `documents` if not exists
    - populates it with random text data (⚠️ wait for generating 10_000_000 rows!)
- creates and starts 300 threads
- each thread runs SQL query in separate DB connection: fulltext search for some text in `documents`

(Optional: run `mvn flyway:migrate` to populate DB with data)

## Inheritance Demo

### `jdbc:postgresql://localhost:5432/postgres` - uses [persistence.xml](src/main/resources/META-INF/persistence.xml)

#### [InheritanceDemo](src/main/java/hibernate/entitymanager/inheritance/InheritanceDemo.java)
- creates several tables annotated with `@Entity` in schema `public`


## Relations Demo

### `jdbc:postgresql://localhost:5432/postgres` - uses [persistence.xml](src/main/resources/META-INF/persistence.xml)

#### [ManyToManyDemo](src/main/java/hibernate/entitymanager/relations/many_to_many/ManyToManyDemo.java)
- used entities: `Employee`, `Guild`

#### [ManyToOneDemo](src/main/java/hibernate/entitymanager/relations/many_to_one/ManyToOneDemo.java)
- used entities: `Address`, `Office`

#### [OneToManyUnidirectionalDemo](src/main/java/hibernate/entitymanager/relations/one_to_many_unidirectional/OneToManyUnidirectionalDemo.java)
- used entities: `Person`, `Note`

#### [OneToManyBidirectionalDemo](src/main/java/hibernate/entitymanager/relations/one_to_many_bidirectional/OneToManyBidirectionalDemo.java)
- used entities: `Customer`, `Order`

#### [OneToOneDemo](src/main/java/hibernate/entitymanager/relations/one_to_one/OneToOneDemo.java)
- used entities: `User`, `Address`, `Profile`, `Photo`, `Message`, `AddressEmbeddable`
