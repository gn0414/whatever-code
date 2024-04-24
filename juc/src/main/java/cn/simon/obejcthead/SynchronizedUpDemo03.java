package cn.simon.obejcthead;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedUpDemo03 {

    public static void main(String[] args) {
        Object o = new Object();
        //注意我们hashcode这个要调用了才会在Mark Word里面体现
        System.out.println("10进制 "+o.hashCode());
        System.out.println("16进制 "+Integer.toHexString(o.hashCode()));
        System.out.println("2进制 "+Integer.toBinaryString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());



    }
}
