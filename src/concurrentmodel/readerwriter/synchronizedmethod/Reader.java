package concurrentmodel.readerwriter.synchronizedmethod;

public class Reader implements Runnable {

    ShareData data;

    public Reader(ShareData data){
        this.data = data;
    }
    @Override
    public void run() {
        data.printInfo(" starts to run");
        data.startRead();
        data.randomDelay(); //¶Á²Ù×÷
        data.endRead();
    }
}
