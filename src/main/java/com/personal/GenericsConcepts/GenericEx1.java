package com.personal.GenericsConcepts;

import java.util.ArrayList;

public class GenericEx1 {

    static void main() {

        ArrayList<String> al = new ArrayList();

        al.add("hiih");
        // al.add(10); // COMPILE time error because now we have added type safety i,e only Strings are allowed

    }
}
