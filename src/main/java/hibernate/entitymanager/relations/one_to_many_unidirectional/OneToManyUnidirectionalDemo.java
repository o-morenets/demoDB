package hibernate.entitymanager.relations.one_to_many_unidirectional;

import hibernate.entitymanager.relations.EntityManagerUtilsRelations;

public class OneToManyUnidirectionalDemo {

    public static void main(String[] args) {

        System.out.println("Saving Customers with Orders");
        persistCustomersWithOrders();
    }

    /**
     * One-To-Many
     * Unidirectional (Order doesn't "know" about Customer)
     */
    private static void persistCustomersWithOrders() {
        EntityManagerUtilsRelations.doInEntityManagerRelations(em -> {

            // Create Customers

            Customer customer1 = new Customer("Customer 1");
            em.persist(customer1);

            Customer customer2 = new Customer("Customer 2");
            em.persist(customer2);

            Customer customer3 = new Customer("Customer 3");
            em.persist(customer3);


            // Create Orders

            Order order1 = new Order("Order 1", 200.00);
            customer1.getOrders().add(order1);
//			em.persist(order1); // don't need it when @OneToMany(..., cascade = CascadeType.ALL) on Customer's side

            Order order2 = new Order("Order 2", 350.00);
//            customer1.getOrders().add(order2); // when no order is added, it will NOT be saved to DB

            Order order3 = new Order("Order 3", 100.00);
            customer1.getOrders().add(order3);

            Order order4 = new Order("Order 4", 1000.00);
            customer2.getOrders().add(order4);

            Order order5 = new Order("Order 5", 750.00);
            customer3.getOrders().add(order5);

            Order order6 = new Order("Order 6", 600.00);
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
}
