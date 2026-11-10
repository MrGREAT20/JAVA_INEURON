package com.personal.Multithreading;

class Thread1 extends Thread {

    int [][] ans;

    Thread1(int[][] ans) {
        this.ans = ans;
    }

    @Override
    public void run(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<4; j++){
                int curr = (3 * j + i);
                if(curr % 3 == 0){
                    this.ans[i][j] = curr + 1;
                }
            }
        }
    }
}

class Thread2 extends Thread {

    int [][] ans;

    Thread2(int[][] ans) {
        this.ans = ans;
    }

    @Override
    public void run(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<4; j++){
                int curr = (3 * j + i);
                if(curr % 3 == 1){
                    this.ans[i][j] = curr + 1;
                }
            }
        }
    }

}

class Thread3 extends Thread {

    int [][] ans;

    Thread3(int[][] ans) {
        this.ans = ans;
    }

    @Override
    public void run(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<4; j++){
                int curr = (3 * j + i);
                if(curr % 3 == 2){
                    this.ans[i][j] = curr + 1;
                }
            }
        }
    }

}

public class ThreadProblemSolving1 {

    static void main() throws Exception {
        int[][] ans = new int[3][4];

        Thread1 t1 = new Thread1(ans);
        Thread2 t2 = new Thread2(ans);
        Thread3 t3 = new Thread3(ans);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        for(int i = 0; i<3; i++){
            System.out.print("t" + (i+1) + " ");
            for(int j = 0; j<4; j++){
                System.out.print(ans[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }
}
