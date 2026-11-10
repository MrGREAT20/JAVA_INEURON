package com.personal.MapInterfaceConcepts;

import java.util.HashMap;
import java.util.IdentityHashMap;

public class IdentityHashMapAndHashMapEx2 {

    static void main() {
        HashMap h2 = new HashMap();

        Integer key1 = new Integer(10);
        Integer key2 = new Integer(10);

        h2.put(key1, "VALUE1");
        h2.put(key2, "VALUE2");

        System.out.println(h2); // Output : {10=VALUE2}

        /**
         * Reason :
         *
         * In hashmap JVM calls equals(Object obj) to identify whether each Key is different
         *
         * and equals of wrapper classes checks the primitive raw values of those objects
         *
         * To fix this, we have to use IdentityHashMap
         */

        IdentityHashMap ihm = new IdentityHashMap();

        ihm.put(key1, "value1");
        ihm.put(key2, "value2");

        System.out.println(ihm); // Output : {10=value2, 10=value1}

        /**
         *  if it is IdentityHashMap , JVM calls == operator to identify whether the keys are duplicated or not
         */

        int nkey1 = 10;
        int nkey2 = 10;

        ihm.put(nkey1, "some");
        ihm.put(nkey2, "some-thing");

        System.out.println(ihm); // Output : {10=some-thing, 10=value2, 10=value1} "Some" wala nhi aaya because
        // for primitive types == only checks for raw values, and nkey1 and nkey2 are same by ==


    }
}
