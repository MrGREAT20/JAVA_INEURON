package com.personal.FunctionalInterface;


public interface IInterfaceConcepts1 {

    public static final String variabl1 = "HELLLO";
    // only constants can be declared as member variables


    // we can have some base implementation of some methods (this was introduced to tackle the streams and forEach problem for Collection Interface)
    default void generateRecipe(){
        System.out.println("acacs");
    }

    static void testing(){
        return ;
    }

    void bark();

    void meow();
}
