package cn.simon.threadlocal;

import java.util.Random;

/**
 * 该demo介绍ThreadLocal的api使用
 * 案例：
 * 某房产集团要统计各销售的销售总额
 * 这时我们要求某销售需要分开统计，来分奖金
 *
 */


class House{
    int count = 0;

    public synchronized void sell(){
        count++;
    }
//    ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>(){
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(()->0);
    public void saleByThreadLocal(){
        saleVolume.set(saleVolume.get()+1);
    }


}
public class ThreadLocalDemo01 {

    public static void main(String[] args) {

        House house = new House();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    int size = new Random().nextInt(5)+1;
                    for (int j = 0; j < size; j++) {
                        house.sell();
                        house.saleByThreadLocal();
                    }

                    System.out.println(Thread.currentThread().getName()+"\t"+"号售出楼房 "+house.saleVolume.get()+" 套楼房");
                }finally {
                    house.saleVolume.remove();

                }
            }).start();
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("共计卖出 "+house.count+" 套");
    }

}
