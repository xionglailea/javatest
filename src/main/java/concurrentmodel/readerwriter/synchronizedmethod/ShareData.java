package concurrentmodel.readerwriter.synchronizedmethod;

//�����ж���� �ж���ʱ����д����д��ʱ�����ж�д
// ���ܵ���д�̼߳���
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

    public synchronized void startRead(){ //ͬ������
        while (isWriting){
           try{
               printInfo(" is waiting!");
               wait(); //��ͬ�������������ܵ���,�ڵ�ǰ�����ϵ���
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
