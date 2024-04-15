package cn.simon.interrupt;

public class InterruptDemo05 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("t1线程中断停止");
                    break;
                }
                System.out.println("hello t1");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt(); //为什么要在异常处再调用一次
                    e.printStackTrace();
                }
            }
        },"t1");


        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(t1::interrupt).start();

    }
}
