package com.personal.CollectionsConcepts;

import java.util.TreeSet;

public class TreeSetEx1 {

    static void main() {
        /**
         * Features
         *
         * TreeSet : internally implements Binary Search Tree which implements Red - Black Algorithm
         * which is nothing but self-balancing binary tree
         *
         * TreeSet implements SortedSet Interface which implements Set interface
         *
         * Treeset gives sorted ascending order
         *
         * No duplicates
         *
         * No indexed based searching
         *
         * null values not allowed (will get NPE)
         *
         *
         */

        TreeSet ts = new TreeSet();

        ts.add(100);
        ts.add(50);
        ts.add(150);
        ts.add(25);
        ts.add(75);
        ts.add(125);
        ts.add(175);
        ts.add(100);
        ts.add("ascacs"); // This will cause error, because add checks for duplicates and this will not be compared
        ts.add(null); // This will also cause error, because NULL values can be added in treeset


        System.out.println(ts);  //Output : [25, 50, 75, 100, 125, 150, 175] (no 100 duplicate)
    }
}
