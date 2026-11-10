package com.personal.ExceptionConcepts;

public class NestedTryCatchEx {

    static void main() {
        try {
            System.out.println("Inside Outer try");
            try {

                System.out.println("Inside inner try");
                System.out.println(10/0);
            } catch (ArithmeticException e) {
                System.out.println("Inside inner catch");
            } finally {

            }

            System.out.println("Outside inner try catch");

        } catch (Exception e) {

            System.out.println("Inside outer catch");

        } finally {

            System.out.println("Inside outer finally");

        }
    }

    /**
     *
     * If inner catch does not match the Exception thrown, the exception is checked by outside Catch and so on
     * once nested try catch are done, then JVM checks if there are any catch in the caller methods be it in any class
     *
     * we can add try catch finally inside try catch finally
     *
     *
     *
     * Only below combinations are fine when implementing exception handling
     *
     * try - catch  --> fine  and also multiple catch are fine but only with different exceptions
     *
     * try - catch - finally  --> fine
     *
     * try - finally  --> fine
     *
     */
}
