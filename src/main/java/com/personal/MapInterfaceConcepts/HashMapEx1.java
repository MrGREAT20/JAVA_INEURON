package com.personal.MapInterfaceConcepts;

import java.util.HashMap;
import java.util.Set;

public class HashMapEx1 {

    static void main() {

        /**
         * Features
         *
         * Map is NOT part of Collection / Iterable interface hierarchy
         *
         *  Map is an interface consisting of one more interface named Entry (I)
         *
         *  Entry interface is nothing but a row in a Map , a row consists of both key and value
         *  so Entry is a KEY-VALUE pair
         *
         *  if you do javap java.util.Map.Entry you will see de-compiled version of Map interface and
         *  see Entry with a dollar sign (which means its an inner class / interface )
         *
         *
         *  How to iterate on Map ?
         *
         *  Using built in functions
         *
         *  1) Set keySet() --> Converts keys's of Map to set and  returns Set interface
         *  2) Collection values() --> Converts the values of Map to Collection Object and  returns Collection interface
         *  3) Set entrySet() --> Converts whole entry of map into set and returns Set of Entry Object
         *
         *
         *  Insertion order is not maintained in HashMap
         */
        HashMap m1 = new HashMap();

        m1.put(10, "Sachin");
        m1.put(18, "Kohli");
        m1.put(19, "dravid");

        System.out.println(m1); // m1.toString will be called, toString method is overridden by the classes
        // Output for above is {18=Kohli, 19=dravid, 10=Sachin}


        Set s = m1.keySet(); // method gets executed and the Set object is created at runtime
        // so .keySet will always return interface and not an implementation class of Set

        System.out.println(s);

        HashMap hm = new HashMap(); //Default capacity is 16 of hashmap and load factor is 0.75
        // which means upon increase of data by 75%, automatically size of Hashmap will be doubled

        hm.put(null, 0);
        hm.put(null, null);
        hm.put(null, "ABC");

        // this above is fine, we can add NULL keys but value will be replaced everytime




    }

}
