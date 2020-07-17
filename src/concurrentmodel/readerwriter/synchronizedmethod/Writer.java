package concurrentmodel.readerwriter.synchronizedmethod;

public class Writer implements Runnable {
    ShareData data;
    public Writer(ShareData data){
        this.data = data;
    }
    @Override
    public void run() {
        data.printInfo(" starts to run");
        data.startWrite();
        data.randomDelay(); //Ð´²Ù×÷
        data.endWrite();
    }
}
