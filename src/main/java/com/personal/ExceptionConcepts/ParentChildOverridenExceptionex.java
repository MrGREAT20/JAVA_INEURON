package com.personal.ExceptionConcepts;

class Demo1 {
    void disp() throws Exception {
        System.out.println("Inside demo1");
    }
}

class Demo2 extends Demo1 {

    /**
     * Child class cannot throw Checked Exceptions (Compile time exceptions) if Parent does not throws any checked exception
     * Child class can throw UnChecked Exceptions (Runtime exceptions) if Parent does not throws any checked exception
     * @throws Exception
     */

    @Override
    void disp()  {
        System.out.println("Inside Demo2");
    }
}

public class ParentChildOverridenExceptionex {

    static void main() {

        Demo1 dm1 = new Demo1();
        Demo2 dm2 = new Demo2();
        Demo1 dm3 = new Demo2();

        // dm1.disp(); // Compilation error , disp throws checked exception and we have not handled

        dm2.disp(); // No compilation error , because Demo2's disp method overrides Demo1's disp

        // dm3.disp(); // Compilation error, disp of Demo1 throws checked exception and we have not handled

    }





}
