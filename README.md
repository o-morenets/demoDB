# demoDB

Run `docker compose up` - it creates mySql and Postgres DB containers with Adminer tool

### DB MySQL

- `jdbc:mysql://localhost:3306/my-examples` - ISOLATION LEVELS // TODO use another db, say `sakila`
- `jdbc:mysql://localhost:3306/sakila` - see `hibernate.cfg.xml` - used in `hibernate.session.HibernateSessionUtils` // 
- `jdbc:mysql://localhost:3306/test_db` - see `hibernate.properties` - used in `hibernate.session.HibernateSessionUtils` // 


## Simultaneous DB access
### `jdbc:mysql://localhost:3306/employees`

- run [Employees.java](src/main/java/jdbc/Employees.java)
  - creates ans starts 100 threads
  - each thread runs SQL query: fetch Employees

## 🦾 High load DB Simulation:
### `jdbc:postgresql://localhost:5433/postgres?currentSchema=high_load`

- run `mvn flyway:migrate`
  - creates schema `high_load`, if not exists
  - creates table `documents` if not exists
  - populates it with random text data (⚠️ wait for generating 10_000_000 rows!)
- run [DbHighLoadSimulator](src/main/java/db_high_load/DbHighLoadSimulator.java)

## Inheritance Demo
### `jdbc:postgresql://localhost:5433/postgres`

- run [InheritanceDemo](src/main/java/hibernate/entitymanager/inheritance/InheritanceDemo.java)
  - uses [persistence.xml](src/main/resources/META-INF/persistence.xml)
  - creates several tables in schema `public`

