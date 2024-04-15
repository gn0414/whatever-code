package cn.simon.future;

import lombok.Data;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author simon
 *
 * 电商比价需求
 * 同时搜索出同款产品在各大电商平台的售价
 */
public class CompletableFutureTaskDemo6 {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<NetMall> list = Arrays
                .asList(
                  new NetMall("jd"),
                  new NetMall("tb"),
                  new NetMall("dd")
                );
//        List<String> prices = stepByStep(list, "mysql");
        List<String> prices = completableFutureSelect(list, "mysql");

        prices.forEach(System.out::println);

        long end = System.currentTimeMillis();

        System.out.println("all search cost:"+(end-start)+"ms");
    }

    public static List<String> stepByStep(List<NetMall> list,String productName){
        return list.stream().map(
                n -> String.format(productName + " in %s price is %.2f",n.getName(),n.calcPrice(productName))
        ).collect(Collectors.toList());
    }

    public static List<String> completableFutureSelect(List<NetMall> list,String productName) {
        return list.stream().map(n -> CompletableFuture.supplyAsync(()->String.format(productName + " in %s price is %.2f",n.getName(),n.calcPrice(productName))))
                .toList()
                .stream()
                .map(s -> s.join()).collect(Collectors.toList());

    }


}

@Data
class NetMall
{
    private String name;
    public NetMall(String name){
        this.name = name;
    }
    public double calcPrice(String productName)  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }

}
