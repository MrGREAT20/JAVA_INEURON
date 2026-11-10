package com.personal.ExceptionConcepts;

import java.util.Scanner;

public class ExceptionExample1 {
    static void main() {
        Scanner scn = new Scanner(System.in);
        int a = scn.nextInt();
        int b = scn.nextInt();
        System.out.println("Pakda pakdi start..");
        try{
            int x = a/(b+1);
            System.out.println("Feku error ki nhi hahahaha....2");
        } catch (RuntimeException e) {
            System.out.println("Pakda gaya");

            // agar catch nhi karte toh mere Stack me pop karta jaata for the previous method which called this
            // and check ki udhar catch hai kya, aise aise karke Main method takk aajata and than Main method bhi Killed but for this running thread
            // for Springboot application each request is a different thread so agar catch nhi kiya toh bhi Application bandh nhi hota bass voh thread ka execution kill ho jaata hai
        } finally {
            System.out.println("Finally Pakda");
        }

        try{
            int y = a/b;
            System.out.println("Cant catch me " + y); // No catch statement so directly Main thread hi killed
        } finally {
            System.out.println("RulaDiya na bichare ko, bandh hogaya");
        }

    }
}
