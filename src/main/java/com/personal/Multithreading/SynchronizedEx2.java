package com.personal.Multithreading;

class INueron implements Runnable
{

    @Override
    public void run() {
        try {
            for(int i = 0; i<5; i++){
                System.out.println(i);
            }
            Thread.sleep(3000);

            // Lets say i only want this below block of printing ASCII to be in synchronized,
            // other block does not matter to me, we can do this as well

            /**
             * synchronized(this) means:
             *
             * "Before entering this block, grab this object's key. Hold it while running the block. Release it when leaving."
             *
             * The key does not freeze the object or protect its other code. It's just a token.
             * The only effect of the key is this: another thread that also tries to grab the same key has to wait.
             * Code that doesn't ask for the key is completely unaffected — which is exactly why your first loop (unsynchronized) runs freely at the same time.
             *
             * So the lock is cooperative/advisory — it only excludes code that also synchronizes on the same object. It's not a force field around the object.
             */

            synchronized (this) {
                for(int i = 0; i<5; i++){
                    System.out.println((char)(i + 65)); // 65 is ASCII value of 'A' character
                }
            }

        } catch (Exception e){
            System.out.println("Some interruption");
        }
    }
}

public class SynchronizedEx2 {

    static void main() {
        INueron in = new INueron();

        Thread t1 = new Thread(in);
        Thread t2 = new Thread(in);
        Thread t3 = new Thread(in);

        t1.start();
        t2.start();
        t3.start();
    }
}
