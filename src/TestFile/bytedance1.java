package TestFile;

public class bytedance1 {
    public static void main(String[] args) {
        int num = 10000;
        int[] data = new int[num];
        for(int i = 0; i < num; i++){
            data[i] = i + 1;
        }
        int leftNum = num;
        while (leftNum > 0){
            int noZero = 0;
            for(int i = 0; i < num; i++){
//                if (leftNum == 1 && data[i] != 0){
//                    System.out.println(data[i]);
//                    System.exit(0);
//                }
                if (data[i] != 0){
                    noZero++;
                    if (noZero % 2 == 0){
                        if (leftNum == 2){
                            System.out.println(data[i]);
                            System.exit(0);
                        }
                        data[i] = 0;
                    }
                }
            }
            leftNum = leftNum / 2 + leftNum % 2;
            System.out.println(leftNum);

        }
    }
}
