package com.personal.Multithreading;

public class MultithreadingEx1 {
    static void main() {
        Thread t = Thread.currentThread();
        System.out.println(t);

        /**
         * Output :
         *
         * Thread[#3,main,5,main]
         *
         *
         * Reason :
         *
         * The #3 in your output represents the Thread ID (a unique number automatically assigned to the thread by the Java Virtual Machine when it is created).
         * You are seeing this specific format because you are using Java 19 or newer.
         * Prior to Java 19, the thread ID was hidden, but Java updated the Thread.toString() method to include it,
         * primarily to help developers track threads easier with the introduction of Virtual Threads.
         * Here is the exact breakdown of the output Thread[#3,main,5,main]:
         *
         * Value     What it means      Description
         *
         * #3          Thread ID      The unique identifier for this thread (3).
         *
         * main        Thread Name       The name of the thread executing your code. Because this is the primary entry point of your application, it is named "main".
         *
         * 5            Priority         The priority of the thread. Java thread priorities range from 1 (lowest) to 10 (highest). 5 is the default "normal" priority.
         *
         * main        Thread Group       The name of the group this thread belongs to. Java groups threads to manage them together; the default group for the main thread is also called "main".
         *                                Why isn't the main thread ID #1?While you might expect your main thread to be the very first thread created (ID 1),
         *                                the JVM actually spins up several internal system threads in the background fractions of a second before your code starts running.
         *                                These background threads (which handle things like garbage collection and system signals) claim the first few IDs,
         *                                leaving your main thread with an ID like 3.
         *
         */

        // We can change the thread name, priority

        t.setName("Pralay ka Thread");
        t.setPriority(4);

        System.out.println(t);

    }
}

