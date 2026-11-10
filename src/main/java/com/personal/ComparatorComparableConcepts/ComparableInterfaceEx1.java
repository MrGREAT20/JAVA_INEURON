package com.personal.ComparatorComparableConcepts;

import java.util.ArrayList;
import java.util.Collections;

class Student implements Comparable<Student> {
    String name;
    int age;

    Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [age= " + this.age + " name= " + this.name + " ]";
    }

    @Override
    public int compareTo(Student o) {
        return this.age - o.age;



        /**
         *
         * a.compareTo(b) answers: "where does a go relative to b?"
         *
         * compareTo will default to ascending order only
         *
         * Return value	        Meaning	            Effect in ascending sort
         *
         * negative (< 0)	a (currentObj) is "smaller" than b (comparedAgainst obj)  a comes before b
         * zero (0)	        a equals b in order	       same position
         * positive (> 0)	a is "larger" than b	   a comes after b
         */

        // return o.age - this.age; (this will cause descending sort)

    }
}

public class ComparableInterfaceEx1 {

    static void main() {

        ArrayList<Student> nums = new ArrayList<Student>();

        nums.add(new Student("Pralay", 26));
        nums.add(new Student("Payal", 29));
        nums.add(new Student("Raj", 28));
        nums.add(new Student("Abhijeet", 27));

        System.out.println(nums);

        // What if we want to sort this ArrayList

        Collections.sort(nums);

        /**
         * This above code does this
         *
         * Sorts the specified list into ascending order, according to the natural ordering of its elements.
         * All elements in the list must implement the Comparable interface. Furthermore, all elements in the list must be mutually comparable
         * (that is, e1.compareTo(e2) must not throw a ClassCastException for any elements e1 and e2 in the list).
         *
         * But Student does not extends the Comparable interface
         */

        System.out.println(nums);

        /**
         * Comparable only works if we have the Source class (i.e Student) in our file
         */
    }

}
