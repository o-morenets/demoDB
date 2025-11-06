# demoDB

Prerequisites:
- JDK 21+
- Docker Compose
- test_db (separate project) with two databases: `employees` and `sakila`
  - run `docker compose up -d` from `test_db` project to start DB containers (starts MySQL with the above databases and Adminer tool)

Run `docker compose up` - creates `MySQL` (port:`3307`) and `PostgreSQL` (potr:`5433`) DB containers


## Simultaneous DB access

### `jdbc:mysql://localhost:3306/employees`

#### [Employees.java](src/main/java/jdbc/Employees.java)
- creates and starts 100 threads
- each thread runs SQL query: fetch Employees


## DB data sources

### Hibernate configuration
### `jdbc:mysql://localhost:3306/employees`
### `jdbc:mysql://localhost:3306/sakila`

#### [RunSessionWithConfig](src/main/java/hibernate/session/RunSessionWithConfig.java)
- demonstrates how to create DB connection using `hibernate.cfg.xml` and `hibernate.properties`
- used databases: `Sakila` and `Employees`


## Transaction isolation levels

### `jdbc:mysql://localhost:3307/isolation-levels`

#### [Dirty Read](src/main/java/jdbc/isolation_levels/dirtyread/DirtyReadExample.java)

#### [Non-Repeatable Read](src/main/java/jdbc/isolation_levels/nonrepeatableread/NonRepeatableReadExample.java)

#### [Phantom Read](src/main/java/jdbc/isolation_levels/phantomread/PhantomReadExample.java)


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
