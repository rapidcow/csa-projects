package example;

public class Main {
    public static void main(String[] args) {
        runEmployeeConstructor();
        runEmployeeBuilder();
    }

    /*
     * The approach mentioned in class.
     */
    private static void runEmployeeConstructor() {
        System.out.println("running Employee constructors");

        Employee e1, e2, e3;
        e1 = new Employee(21000);
        e2 = new Employee(21000, "Ben");
        e3 = new Employee(21000, "Ben", 2500.0);

        e1.display();
        e2.display();
        e3.display();

        /* Case where the name is left out */
        Employee e4 = new Employee(21000, null, 2500.0);
        e4.display();
    }

    /*
     * The builder pattern approach.
     */
    private static void runEmployeeBuilder() {
        System.out.println("running EmployeeBuilder");

        /*
         * One cool thing you can do with a builder (as mentioned in
         * Exploring Joshua Bloch's Builder design pattern in Java) is that
         * you can reuse the same builder when the objects you're building
         * just differ slightly from one another.
         */
        Employee e1, e2, e3;
        EmployeeBuilder builder = new EmployeeBuilder(21000);
        e1 = builder.build();
        e2 = builder.setName("Ben").build();
        e3 = builder.setSalary(2500.0).build();

        e1.display();
        e2.display();
        e3.display();

        /* Case where the name is left out --- now elegantly handled! */
        Employee e4 = new EmployeeBuilder(21000).setSalary(2500.0).build();
        e4.display();

        /* If we don't reuse the builder, here's what we would write: */
        e1 = new EmployeeBuilder(21000).build();
        e2 = new EmployeeBuilder(21000).setName("Ben").build();
        e3 = new EmployeeBuilder(21000).setName("Ben")
                                       .setSalary(2500.0).build();

        e1.display();
        e2.display();
        e3.display();
    }
}
