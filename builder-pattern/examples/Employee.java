package example;

public class Employee {
    /* instance variables */
    private int _id;
    private String _name;
    private double _salary;

    /* first constructor */
    public Employee(int id) {
        _id = id;
    }

    /* second constructor */
    public Employee(int id, String name) {
        _id = id;
        _name = name;
    }

    /* third constructor */
    public Employee(int id, String name, double salary) {
        _id = id;
        _name = name;
        _salary = salary;
    }

    /* getter */
    public void display() {
        System.out.println("id: " + _id);
        System.out.println("name: " + _name);
        System.out.println("salary: " + _salary);
    }
}
