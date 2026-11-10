package com.personal.Multithreading;

class Work4 implements Runnable{

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

class Work5 implements Runnable{
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

class Work6 implements Runnable{
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

public class MultithreadingRunnableEx3 {

    static void main() {

        Work4 w4 = new Work4(); // This will not create a Thread, because Work4 is implementing Runnable
        Work5 w5 = new Work5();
        Work6 w6 = new Work6();


        Thread t1 = new Thread(w4); // This will create a Thread , but not yet executed.
        Thread t2 = new Thread(w5); // we need to pass runnable to Thread during constructor initialization,
        Thread t3 = new Thread(w6); // because since we are creating thread, we must also pass the run method for the thread to execute

        t1.start();
        t2.start();
        t3.start();

        /**
         * Now the question is which is better approach between both implementations
         * Shall we implement multithreading using Thread class or implementing Runnable ?
         *
         * Answer: By Implementing Runnable we are opening doors for multiple inheritance which
         * might not be possible if we extend Thread Class
         * as we know we can implement Interface and extend other classes in the same way
         *
         */

    }
}
