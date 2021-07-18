package concurrentmodel.readerwriter.semaphoremethod;

import java.util.concurrent.Semaphore;

//信号量的方式
public class ShareDataReadFirst implements ShareData {
    int readerCount = 0;
    Semaphore writSem = new Semaphore(1);
    Semaphore readSem = new Semaphore(1);


    public void startReading(){
        try {
            readSem.acquire();
            printInfo(" starts to read!");
            readerCount++;
            if (readerCount == 1) {
                writSem.acquire();
            }
            printInfo(" current readCount " + readerCount);
            readSem.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void endReading(){
        try {
            readSem.acquire();
            printInfo(" reading done");
            readerCount--;
            if (readerCount == 0){
                writSem.release();
            }
            printInfo(" current readCount " + readerCount);
            readSem.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startWriting(){
        try {
            writSem.acquire();
            printInfo(" starts to write!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void endWriting(){
        printInfo(" write done!");
        writSem.release();
    }
}
