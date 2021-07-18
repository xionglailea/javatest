package concurrentmodel.philosopher;

import java.util.HashMap;
import java.util.Map;

public class ShareData {
    private boolean[] flag;
    private int num;
    private Map<Integer, Integer> statistic = new HashMap<>();

    public ShareData(int num) {
        flag = new boolean[num];
        this.num = num;
        for(int i = 0; i < num; i++){
            statistic.put(i, 0);
        }
    }

    public synchronized void take(int i){
        printInfo(" start to take");
        while(flag[i] || flag[(i + 1) % num]){
            try {
                printInfo(" is waiting");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printInfo(" take success");
        flag[i] = true;
        flag[(i + 1) / num] = true;
        int old = statistic.getOrDefault(i, 0);
        statistic.put(i, old + 1);
    }

    public synchronized void put(int i){
        printInfo(" put back");
        flag[i] = false;
        flag[(i + 1) / num] = false;
        notifyAll();
    }

    public void randomDelay() throws InterruptedException {
        Thread.sleep((long) (1000 * Math.random()));
    }

    public void printInfo(String info){
//        System.out.println(Thread.currentThread().getName() + info);
    }

    //按理说最后的获取次数应该是大致相等的
    public void printStatistic(){
        for(int i = 0; i < num; i++){
            System.out.print(i + " " + statistic.get(i) + " | ");
        }
        System.out.println();
    }

}
