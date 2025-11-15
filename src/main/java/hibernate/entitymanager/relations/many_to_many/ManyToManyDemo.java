package hibernate.entitymanager.relations.many_to_many;


import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

public class ManyToManyDemo {

	public static void main(String[] args) {

		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Employee employee1 = new Employee();
			employee1.setFirstName("Genry");
			employee1.setLastName("Ford");
			em.persist(employee1);

			Employee employee = new Employee();
			employee.setFirstName("John");
			employee.setLastName("Smith");

			Guild guild = new Guild();
			guild.setName("Back-end guild");
			guild.addEmployee(employee);

			em.persist(guild);
		});

		// add new Employee to existing Guild
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Guild guild = em.find(Guild.class, 1L);

			Employee empSamantaFox = new Employee();
			empSamantaFox.setFirstName("Samanta");
			empSamantaFox.setLastName("Fox");
			guild.addEmployee(empSamantaFox);
		});

		// add new Guild to existing Employee
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Employee employee = em.find(Employee.class, 2L);

			Guild guild = new Guild();
			guild.setName("Designers guild");
			// we can create another helper method on employee's side OR use one on Guild's side
//			employee.getGuilds().add(guild);
//			guild.getEmployees().add(employee);
			guild.addEmployee(employee);

//			em.persist(guild); // we have CascadeType on Employee side
		});
	}
}
