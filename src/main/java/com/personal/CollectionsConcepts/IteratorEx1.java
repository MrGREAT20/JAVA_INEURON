package com.personal.CollectionsConcepts;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class IteratorEx1 {

    static void main() {

        LinkedList lhs1 = new LinkedList();

        lhs1.add(12);
        lhs1.add(123);
        lhs1.add(1234);
        lhs1.add(12345);

        Iterator itr = lhs1.iterator(); // this will create an iterator object pointing towards the collection object

        while(itr.hasNext()){
            System.out.println(itr.next()); // this will return the Object at Next point
        }



        /**
         * Output:
         * 12
         * 123
         * 1234
         * 12345
         */

        Iterator ditr = lhs1.descendingIterator(); // This is available only Deque interface implementations

        /**
         * There are also more features like ListIterators which starts from an index
         * the above is only available for Classes Implementing List interface
         *
         * There is one more descendingIterator
         */
    }
}
