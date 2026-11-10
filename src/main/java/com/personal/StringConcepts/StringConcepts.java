package com.personal.StringConcepts;

public class StringConcepts {

    public void main(){

        String pt1 = "Hello";
        String pt2 = "Hello";
        System.out.println(pt1 == pt2); // TRUE

        // Compile Time String constants

        String p = "Hello";
        String q = "Hel" + "lo";
        System.out.println(p == q); // True

        String r = "Hello";
        final String part1 = "Hel"; // final + initialized with a constant → compile-time constant
        String s = part1 + "lo"; // folded at compile time → "hello"
        System.out.println(r == s); // True


        // =====================================


        // Run Time String constants

        String a = "Hello";
        String part2 = "Hel";
        String b = part2 + "lo"; // Java doesnt know the value of part2, it creates a new Object in Heap at runtime
        // so b is pointing towards memory reference of the new string "Hello"

        System.out.println(a == b); // False

        System.out.println(a == b.intern()); // True
        // because we know == compares the memory reference and a is referencing the address of "Hello" 's string pool address
        // b.intern() also points to the same string pool memory address reference thats why == becomes true

        System.out.println(a.equals(b)); // True

        // =================

        // contentEquals

        System.out.println("Hello".equals(pt1)); // True

        StringBuilder sb1 = new StringBuilder("Hello");
        StringBuffer sb2 = new StringBuffer("Hello");
        System.out.println("Hello".equals(sb1)); // False, because string 's  .equals() method checks instanceOf String
        System.out.println("Hello".equals(sb2)); // False same as above
    }
}
