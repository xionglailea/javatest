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
                data.randomDelay(); //模拟思考过程
                data.take(index);
                data.printInfo(" is eating");
                Thread.sleep(500L); //模拟吃饭过程
                data.put(index);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
