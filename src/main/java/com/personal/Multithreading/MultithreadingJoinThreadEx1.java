package com.personal.Multithreading;

class Work10 extends Thread implements Runnable{

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

class Work11 extends Thread implements Runnable{
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

class Work12 extends Thread implements Runnable{
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

public class MultithreadingJoinThreadEx1 {

    static void main() throws Exception {

        Thread t = Thread.currentThread();

        Work10 w10 = new Work10();
        Work11 w11 = new Work11();
        Work12 w12 = new Work12();

        w10.start();
        w11.start();
        w12.start();


        // System.out.println("Main thread execution completed " + t.getName() + " is " + t.isAlive());

        /**
         * In the above scenario, Main Thread execution completed log is displayed before all the Work logs
         * are completed
         *
         * To handle such use case, where we have to wait for other threads to complete, we can use
         * thread.join( )
         */

        w10.join();
        w11.join();
        w12.join();

        // This above means, the current thread Main Thread is waiting for w10, w11, w12 to complete

        System.out.println("Main thread execution completed " + t.getName() + " is " + t.isAlive());



    }


}
