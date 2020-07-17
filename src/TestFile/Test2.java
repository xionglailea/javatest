package TestFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws IOException {
        List<String> all = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\new 1.txt"));
        int a = 0,b = 0,c = 0,d = 0,e = 0,f = 0,g = 0,h = 0,i = 0,j = 0, k = 0;
        for(String s : all){
            String[] strings = s.split(" ");
            if(strings[0].equals("办公电脑主机")){
                a += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("显示器22寸")){
                b += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("显示器24寸")){
                c += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("电话机")){
                d += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("插线板")){
                e += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("网线")){
                f += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("绘图板")){
                g += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("笔记本电脑")){
                h += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("衣物")){
                i += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("行李箱")){
                j += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("行军床")){
                k += Integer.parseInt(strings[1]);
            }
        }
        System.out.println("办公电脑主机 " + a);
        System.out.println("显示器22寸 " + b);
        System.out.println("显示器24寸 " + c);
        System.out.println("电话机 " + d);
        System.out.println("插线板 " + e);
        System.out.println("网线 " + f);
        System.out.println("绘图板 " + g);
        System.out.println("笔记本电脑 " +h);
        System.out.println("衣物 " + i);
        System.out.println("行李箱 " + j);
        System.out.println("行军床 " + k);
    }

}
