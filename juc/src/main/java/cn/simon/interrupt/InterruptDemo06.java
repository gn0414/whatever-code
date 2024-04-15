package cn.simon.interrupt;

public class InterruptDemo06 {

    public static void main(String[] args) {
        System.out.println(Thread.interrupted());
        System.out.println(Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("-----");
        System.out.println(Thread.interrupted());
        //可以很明确的看出来，中断状态确实是清除了
        System.out.println(Thread.interrupted());


    }
}
