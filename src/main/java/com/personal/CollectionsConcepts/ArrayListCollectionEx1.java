package com.personal.CollectionsConcepts;

import java.util.*;

public class ArrayListCollectionEx1 {

    static void main() {
        ArrayList al1 = new ArrayList();
        // ArrayList has default initial capacity of 10 elements

        // I can store heterogeneous / homogeneous data in ArrayList

        /**
         * Features :
         *
         * 1) Dynamic Sizing on the go
         * 2) Homogeneous / Heterogeneous Data
         * 3) ADD ( x ) in rear end is O(1) , ADD (X, Y) is O (N + M)
         * 4) addFirst() --> add(0, e) is O(N)
         * 5) removeFirst() is O(N)
         * 6) NULL values are allowed
         */

        al1.add("INeuron");
        al1.add('B');
        al1.add(4);
        al1.add(25.5);
        al1.add(new Object());



        // ArrayList is a dynamically increasing data structure
        // for every .add(x) they internally copy the elements of this array and copy in a different array
        // with size + 1

        // ArrayList retains the order of insertion , add(x) happens in the rear end and GET / SET are O(1)

        ArrayList al2 = new ArrayList();

        al2.addAll(al1); // this will add the existing arraylist into the new one

        al1.add("Goti lele"); // since we have changed in al1, al2 wont change, (No same reference, addAll creats new reference)

        System.out.println(al2); // Output : [INeuron, B, 4, 25.5, java.lang.Object@27716f4]
        System.out.println(al1); // Output : [INeuron, B, 4, 25.5, java.lang.Object@27716f4, Goti lele]

        ArrayList al3 = new ArrayList();

        al3.addAll(List.of(1)); // List.of creates an immutable List, but addAll only copies the elements of the List provided in arguments and add them in the new arrayList

        al3.add(4); // This is possible because we are not mutating the above List.of , we are mutating al3 which is mutable



        System.out.println(al3); // Output : [1, 4]

        al3.add(1, 5); // all the elements >= 1 will be shifted to right i.e (+1)
        // .add (x, y) is not O(1) time complexity . It is O(N) worst case

        System.out.println(al3); // Output : [1, 5, 4]
    }
}
