package cn.simon.obejcthead;


import org.openjdk.jol.info.ClassLayout;

class Ticket{

    private int ticket = 50;

    private Object o = new Object();

    public void sale(){
        synchronized (o){
            for(;;) {
                if (ticket <= 0) break;
                System.out.println(Thread.currentThread().getName() + " sale ticket leave " + --ticket);
            };
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

}
public class SaleTicketDemo04 {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Thread a = new Thread(ticket::sale,"a");
        Thread b = new Thread(ticket::sale,"b");
        Thread c = new Thread(ticket::sale,"c");


        a.start();
        b.start();
        c.start();


    }
}
