package com.personal.StreamConcepts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Employee {
    int id;
    String name;
    String gender;
    String city;
    Double salary;

    public Employee(int id, String name, String gender, String city, Double Salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.salary = Salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String toString(){
        String salary = this.salary == null ? "NULL" : this.salary.toString();
        return "[ Employee name= " + this.name + " gender= " + this.gender + " salary= " + salary + " ]";
    }
}

public class StreamConceptEx1 {

    static void main() {

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "A", "Male", "Mumbai", 102030.00));
        employees.add(new Employee(2, "B", "Female", "Mumbai", 104030.00));
        employees.add(new Employee(3, "C", "Female", "Delhi", 100030.00));
        employees.add(new Employee(4, "F", "Female", "Delhi", 105030.00));
        employees.add(new Employee(5, "E", "Bigender", "Delhi", 202030.00));
        employees.add(new Employee(6, "D", "Female", "Delhi", 105030.00));
        employees.add(new Employee(7, "G", "Female", "Mumbai", null));
        System.out.println(employees);

        List<Employee> result =  employees.stream().filter((emp) -> emp.gender.equalsIgnoreCase("FEMALE")).collect(Collectors.toList());

        Comparator<Employee> comparatorFunc = new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                    if (e1.salary == null && e2.salary == null) return 0; // if == 0 than a(e1) and b (e2) will be not swapped
                    if (e1.salary == null) return 1;    // if > 0 a(e1) will come after b(e2)
                    if (e2.salary == null) return -1; // if <0 a(e1) will come before b (e2)
                    return Double.compare(e2.salary, e1.salary);   // e2 vs e1 → descending , // if we did (e1.salary , e2.salary) , it would be in ascending order
                }
        };

        result.sort(comparatorFunc);

        // This below is one clean approach
//        result.sort(Comparator.comparing(
//                Employee::getSalary,
//                Comparator.nullsLast(Comparator.reverseOrder())));


        // This below is using stream API

        // employees.stream().filter().sorted(comparatorFunc) and below is more cleaner way

        /**
         * List<Employee> result = employees.stream()
         *     .filter(emp -> emp.getGender().equalsIgnoreCase("Female"))
         *     .sorted(Comparator.comparing(
         *                 Employee::getSalary,
         *                 Comparator.nullsLast(Comparator.reverseOrder())))   // descending, nulls last
         *     .collect(Collectors.toList());
         *
         */

        System.out.println(result);

    }


}
