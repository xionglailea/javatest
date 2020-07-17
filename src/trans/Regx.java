package trans;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by xiong on 2016/7/26.
 */
public class Regx {
    public static void main(String[] args) {
        String s1= "毛泽东";
        String regx1 = ".*";
        for(int i = 0 ; i < s1.length(); i++){
            regx1 += s1.charAt(i) + ".*";
        }
        String s2 = "习近平";
        String regx2 = ".*";
        for(int i = 0 ; i < s2.length(); i++){
            regx2 += s2.charAt(i) + ".*";
        }
        System.out.println(regx1);
        System.out.println(regx2);
        String regx = regx1+ "|" + regx2 + "|.*李.*克.*强.*";
        System.out.println(regx);
        Pattern p = Pattern.compile(regx);
        String tofind = "哈习哈哈近*平";
        Matcher m = p.matcher(tofind);
//        while(m.find()){
//            System.out.println(m.group());
//        }
//        boolean result = m.find();
//        System.out.println(result);
        System.out.println(m.replaceAll("***"));
        System.out.println(10%5);
//        System.out.println(getFuzzyWords());
    }

    public static String getFuzzyWords(){
        Set<String> ret = new HashSet<>();
        ret.add("习近平");
        ret.add("李克强");
        ret.add("毛泽东");
        return ret.stream().map(s -> makeRegx(s)).collect(Collectors.joining("|"));
    }

    public static String makeRegx(String s){
        String regx = ".*";
        for(int i = 0 ; i < s.length(); i++){
            regx += s.charAt(i) + ".*";
        }
        return regx;
    }
}
