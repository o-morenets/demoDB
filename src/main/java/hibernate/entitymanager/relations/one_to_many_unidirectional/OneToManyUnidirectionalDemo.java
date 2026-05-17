package hibernate.entitymanager.relations.one_to_many_unidirectional;

import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

public class OneToManyUnidirectionalDemo {

    public static void main(String[] args) {

        System.out.println("Saving Customers with Orders");
        persistCustomersWithOrders();

        System.out.println("1 + N problem example");
        selectFromPersonOnePlusNProblem();
    }

    /**
     * One-To-Many
     * Unidirectional (Order doesn't "know" about Customer)
     */
    private static void persistCustomersWithOrders() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {

            // Create Customers

            Customer customer1 = new Customer();
            customer1.setName("Customer 1");
            em.persist(customer1);

            Customer customer2 = new Customer();
            customer2.setName("Customer 2");
            em.persist(customer2);

            Customer customer3 = new Customer();
            customer3.setName("Customer 3");
            em.persist(customer3);


            // Create Orders

            Order order1 = new Order();
            order1.setDescription("Order 1");
            order1.setAmount(200.00);
            customer1.getOrders().add(order1);
//			em.persist(order1); // don't need it when @OneToMany(..., cascade = CascadeType.ALL) on Customer's side

            Order order2 = new Order();
            order2.setDescription("Order 2");
            order2.setAmount(350.00);
//            customer1.getOrders().add(order2); // when no order is added, it will NOT be saved to DB

            Order order3 = new Order();
            order3.setDescription("Order 3");
            order3.setAmount(100.00);
            customer1.getOrders().add(order3);

            Order order4 = new Order();
            order4.setDescription("Order 4");
            order4.setAmount(1000.00);
            customer2.getOrders().add(order4);

            Order order5 = new Order();
            order5.setDescription("Order 5");
            order5.setAmount(750.00);
            customer3.getOrders().add(order5);

            Order order6 = new Order();
            order6.setDescription("Order 6");
            order6.setAmount(600.00);
            customer3.getOrders().add(order6);

            // Customers are in persistent state, so all related Orders also will be saved

            // IMPORTANT NOTE for unidirectional relationships
            // In Unidirectional relationship, firstly child entity will be inserted with FK == null
            // as child doesn't know about its parent
            // and then, child entity will be updated with FK:
            // [Hibernate] insert into order_details (amount,description) values (?,?)
            // [Hibernate] update order_details set customer_id=? where id=?
        });
    }

    private static void selectFromPersonOnePlusNProblem() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {

//			String selectString = "from Customer"; // 1 + N
            String selectString = "select distinct c from Customer c left join fetch c.orders"; // fix 1 + N

            em.createQuery(selectString, Customer.class)
                    .getResultList()
                    .forEach(customer -> System.out.println("Customer #" + customer.getId() + ": " + customer.getOrders()));
        });
    }
}
