package com.personal.CollectionsConcepts;

import java.util.ArrayDeque;
import java.util.List;

public class ArrayDequeEx1 {

    static void main() {

        ArrayDeque adq1 = new ArrayDeque();

        /**
         * ArrayDeque implements Deque interface
         *
         * Features :
         * 1) Random operations not allowed apart from front and back
         * 2) index based accessing is not allowed
         * 3) any form of data can be stored
         * 4) order of insertion is maintained
         * 5) NULL values are not allowed in ArrayDeque
         */

        adq1.add("Pralay");
        adq1.add(10);
        adq1.add("Mumbai");


        adq1.addAll(List.of(1, 2));  // Adds all of the elements in the specified collection at the end of this deque

        System.out.println(adq1);  // Output : [Pralay, 10, Mumbai, 1, 2]



    }
}
