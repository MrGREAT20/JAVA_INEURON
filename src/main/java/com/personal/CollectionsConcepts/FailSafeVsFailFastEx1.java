package com.personal.CollectionsConcepts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailSafeVsFailFastEx1 {

    static void main() {

        // ------------ FAIL FAST EXAMPLE ---------------

        ArrayList al = new ArrayList();

        al.add(10);
        al.add(20);
        al.add(30);
        al.add(40);
        al.add(50);


        for (int i = 0; i < al.size(); i++) {

            System.out.println(al.get(i));

           // al.add(99); // Modifying the Data while accessing the same data will cause Infinite Loop
        }
            /**
             * Structural Modification means attempting to modify the data while accessing it
             * to avoid Structural Modification we must Fail Fast, because if not than program can go on
             * infinite loop
             *
             *
             * To solve this, we should use Iterator
             */

            Iterator itr = al.iterator();

            while(itr.hasNext()) {
                System.out.println(itr.next());
                al.add(99); // We will get a compiler error of ConcurrentModificationException
                // This means we are FAILING FAST
            }




            // --------- FAIL SAFE EXAMPLE -----------------

            // Use of java.util.concurrent.* package Collections allow No exception thrown for
            // ConcurrentModificationException , which means they are failing safe and not throwing exception

        CopyOnWriteArrayList cal = new CopyOnWriteArrayList();
            cal.add(100);
            cal.add(200);
            cal.add(300);

            Iterator itr1 = cal.iterator();

            while(itr1.hasNext()){
                System.out.println(itr1.next());
                cal.add(100); // 100 is not in the output because Concurrent Package List dont throw exception for ConcurrnetModification
            }

        for (int i = 0; i < cal.size(); i++) {

            System.out.println(cal.get(i));

            cal.add(99); // Modifying the Data while accessing the same data will cause Infinite Loop , we have to solve this using above Iterator only
        }

    }

}
