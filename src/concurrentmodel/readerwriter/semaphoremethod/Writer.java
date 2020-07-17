package concurrentmodel.readerwriter.semaphoremethod;

public class Writer implements Runnable {
    ShareData data;
    public Writer(ShareData data){
        this.data = data;
    }
    @Override
    public void run() {
        data.printInfo(" starts to run");
//        while (true) {
            data.startWriting();
            data.randomDelay(); //Ð´²Ù×÷
            data.endWriting();
//        }
    }
}
