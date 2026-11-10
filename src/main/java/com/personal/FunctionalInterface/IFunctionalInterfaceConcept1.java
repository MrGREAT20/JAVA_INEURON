package com.personal.FunctionalInterface;

@FunctionalInterface
public interface IFunctionalInterfaceConcept1 {

    /**
     * Functional interface should only have 1 ABSTRACT METHOD
     * functional interface can have constant variables and other default methods
     *
     *
     * Functional interfaces can be implemented by the below patterns
     * -> Class,
     * -> Anonymous inner class,
     * -> Lambda expressions (syntactic sugar for anonymous inner classes)
     */

    void abstractMethod();
}

class ChildClass implements IFunctionalInterfaceConcept1 {

    @Override
    public void abstractMethod() {
        System.out.println("Hii there");
    }
}

class StartHere{
    public static void main() {
        IFunctionalInterfaceConcept1 test1 = new ChildClass();
        test1.abstractMethod();
    }
}