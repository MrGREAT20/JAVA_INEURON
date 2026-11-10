package com.personal.CollectionsConcepts;

import java.util.PriorityQueue;

public class PriorityQueueEx1 {

    static void main() {

        /**
         * Features :
         *
         * PriorityQueue extends AbstractQueue which implements Queue interface
         *
         * PriorityQueue internal implementation is min_heap (minimum heap)
         *
         * No front or middle insertion
         *
         * Only trailing addition is fine
         *
         * Order of insertion is not maintained
         *
         * NULL values are not allowed in PriorityQueue
         *
         */

        PriorityQueue pq = new PriorityQueue();

        pq.add(100);
        pq.add(1);
        pq.add(2);
        pq.add(156);
        pq.add(167);


        System.out.println(pq);  // Output : [1, 100, 2, 156, 167]  No insertion order, No sorted order

        /**
         * Reason : PriorityQueue default implementation is of min_heap which means smallest element at top
         *
         * in the output [1, 100, 2, 156, 167] --> 1 is smallest thats why its at start
         */

        pq.add("Pralay");
        System.out.println(pq); // This causes error during run time check below exception

        /***
         * Exception in thread "main" java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String (java.lang.Integer and java.lang.String are in module java.base of loader 'bootstrap')
         * 	at java.base/java.lang.String.compareTo(String.java:144)
         * 	at java.base/java.util.PriorityQueue.siftUpComparable(PriorityQueue.java:647)
         * 	at java.base/java.util.PriorityQueue.siftUp(PriorityQueue.java:639)
         * 	at java.base/java.util.PriorityQueue.offer(PriorityQueue.java:330)
         * 	at java.base/java.util.PriorityQueue.add(PriorityQueue.java:311)
         * 	at com.personal.CollectionsConcepts.PriorityQueueEx1.main(PriorityQueueEx1.java:31)
         */
    }
}
