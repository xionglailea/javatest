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
        Thread t1 = new Thread(reader1, "���߳�1");
        Thread t2 = new Thread(reader2, "���߳�2");
        Thread t3 = new Thread(reader3, "���߳�3");
        Thread t4 = new Thread(reader4, "���߳�4");
        Thread t5 = new Thread(writer1, "д�߳�5");
        Thread t6 = new Thread(writer2, "д�߳�6");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
