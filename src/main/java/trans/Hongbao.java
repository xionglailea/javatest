package trans;

import java.util.Random;

/**
 * Created by xiong on 2017/1/5.
 */
public class Hongbao {
    public static void main(String[] args) {
        MoneyPackage moneyPackage = new MoneyPackage(10, 100);
        for(int i = 1; i <= 100; i++){
            int money = getMoney(moneyPackage);
            System.out.println("i:" + i + "; get money:" + money + ";" + moneyPackage);
        }
    }

    //过年红包，保证每个人至少能得到1元宝
    static class MoneyPackage{
        public int remainSize;
        public int remainMoney;
        public MoneyPackage(int size, int money){
            this.remainMoney = money;
            this.remainSize = size;
        }

        @Override
        public String toString() {
            return "remainSize = " + remainSize + ";remainMoney = " + remainMoney;
        }
    }

    public static int getMoney(MoneyPackage moneyPackage){
        int result = 0;
        if(moneyPackage.remainSize <= 0){
            return 0;
        }
        if(moneyPackage.remainSize == 1){
            moneyPackage.remainSize--;
            result = moneyPackage.remainMoney;
            moneyPackage.remainMoney = 0;
            return result;
        }
        int extra = moneyPackage.remainMoney - moneyPackage.remainSize;
        if(extra <= 0){
            moneyPackage.remainSize--;
            moneyPackage.remainMoney -= 1;
            return 1;
        }else {
            Random r = new Random();
            int i = r.nextInt(extra + 1);
            result = i + 1;
            moneyPackage.remainSize--;
            moneyPackage.remainMoney -= result;
            return result;
        }
    }


}
