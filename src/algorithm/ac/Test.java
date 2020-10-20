package algorithm.ac;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ����
 *
 * <p>
 * create by xiongjieqing on 2020/10/15 18:30
 */
public class Test {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("data/small.dic"));
        String line;
        List<String> words = new ArrayList<String>();
        Set<Character> charset = new HashSet<Character>();
        while ((line = reader.readLine()) != null) {
            words.add(line);
            // ����һ�����debug
            for (char c : line.toCharArray()) {
                charset.add(c);
            }
        }
        reader.close();
        // ����ֵ����Ҫ�����´ʱ��밴�ֵ��򣬲ο�����Ĵ���
        //        Collections.sort(words);
        //        BufferedWriter writer = new BufferedWriter(new FileWriter("./data/sorted.dic", false));
        //        for (String w : words)
        //        {
        //            writer.write(w);
        //            writer.newLine();
        //        }
        System.out.println("�ֵ������" + words.size());

        {
            String infoCharsetValue = "";
            String infoCharsetCode = "";
            for (Character c : charset) {
                infoCharsetValue += c.charValue() + "    ";
                infoCharsetCode += (int) c.charValue() + " ";
            }
            infoCharsetValue += '\n';
            infoCharsetCode += '\n';
            System.out.print(infoCharsetValue);
            System.out.print(infoCharsetCode);
        }

        DoubleArrayTrie dat = new DoubleArrayTrie();
        System.out.println("�Ƿ����: " + dat.build(words));
        System.out.println(dat);
        List<Integer> integerList = dat.commonPrefixSearch("����");
        for (int index : integerList) {
            System.out.println(words.get(index));
        }
    }
}
