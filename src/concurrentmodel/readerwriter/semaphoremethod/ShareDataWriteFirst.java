package concurrentmodel.readerwriter.semaphoremethod;

import java.util.concurrent.Semaphore;

//д����,ֻҪ��д�߳��ڵȴ�����ôд�̻߳���ִ��
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
            queue.acquire(); //�Ŷ� Ҫ��ȡ������
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
            if (writerCount == 1){  //ֻҪ���߳���д����ô������һֱ���ţ����߳��޷���ȡ��������ֻ�ܵ�д����ܻ�ȡ
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
