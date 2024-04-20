package cn.simon.cas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
class Book{

    private int id;
    private String bookName;

}
public class CASDemo05 {
    public static void main(String[] args) {
        Book javaBook = new Book(1,"javaBook");
        Book mysql = new Book(2,"mysql");
        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(javaBook,1);

        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        atomicStampedReference.compareAndSet(javaBook,mysql, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);

        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        atomicStampedReference.compareAndSet(mysql,javaBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);

        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());
    }
}
