package com.personal.StringConcepts;

public class StringBuilderConcepts {

    // StringBuilder = Mutable but not thread safe, cant be used in scenarios where multiple threads are expected to mutate the String and might cause race conditions

        static void main() {
            StringBuilder sb1 = new StringBuilder("hello");
            StringBuilder sb2 = new StringBuilder("hello");

            System.out.println(sb1.equals(sb2)); // False  because StringBuilder ka equals method only check the references

            /*
                2. sb1.toString() == sb2.toString() -> False
To understand this one, we have to look at what the .toString() method actually does under the hood.
When you call .toString() on a StringBuilder, it doesn't look at the String Pool. Instead, it explicitly executes a new String(...) command.
sb1.toString() creates a brand-new String object on the standard Heap memory (Address A).
sb2.toString() creates another brand-new String object on the standard Heap memory (Address B).
Because the == operator only checks memory addresses, and you just forced Java to build two completely new, separate objects on the Heap, Address A does not equal Address B.
            * **/
            System.out.println(sb1.toString() == sb2.toString()); // False



            System.out.println(sb1.toString() == sb2.toString().intern()); // False

            System.out.println(sb1.toString().intern() == sb2.toString().intern()); // True
            // because .intern() returns string but == checks references, the reference of the string returned by .intern() points to the memory address of the intern pool

        }
}
