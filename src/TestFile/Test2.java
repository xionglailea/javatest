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
            if(strings[0].equals("�칫��������")){
                a += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("��ʾ��22��")){
                b += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("��ʾ��24��")){
                c += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("�绰��")){
                d += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("���߰�")){
                e += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("����")){
                f += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("��ͼ��")){
                g += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("�ʼǱ�����")){
                h += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("����")){
                i += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("������")){
                j += Integer.parseInt(strings[1]);
            }else if(strings[0].equals("�о���")){
                k += Integer.parseInt(strings[1]);
            }
        }
        System.out.println("�칫�������� " + a);
        System.out.println("��ʾ��22�� " + b);
        System.out.println("��ʾ��24�� " + c);
        System.out.println("�绰�� " + d);
        System.out.println("���߰� " + e);
        System.out.println("���� " + f);
        System.out.println("��ͼ�� " + g);
        System.out.println("�ʼǱ����� " +h);
        System.out.println("���� " + i);
        System.out.println("������ " + j);
        System.out.println("�о��� " + k);
    }

}
