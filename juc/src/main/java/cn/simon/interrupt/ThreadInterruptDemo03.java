package cn.simon.interrupt;

public class ThreadInterruptDemo03 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("t1 is working");
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " has stop");
                    break;
                }
            }

        }, "t1");
        t1.start();



        new Thread(
                ()->{
                    try {Thread.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
                    t1.interrupt();
                },"t2"
        ).start();

    }


}
