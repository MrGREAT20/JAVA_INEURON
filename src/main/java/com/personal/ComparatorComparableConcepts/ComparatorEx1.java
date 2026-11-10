package com.personal.ComparatorComparableConcepts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class ComparatorImpl implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o1.age - o2.age;

        /**
         *
         *
         *
         * compareTo will default to ascending order only
         *
         * Return value	        Meaning	            Effect in ascending sort
         *
         * negative (< 0)	a (currentObj) is "smaller" than b (comparedAgainst obj)  a comes before b
         * zero (0)	        a equals b in order	       same position
         * positive (> 0)	a is "larger" than b	   a comes after b
         */

    }
}

public class ComparatorEx1 {

    static void main() {

        ArrayList<Student> nums = new ArrayList<Student>();

        nums.add(new Student("Pralay", 26));
        nums.add(new Student("Payal", 29));
        nums.add(new Student("Raj", 28));
        nums.add(new Student("Abhijeet", 27));

        Comparator<Student> comp = new ComparatorImpl();

        Collections.sort(nums, comp);

        Collections.reverse(nums); // This will reverse the nums arrayList

        System.out.println(nums); // Output : [Student [age= 26 name= Pralay ], Student [age= 27 name= Abhijeet ], Student [age= 28 name= Raj ], Student [age= 29 name= Payal ]]


        nums.sort((l, r) -> l.age - r.age);

        // this above is also fine because Comparator is a FunctionalInterface


    }
}
