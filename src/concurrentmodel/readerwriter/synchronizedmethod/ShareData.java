package concurrentmodel.readerwriter.synchronizedmethod;

//可以有多个读 有读的时候不能写，有写的时候不能有读写
// 可能导致写线程饥饿
public class ShareData {
    int readerCount;
    boolean isReading;
    boolean isWriting;

    public void printInfo(String info){
        System.out.println(Thread.currentThread().getName() + info);
    }

    public void randomDelay(){
        try {
            Thread.sleep((long) (4000 * Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void startRead(){ //同步方法
        while (isWriting){
           try{
               printInfo(" is waiting!");
               wait(); //在同步代码块里面才能调用,在当前对象上调用
           } catch (InterruptedException e){
                e.printStackTrace();
           }
        }
        printInfo(" starts read!");
        readerCount++;
        if (readerCount == 1) {
            isReading = true;
        }
        printInfo(" current readCount " + readerCount);
    }

    public synchronized void endRead(){
        --readerCount;
        if (readerCount == 0){
            isReading = false;
        }
        notifyAll();
        printInfo(" reading done!");
        printInfo(" current readCount " + readerCount);

    }

    public synchronized void startWrite(){
        while (isReading || isWriting){
            try {
                printInfo(" is waiting!");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printInfo(" starts write!");
        isWriting = true;
    }

    public synchronized void endWrite(){
        isWriting = false;
        notifyAll();
        printInfo(" writing done!");
    }

}
