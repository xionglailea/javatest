package concurrentmodel.readerwriter.semaphoremethod;

import java.util.concurrent.Semaphore;

//写优先,只要有写线程在等待，那么写线程会先执行
public class ShareDataWriteFirst implements ShareData {
    int readerCount = 0;
    int writerCount = 0;
    Semaphore writSem = new Semaphore(1);
    Semaphore readSem = new Semaphore(1);
    Semaphore queue = new Semaphore(1);
    Semaphore writFirst = new Semaphore(1);
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

    public void startReading(){
        try {
            queue.acquire(); //排队 要获取队列锁
            readSem.acquire();
            printInfo(" starts to read!");
            readerCount++;
            if (readerCount == 1) {
                writSem.acquire();
            }
            printInfo(" current readCount " + readerCount);
            readSem.release();
            queue.release();
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
            writFirst.acquire();
            writerCount++;
            if (writerCount == 1){  //只要有线程在写，那么队列锁一直不放，读线程无法获取队列锁，只能等写完才能获取
                queue.acquire();
            }
            printInfo(" write count is " + writerCount);
            writFirst.release();
            writSem.acquire();
            printInfo(" starts to write!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void endWriting(){
        printInfo(" write done!");
        try {
            writFirst.acquire();
            writerCount--;
            if (writerCount == 0){
                queue.release();
            }
            printInfo(" write count is " + writerCount);
            writFirst.release();
            writSem.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
