package LabTask2;

public class Main{


        public static void main(String[] args) {
            // Create and start 5 threads
            for (int i = 0; i < 5; i++) {
                MyThread t = new MyThread();
                t.start();
            }
        }
}
