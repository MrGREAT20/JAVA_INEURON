package com.personal.StringConcepts;

public class StringBufferConcepts {

    // StringBuffer = mutable but thread safe, thats why slow because CPU intensive work of Locking and releasing

    static void main() {
        StringBuffer sb1 = new StringBuffer("Hello");
        StringBuffer sb2 = new StringBuffer("Hello");

        System.out.println(sb1 == sb2); // False
        System.out.println(sb1.toString() == sb2.toString()); // False // Same defnition of toString as in StringBuilder
        System.out.println(sb1.toString().intern() == sb2.toString().intern()); // True

        StringBuilder sb3 = new StringBuilder("Hello");
        // System.out.println(sb3 == sb1); // Compile error
    }
}
