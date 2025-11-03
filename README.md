# demoDB

Run `docker compose up` - creates mySql and Postgres DB containers with Adminer tool.

### DB MySQL: `jdbc:mysql://localhost:3306`

- `jdbc:mysql://localhost:3306/employees` - see `jdbc.Employees`
- `jdbc:mysql://localhost:3306/my-examples` - ISOLATION LEVELS
- `jdbc:mysql://localhost:3306/sakila` - see `hibernate.cfg.xml` - used in `hibernate.session.HibernateSessionUtils`
- `jdbc:mysql://localhost:3306/test_db` - see `hibernate.properties` - used in `hibernate.session.HibernateSessionUtils`


## 🦾High load DB Simulation:
### `jdbc:postgresql://localhost:5433/postgres?currentSchema=high_load`

- run `mvn flyway:migrate`
  - creates schema `high_load`, if not exists
  - creates table `documents` if not exists
  - populates it with random text data (⚠️ wait for generating 10_000_000 rows!)
- run [DbHighLoadSimulator](src/main/java/db_high_load/DbHighLoadSimulator.java)

## Inheritance Demo
### `jdbc:postgresql://localhost:5433/postgres`

- run [InheritanceDemo](src/main/java/hibernate/entitymanager/inheritance/InheritanceDemo.java) (it creates several tables for entities)

