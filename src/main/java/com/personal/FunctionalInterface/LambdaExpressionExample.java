package com.personal.FunctionalInterface;

interface IComputer {
    void show(int n);
}

public class LambdaExpressionExample {

    // we can remove the new IComputer() { @override public void show() { } }
    // and directly use the " -> " keyword and write the implementation of the function

    static void main() {
        IComputer com = (n) -> {
            System.out.println("Hiii " + n);
        };

        // you can also remove the curly brackets

        IComputer com1 = ( n) -> System.out.println("Hii there " + n);

        com.show(12); // Hiii 12
        com1.show(13); // Hii there 13
    }

}
