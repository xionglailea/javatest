package trans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiong on 2016/12/1.
 */
public class MergeCompensation {
    public static void main(String[] args) throws IOException {
        Map<Integer, Items> detail = new HashMap<>();
        List<String> levels = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\merge.txt"));
        for(String s : levels){
            Items items = new Items(s);
            detail.put(items.level, items);
        }
        System.out.println(detail);
    }

    public static class Pair{
        public int key;
        public int num;
        public Pair(int key, int num){
            this.key = key;
            this.num = num;
        }

        @Override
        public String toString() {
            return "key:" + key + " num:" + num;
        }
    }

    public static class Items{
        public int level;
        public Pair jinyandan;
        public Pair lianqifu;
        public Pair guanzhufu;
        public Pair shipinglianqifu;
        public Pair shipingguanzhufu;
        public Pair wufadan;
        public Pair xiandou;
        public Pair butianshi;
        public Pair jinbi;
        public Items(String detail){
            String[] all = detail.split("\t");
            level = Integer.parseInt(all[0]);
            jinyandan = new Pair(10100084, Integer.parseInt(all[1]));
            lianqifu = new Pair(10400001, Integer.parseInt(all[2]));
            guanzhufu = new Pair(10400006, Integer.parseInt(all[3]));
            shipinglianqifu = new Pair(10400036, Integer.parseInt(all[4]));
            shipingguanzhufu = new Pair(10400037, Integer.parseInt(all[5]));
            wufadan = new Pair(10400041, Integer.parseInt(all[6]));
            xiandou = new Pair(10400013, Integer.parseInt(all[7]));
            butianshi = new Pair(10400012, Integer.parseInt(all[8]));
            jinbi = new Pair(10200001, Integer.parseInt(all[9]));
            System.out.println(this);
        }

        public void addItems(long roleid, int multi){

        }

        @Override
        public String toString() {
            return "level:" + level + " jinyandan:" + jinyandan + " lianqifu:" + lianqifu +
                    " guanzhufu:" + guanzhufu + " shipinglianqifu" + shipinglianqifu + " shipingguanzhufu" + shipingguanzhufu +
                     " wufadan"  + wufadan + " xiandou:" + xiandou + " butianshi:" + butianshi + " jinbi:" + jinbi;
        }
    }
}
