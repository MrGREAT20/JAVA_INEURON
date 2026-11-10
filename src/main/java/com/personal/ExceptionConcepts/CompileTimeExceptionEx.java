package com.personal.ExceptionConcepts;

class Example1 {
    int asc() throws Exception {
        int x = 100;
        int y = 0;
        int temp = x / y ;
        return temp;
    }

    /**
     * We know that this method throws Exception, and the type is Exception class
     * so whenever we invoke this function from anywhere, we must wrap the function in try catch
     *
     *
     * Lets say if i write " throws ArithmeticException" than this is not compile time exception (CheckedException)
     * because ArithmeticException is a RuntimeException (uncheckedException), so compiler does not
     * enforce to handle RuntimeException,
     */
}

public class CompileTimeExceptionEx {

    static void main() throws Exception {
        Example1 ex1 = new Example1();
        int temp = ex1.asc(); // this causes compiler error because .asc() throws exception, and we have not wrapped in try / catch

        /*
        either wrap .asc() in try catch or let the function main( ) use throws Exception signature

        like this below

        static void main() throws Exception {
        Example1 ex1 = new Example1();
        int temp = ex1.asc();

         */

        /**
         * throws should be used by only those methods where the caller's (the one who is invoking the method)
         * input matters and that caller is ready to tackle the problem / exception passed by the method
         *
         * It is not the methods duty to solve each and every exception based on different types of input.
         * methods duty should be complete the logic based by the parameters provided and throw potential
         * exceptions which can occur during execution, It should be callers duty to gracefully handle that
         *
         *
         * Compile time exception (Checked Exception) -> Your function throws any Exception (which is not derived
         * class of RuntimeException) and if you dont catch that exception than Compiler wont let you compile because we have not
         * handled the exception which the method has thrown
         */
    }

}
