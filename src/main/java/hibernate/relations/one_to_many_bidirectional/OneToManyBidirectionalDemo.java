package hibernate.relations.one_to_many_bidirectional;

import hibernate.EntityManagerUtils;
import hibernate.relations.one_to_many_unidirectional.Person;

public class OneToManyBidirectionalDemo {

	public static void main(String[] args) {
		insertCustomerWithOrders();
		selectFromPersonNplusOneProblem();
	}

	/**
	 * One-To-Many
	 * Bidirectional (Order "knows" about Customer)
	 */
	private static void insertCustomerWithOrders() {
		EntityManagerUtils.doInPersistentContext(em -> {
			Customer customer1 = new Customer();
			customer1.setName("Customer 1");
			em.persist(customer1);

			Customer customer2 = new Customer();
			customer2.setName("Customer 2");
			em.persist(customer2);

			Customer customer3 = new Customer();
			customer3.setName("Customer 3");
			em.persist(customer3);

			Order order1 = new Order();
			order1.setDescription("Order 1");
			order1.setAmount(200.00);
			customer1.addOrder(order1); // use helper method
//			em.persist(order1); // don't need it when @OneToMany(..., cascade = CascadeType.PERSIST) on Customer's side

			Order order2 = new Order();
			order2.setDescription("Order 2");
			order2.setAmount(350.00);
			customer1.addOrder(order2); // use helper method

			Order order3 = new Order();
			order3.setDescription("Order 3");
			order3.setAmount(100.00);
			customer1.addOrder(order3); // use helper method

			Order order4 = new Order();
			order4.setDescription("Order 4");
			order4.setAmount(1000.00);
			customer2.addOrder(order4); // use helper method

			Order order5 = new Order();
			order5.setDescription("Order 5");
			order5.setAmount(750.00);
			customer3.addOrder(order5); // use helper method

			Order order6 = new Order();
			order6.setDescription("Order 6");
			order6.setAmount(600.00);
			customer3.addOrder(order6); // use helper method

			// Customer is in persistent state, so it saves all Orders added to it
		});
	}

	private static void selectFromPersonNplusOneProblem() {
		EntityManagerUtils.doInPersistentContext(em -> {

//			String selectString = "from Customer"; // N+1
			String selectString = "from Customer c left join fetch c.orders"; // fix N+1

			em.createQuery(selectString, Customer.class)
					.getResultList()
					.forEach(customer -> System.out.println(customer.getId() + ": " + customer.getOrders()));
		});
	}
}
