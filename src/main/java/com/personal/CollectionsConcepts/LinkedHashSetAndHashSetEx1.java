package com.personal.CollectionsConcepts;

import java.util.*;

public class LinkedHashSetAndHashSetEx1 {

    static void main() {
        /**
         * Features : HashSet & LinkedHashSet internally follows Hashtable and HashFunction
         * Hashset internally is a HashMap where Keys are the elements stored in set
         * and values are dummy Object (new Object())
         *
         * all the elements which will work as KEY for hashmap goes through hash function and
         * whatever value returned by the hash function,on that index the key will be placed in the bucket
         *
         * HashSet
         *
         * 1) No duplicates
         * 2) Retrieval is O(1) -> GET is based on HashCode
         * 3) Insertion order is not followed , No actual order
         * 4) HashSet belongs to Collections framework but it internally uses HashMap for storing
         *
         *
         * LinkedHashSet
         *
         * 1) Insertion order is followed
         */

        HashSet hs = new HashSet();

        hs.add(100);
        hs.add(50);
        hs.addAll(List.of("ascacs", 158)); // addAll flattens the collection object and than check if each element can be added using the add function
        hs.add("ascacs"); // HashSet does not contains duplicates, so this value will not be added

        System.out.println(hs); // [50, 100, ascacs, 158]


        LinkedHashSet lhs = new LinkedHashSet();

        lhs.add(100);
        lhs.add(50);
        lhs.addAll(List.of("ascacs", 158));
        lhs.add("ascacs");
        lhs.add(null); // null can be added

        System.out.println(lhs); // [100, 50, ascacs, 158]

    }
}
