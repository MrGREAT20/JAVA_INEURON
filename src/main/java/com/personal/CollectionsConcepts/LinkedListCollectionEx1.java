package com.personal.CollectionsConcepts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListCollectionEx1 {

    static void main() {

        // LinkedList ==> (Java internally implements DoublyLinkedList for LinkedList implementation
        // and implements List and Deque (Double ended Queue) interface)

        // LinkedList (DoublyLinkedList) is non-continous (in memory) and can store any form of data

        /**
         *  Features of LinkedList :
         *
         *  1) We can store any form of data
         *  2) We can add one collection to another (similar to ArrayList)
         *  3) It can make use of Dispersed Memory location (non continous)
         *  4) Order of Insertion is maintained
         *  5) Insertion and Deletion operation at any give position (including front and rear end)
         *  6) NULL values are allowed
         */

        LinkedList ll1 = new LinkedList();

        ll1.add(10);
        ll1.add("Pralay");
        ll1.add('M');
        ll1.add("Mumbai");

        System.out.println(ll1); // Output : [10, Pralay, M, Mumbai]


        ll1.add(2, 70);

        System.out.println(ll1); // Output : [10, Pralay, 70, M, Mumbai]

        LinkedList ll2 = new LinkedList();

        ll2.add("hehehe");
        ll2.add("ioioio");
        ll2.add("pppppp");

        ll1.addAll(1, ll2);

        System.out.println(ll1); // Output : [10, hehehe, ioioio, pppppp, Pralay, 70, M, Mumbai]

        ArrayList al1 = new ArrayList();

        al1.add("Checking hetero");
        al1.add("Why not homo");

        ll1.addAll(3, al1); // addAll converts the al1 elements to a LinkedList implementation i.e (Node, data)
        // TC for above O(N + M) -> N : current LinkedList size , M : incoming Element E size

        System.out.println(ll1); // Output : [10, hehehe, ioioio, Checking hetero, Why not homo, pppppp, Pralay, 70, M, Mumbai]

        System.out.println( ll1.get(6) ); // Output : Pralay  (TC : O(N) .get(indx) internally run a for loop to go till indx )

    }
}
