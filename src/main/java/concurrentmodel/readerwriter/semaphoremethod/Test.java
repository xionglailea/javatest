package concurrentmodel.readerwriter.semaphoremethod;

public class Test {
    public static void main(String[] args) {
        ShareDataWriteFirst data = new ShareDataWriteFirst();
        Reader reader1 = new Reader(data);
        Reader reader2 = new Reader(data);
        Reader reader3 = new Reader(data);
        Reader reader4 = new Reader(data);
        Writer writer1 = new Writer(data);
        Writer writer2 = new Writer(data);
        Thread t1 = new Thread(reader1, "读线程1");
        Thread t2 = new Thread(reader2, "读线程2");
        Thread t3 = new Thread(reader3, "读线程3");
        Thread t4 = new Thread(reader4, "读线程4");
        Thread t5 = new Thread(writer1, "写线程5");
        Thread t6 = new Thread(writer2, "写线程6");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
