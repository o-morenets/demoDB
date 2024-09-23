# demoDB
## Used different datasources:
- `jdbc:mysql://localhost:3306/employees` - see `jdbc.Employees`
- `jdbc:mysql://localhost:3306` - `jdbc.isolation_levels.MySqlConnection` (db name `test_db` is provided in query strings - see `jdbc.isolation_levels`)
- `jdbc:postgresql://localhost:5432/postgres` - see `META-INF/persistence.xml` - used in `hibernate.entitymanager.EntityManagerUtils`
- `jdbc:mysql://localhost:3306/sakila` - see `hibernate.cfg.xml` - used in `hibernate.session.HibernateSessionUtils`
- `jdbc:mysql://localhost:3306/test_db` - see `hibernate.properties` - used in `hibernate.session.HibernateSessionUtils`