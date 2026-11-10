package com.personal.FunctionalInterface;

@FunctionalInterface
interface IAlpha {
    void abstractMethod();

}


class AnonymousClassEx {
    public void checkMethod(){
        System.out.println("Check this method");
    }
    // static method cant be overriden by the child class, be with anonymous or anything
}

public class AnonymousInnerClassExampleInterface {

    public static void main() {

        /**
         * This below is the implementation of Anonymous inner classes, no need to write extra class
         * which implements the interface and initiate the object of that class to invoke the function
         * we can directly invoke the function by using anonymous class (Class with no name and it implements or extends others)
         * */
        IAlpha a = new IAlpha() {
            @Override
            public void abstractMethod() {
                System.out.println("Hii there");
            }
        };

        a.abstractMethod();


        AnonymousClassEx ex = new AnonymousClassEx(){
            @Override
            public void checkMethod(){
                System.out.println("Check you out");
                super.checkMethod();
                System.out.println("Check me out");
            }
        };

        ex.checkMethod();
    }
}
