package com.personal.ExceptionConcepts;

class Example {
    int check(){
        int x = 100;
        int y = 0;

        try{
            int temp = (x/y);
            System.out.println("Result --> " + temp);
            return 2;
        } catch (Exception e) {
            return 1;
        }
        finally {  // finally will always override returns from try and catch
            System.out.println("HEHEHEHEH");
        }
    }
}

public class ExceptionReturnExample {
    static void main() {
        Example ex = new Example();
        System.out.println( ex.check() );
    }

    /***
     *
     * Input :
     * x = 100
     * y = 10
     *
     * Output :
     *
     *  Result --> 10  --> system out from try
     *  HEHEHEHEH --> system out from finally
     *  2 --> return from try
     *
     *
     *
     *  Input :
     *  x = 100
     *  y = 0
     *
     *  Output:
     *
     *  HEHEHEHEHE --> system out from finally
     *  1 --> return from catch
     */
}
