package com.personal.Multithreading;

import com.personal.ExceptionConcepts.ThrowableEx;

class BathRoom implements Runnable {


    /**
     * This below causes unordered way of execution of the steps
     * for eg
     * Thread t1 might print " is using bathroom" but t2 comes and than prints " has entered bathroom"
     * so CPU may abruptly remove the current thread execution based on the priority of each incoming task
     * But in order to make this resource mutually exclusive to each thread at a time we use
     *

     *   Synchronized keyword
     *
     *   this is the new output after using synchronized keyword
     *
     *   MALE has entered the bathroom
     * MALE is using the bathroom
     * MALE exited the bathroom
     * FEMALE has entered the bathroom
     * FEMALE is using the bathroom
     * FEMALE exited the bathroom
     * OTHERS has entered the bathroom
     * OTHERS is using the bathroom
     * OTHERS exited the bathroom
     *
     *
     *  Synchronized keyword is only used when Threads are sharing a resource and we dont want
     *  multiple threads to be in this space at the same time
     */

    @Override
    synchronized public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " has entered the bathroom");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " is using the bathroom");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " exited the bathroom");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

public class SynchronizedEx1 {

    static void main() throws Exception {
        BathRoom b = new BathRoom();

        Thread t1 = new Thread(b);
        Thread t2 = new Thread(b);
        Thread t3 = new Thread(b);

        t1.start();
        // t1.join(); //Waits for this thread to terminate . An invocation of this method behaves in exactly the same way as the invocation
        t2.start();
        t3.start();

        t1.setName("MALE");
        t2.setName("FEMALE");
        t3.setName("OTHERS");

        // if you want T1 to work first than T2 and T3 in this order of execution only
        // then do this below

        /**
         *
         * t1.start( )
         *
         * t1.join() --> let t1 complete
         *
         */


    }
}
