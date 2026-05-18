package hibernate.entitymanager.relations.many_to_many;


import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

public class ManyToManyDemo {

	public static void main(String[] args) {

		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Employee employee1 = new Employee("Martin", "Fowler");
			em.persist(employee1);

			Employee employee2 = new Employee("John", "Smith");

			Guild guild = new Guild("Back-end guild");
			guild.addEmployee(employee2); // helper method

			em.persist(guild);
		});

		// add new Employee to existing Guild
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Guild guild = em.find(Guild.class, 1L);

			Employee empSamantaFox = new Employee("Samanta", "Fox");
			guild.addEmployee(empSamantaFox); // helper method
		});

		// add new Guild to existing Employee
		EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {
			Employee employee = em.find(Employee.class, 2L);

			Guild guild = new Guild("Designers guild");

			// we can create another helper method on employee's side OR use one on Guild's side
//			employee.getGuilds().add(guild);
//			guild.getEmployees().add(employee);

            // OR
			guild.addEmployee(employee); // helper method

//			em.persist(guild); // not needed as we have CascadeType on Employee side
		});
	}
}
