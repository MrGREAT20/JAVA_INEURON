package com.personal.Multithreading;

class Work {
    static void startTask(String taskName) throws Exception {

        System.out.println(taskName + " Started Successfully..........");

        for(int i = 0; i<3; i++){
            Thread.sleep(2000);
            System.out.println(taskName + " .....");
        }

        System.out.println(taskName + " Completed Successfully..........");

        System.out.println("-------------------------------");

    }
}


class Work1 extends Thread{

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

class Work2 extends Thread{
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

class Work3 extends Thread{
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

public class MultithreadingEx2 {

    static void main() throws Exception{

        Work.startTask("Banking");
        Work.startTask("Printing");
        Work.startTask("Calculation");

        /**
         * In the above example, we have 3 functions each unrelated to each other, this all functions are
         * executed by a single Thread only. Because of that even though tasks are unrelated, other functions are waiting
         */


        /**
         * 2 ways to create a Thread, Either make a Thread Object or use Runnable Interface
         * Runnable is a functional interface with only 1 abstract method "run"
         * Thread class also implements runnable interface
         */


        System.out.println("------------------NEW EXAMPLE------------------------");

        // In this below example Work1, Work2, Work3 all extends Thread, which means 3 threads will be
        // created, in total we have 4 threads in this application (3 custom threads + 1 main thread)

        Work1 w1 = new Work1();
        Work2 w2 = new Work2();
        Work3 w3 = new Work3();


        // Calling w1.start() creates a new thread and is assigned to Thread scheduler and that thread invokes run() during its execution.
        // If you called w1.run() directly, it would just execute on the main thread (no new thread, no parallelism)

        w1.start();
        w2.start();
        w3.start();

    }
}
