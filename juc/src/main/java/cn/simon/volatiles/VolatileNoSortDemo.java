package cn.simon.volatiles;

/**
 * 该代码演示volatile禁止指令重排的demo
 */
public class VolatileNoSortDemo {

//    volatile boolean flag = false;
    boolean flag = false;

    int num = 0;


    public void write(){
        num = 10;
        flag = true;
    }
    public void read(){
        if (flag){
            System.out.println(num);
        }
    }
}
