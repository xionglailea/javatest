package concurrentmodel.readerwriter.semaphoremethod;

public interface ShareData {
    void startReading();
    void endReading();
    void startWriting();
    void endWriting();
    default void printInfo(String info){
        System.out.println(Thread.currentThread().getName() + info);
    }

    default void randomDelay(){
        try {
            Thread.sleep((long) (4000 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
