package trans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiong on 2016/12/21.
 */
public class bishi {
    public static void main(String[] args) {
//        ceo("CCCEEE");
//        numOfWords("i love you");
        System.out.println( 1 > 1 ? 9.9 : 9);
    }



    public static void ceo(String str){
        int cNum = 0, eNum = 0, oNum = 0;
        for(char c : str.toCharArray()){
            if(c == 'C'){
                cNum++;
            }else if(c == 'E'){
                eNum++;
            }else if(c == 'O'){
                oNum++;
            }
        }
        StringBuilder sb = new StringBuilder();
        int all = Math.min(Math.min(cNum, eNum), oNum);
        cNum -= all;
        eNum -= all;
        oNum -= all;
        while (all > 0){
            sb.append("CEO");
            all--;
        }
        if(cNum == 0){
            int two = Math.min(eNum, oNum);
            eNum -= two;
            oNum -= two;
            while (two > 0){
                sb.append("EO");
                two--;
            }
            while (eNum > 0){
                sb.append("E");
                eNum--;
            }
            while (oNum > 0){
                sb.append("O");
                oNum--;
            }
        }else if(eNum == 0){
            int two = Math.min(cNum, oNum);
            cNum -= two;
            oNum -= two;
            while (two > 0){
                sb.append("CO");
                two--;
            }
            while (cNum > 0){
                sb.append("C");
                cNum--;
            }
            while (oNum > 0){
                sb.append("O");
                oNum--;
            }

        }else if(oNum == 0){
            int two = Math.min(cNum, eNum);
            cNum -= two;
            eNum -= two;
            while (two > 0){
                sb.append("CE");
                two--;
            }
            while (cNum > 0){
                sb.append("C");
                cNum--;
            }
            while (eNum > 0){
                sb.append("E");
                eNum--;
            }
        }
        System.out.println(sb.toString());
    }


    public static void numOfWords(String str){
        String[] allWords = str.split(" ");
        Map<String, Integer> words2num = new HashMap<>();
        for(String s : allWords){
            if(s.isEmpty())
                continue;
            int num = words2num.getOrDefault(s, 0);
            words2num.put(s, num + 1);
        }
        System.out.println(words2num.size());
    }

}
