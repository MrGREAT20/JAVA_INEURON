package com.personal.Multithreading;

import java.util.ArrayList;
import java.util.List;

class Work7 extends Thread implements Runnable{

    /**
     * This method is run by the thread when it executes.
     *
     */
    public void run()  {

        System.out.println("Banking" + " Started Successfully..........");

        for(int i = 0; i<3; i++){
            try{
                Thread.sleep(2000);
                System.out.println("Banking" + " .....");
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        System.out.println("Banking" + " Completed Successfully..........");

        System.out.println("-------------------------------");

    }
}

class Work8 extends Thread implements Runnable{
    public void run()  {

        System.out.println("Printing" + " Started Successfully..........");

        for(int i = 0; i<3; i++){

            try{
                Thread.sleep(2000);
                System.out.println("Printing" + " .....");
            } catch (Exception e){
                e.printStackTrace();
            }



        }

        System.out.println("Printing" + " Completed Successfully..........");

        System.out.println("-------------------------------");

    }
}

class Work9 extends Thread implements Runnable{
    public void run()  {

        System.out.println("Calculations" + " Started Successfully..........");

        for(int i = 0; i<3; i++){

            try{
                Thread.sleep(2000);System.out.println("Calculations" + " .....");
                System.out.println("Banking" + " .....");
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        System.out.println("Calculations" + " Completed Successfully..........");

        System.out.println("-------------------------------");

    }
}

public class MultiThreadingRunnableEx4 {

    static void main() {

        Work7 w7 = new Work7();
        Work8 w8 = new Work8();
        Work9 w9 = new Work9();

        w7.start();
        w8.start();
        w9.start();

        int total = 12; // (3*4)
        int n = 3;
        int m = 4;





    }


}
