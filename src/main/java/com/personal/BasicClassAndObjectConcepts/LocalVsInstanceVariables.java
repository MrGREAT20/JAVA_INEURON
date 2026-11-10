package com.personal.BasicClassAndObjectConcepts;

class Student {
    // Instance variables: live inside the object on the Heap.
    // If we don't assign them, the JVM gives them default values.
    int rollNo;         // 0
    double marks;       // 0.0
    boolean passed;     // false
    char grade;         // '\u0000' (null character)
    String name;        // null
    int[] scores;       // null

    // Static (class) variables also get default values.
    static int count;   // 0

    // A final instance variable gets NO default value.
    // It must be assigned here or in every constructor, otherwise:
    // error: variable id not initialized in the default constructor
    final int id = 101;

    void printDefaults() {
        System.out.println("rollNo  = " + rollNo);
        System.out.println("marks   = " + marks);
        System.out.println("passed  = " + passed);
        System.out.println("grade   = [" + grade + "]  (int value: " + (int) grade + ")");
        System.out.println("name    = " + name);
        System.out.println("scores  = " + scores);
        System.out.println("count   = " + count);
        System.out.println("id      = " + id);
    }
}

public class LocalVsInstanceVariables {

    public static void main(String[] args) {
        // Instance variables -> default values are used, no error.
        Student s = new Student();
        s.printDefaults();

        // Local variables live on the Stack and get NO default value.
        int x;
        // System.out.println(x);
        // compilation error: variable x might not have been initialized

        // Declaring without using is fine - the error only appears on READ.
        x = 10;
        System.out.println("x       = " + x);

        // The compiler checks every path (definite assignment).
        int y;
        if (args.length > 0) {
            y = 5;
        }
        // System.out.println(y);
        // compilation error: variable y might not have been initialized
        // (args.length could be 0, so y is not assigned on every path)

        int z;
        if (args.length > 0) {
            z = 5;
        } else {
            z = 0;
        }
        System.out.println("z       = " + z); // OK: assigned on every path

        // Local reference variables behave the same way.
        String str;
        // System.out.println(str.length());
        // compilation error: variable str might not have been initialized
        // (NOT a NullPointerException - the code never even compiles)

        // Accessing a null instance variable compiles fine but fails at runtime.
        // System.out.println(s.name.length());
        // runtime error: java.lang.NullPointerException
    }

    /**
     *
     *Check the obsidian notes or the md file
     */

}
