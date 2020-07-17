package concurrentmodel.readerwriter.semaphoremethod;

public class Reader implements Runnable {
    ShareData data;
    public Reader(ShareData data){
        this.data = data;
    }
    @Override
    public void run() {
        data.printInfo(" starts to run");
        data.startReading();
        data.randomDelay(); //¶Á²Ù×÷
        data.endReading();
    }
}
