package concurrentmodel.philosopher;

public class Philosopher implements Runnable {

    int index;
    ShareData data;

    public Philosopher(int index, ShareData data) {
        this.index = index;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.printInfo(" is thinking");
                data.randomDelay(); //ģ��˼������
                data.take(index);
                data.printInfo(" is eating");
                Thread.sleep(500L); //ģ��Է�����
                data.put(index);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
