package example;

class EmployeeBuilder {
    private int _id;
    private String _name;
    private double _salary;

    public EmployeeBuilder(int id) {
        _id = id;
    }

    public EmployeeBuilder setName(String name) {
        _name = name;
        return this;
    }

    public EmployeeBuilder setSalary(double salary) {
        _salary = salary;
        return this;
    }

    public Employee build() {
        return new Employee(_id, _name, _salary);
    }
}
