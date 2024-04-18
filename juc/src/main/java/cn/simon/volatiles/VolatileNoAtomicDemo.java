package cn.simon.volatiles;

/**
 * @author Simon
 * 该demo演示
 * volatile没有原子性的案例
 */

class MyNum{
    volatile int num;

//    public synchronized void addNum(){
//        num++;
//    }

    public void addNum(){
        num++;
    }

    public int getNum() {
        return num;
    }
}
public class VolatileNoAtomicDemo {
    public static void main(String[] args) {
        MyNum myNum = new MyNum();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myNum.addNum();
                }
            }).start();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(myNum.getNum());
        }
}
