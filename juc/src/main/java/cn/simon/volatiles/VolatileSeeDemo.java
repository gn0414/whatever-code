package cn.simon.volatiles;


/**
 * @author Simon
 *
 * 该demo演示
 * volatile的可见性
 */
public class VolatileSeeDemo {

//    static boolean flag = true;

    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("t1 is come in");
            while (flag){

            }
            System.out.println("flag修改已检测,退出程序");
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        flag = false;

        System.out.println("flag修改了,flag:"+flag);
    }

}
