package com.personal.ExceptionConcepts;

class ThrowableEx1 {
    void test() throws Throwable {
        int x = 100;
        int y = 0;
        if(y == 0){
            throw new Throwable("Example Exception");
        }
    }
}
public class ThrowableEx{

    static void main() throws Throwable {
        ThrowableEx1 ex1 = new ThrowableEx1();

        ex1.test();  // Example of checked Exception
    }
}
