package com.personal.BasicClassAndObjectConcepts;

class A {
    public String func() {
        return "Nothing else";
    }
}

class B extends A {
    public String func() {
        return "Something Else";
    }
}

public class SameMethodMultiClass {

    public static void main(String[] args) {
        A a = new B();

        // B b = new A(); // compilation error

        System.out.println(a.func()); // result : "Something else"
    }

    /**
     *
     *Check the obsidian notes or the md file
     */

}
