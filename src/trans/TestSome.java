package trans;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by arctest on 2015/12/18.
 */
public class TestSome {

    public final static java.util.Map<Integer, Long> familyRankMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(20 % 10);
//        Map<Integer,Integer> map = new HashMap<>();
//        System.out.println(map.values().stream().reduce(0, Integer::sum));
//        Properties pro = System.getProperties(); //获取系统参数
//        System.out.println(pro);
//        PriorityQueue<Integer> pq = new PriorityQueue<>(20);
//        map.put(1,1);
//        System.out.println(map.get(2)==null?true:false);
//        System.out.println(getFindbackCfgKey());
//        System.out.println(51%30);
//        System.out.println(10%30);
//        int x = 0x00021210;
//        byte y = (byte)(x >> 8);
//        System.out.println(x + "," + y);
//        int i = 10;
//        while (i > 0){
//            aa test = new aa();
//            hbtest(test);
//            i--;
//        }
//        float a = randomflo();
//        int b = (int) (a / 0.2);
//        System.out.println(a + " " + b);
//        System.out.println(Math.round(4.3f));
//        System.out.println(Math.round(4.8f));
//        System.out.println(Math.round(4.5f));
//          GetRandom(10,16,4);
//        getMutiRandom1(5,10,4);
//        Map<Long,Integer> testmap = new HashMap<>();
//        testmap.put(1L,1);
//        Set<Long> set = new HashSet<>();//不能直接取出map的keyset，然后往里面add东西
//        set.addAll(testmap.keySet());
//        set.add(2L);
//        testLinkedList();
//        String s = "s" + "\n\r" + "e";
//        System.out.println(s.length());
//        System.out.println("转换前："+s);
//
//        s = s.replaceAll("\r|\n", "");
//        System.out.println(s.length());
//        System.out.println("转换后："+s);
//        String a = "11234";
//        System.out.println(a.split("_")[0]);
//        System.out.println(convert("12"));
//        familyRankMap.put(1, 10L);
//        familyRankMap.put(2, 20L);
//        familyRankMap.put(3, 30L);
//        familyRankMap.put(4, 40L);
//        familyRankMap.put(5, 50L);
//        familyRankMap.put(6, 60L);
//        familyRankMap.put(7, 70L);
//        familyRankMap.put(8, 80L);
//        removeMapKey(40);
//        removeMapKey(50);
//        addValue(90L);
//        deleteList();
//        iterator();
//        String a = "MERGESERVERS";
//        String[] all = a.split(",");
//        System.out.println(all.length);
//        for(String s : all){
//            System.out.println(s);
//        }
//        long a = -1L;
//        System.out.println(a);
//        System.out.println(getServeridByRoleid(2252309));
//        double a = 1.0;
//        System.out.println(position2IndexX(a));
//        System.out.println(decode("3EL0AXIW37"));
//        testTreeMap();
//        testArray();
//        float a= 1f;
//        double b = 1;
//        System.out.println(Math.atan2(1.0, 0.0) * 180 / Math.PI);  //计算 arc tan
//        System.out.println(a*b);
        sortTest();
    }

    public static void removeMapKey(int key){
        int orginalSize = familyRankMap.size();
        familyRankMap.entrySet().stream().filter(entry -> entry.getValue() == key).findFirst().ifPresent(e -> {
            familyRankMap.remove(e.getKey());
            for(int i = e.getKey() + 1; i <= orginalSize; i++){
                if(familyRankMap.containsKey(i)){
                    long fid = familyRankMap.remove(i);
                    familyRankMap.put(i - 1, fid);
                }
            }

        });
        System.out.println(familyRankMap);
    }

    public static void addValue(long value){
        int curSize = familyRankMap.size();
        familyRankMap.put(curSize + 1, value);
        System.out.println(familyRankMap);
    }

    public static void deleteList(){
        List<Integer> acceptTasks = new ArrayList<>();
        acceptTasks.add(0);
        acceptTasks.add(1);
        acceptTasks.add(2);
        acceptTasks.add(3);
        acceptTasks.add(4);
        acceptTasks.add(5);
//
        int completeNums = 4;
        int acceptLen = acceptTasks.size();
        if(acceptLen > completeNums){
            for(int i = acceptLen - 1; i >= completeNums; i--){
                acceptTasks.remove(i);
            }
        }
        System.out.println(acceptTasks);
    }

    public static int getFindbackCfgKey(){
        Map<Integer,Integer> map = new HashMap<>();
        int level = 95;
        map.put(30,1);
        map.put(40,1);
        map.put(50,1);
        map.put(60,1);
        map.put(70,1);
        map.put(80,1);
        map.put(90,1);
        map.put(100,1);
        Set<Integer> keySet = map.keySet();
        List<Integer> a = keySet.stream().filter(i -> i <= level).collect(Collectors.toList());
        return Collections.max(a);
    }

    public static class aa{
        int a = 1;
    }
    public static void hbtest(aa test) throws InterruptedException {

        Thread one = new Thread(() -> {

                System.out.println(test.a);
        });
        one.start();
        Thread.sleep(1000);
        test.a = 4;  //one线程中读取到的a是不确定的，可能为4，可能为1；加延迟后读取出来的就是1了
        one.join();
    }


    static Random random = new Random();

    public static Random random() {
        return random;
    }

    public static double random01() {
        return random.nextDouble();
    }

    public static double randomRange(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static float randomflo(){
        return random.nextFloat();
    }


    public static int[] getMutiRandom1(int minValue, int maxValue, int count){
        int[] intList = new int[maxValue - minValue + 1];
        int n = intList.length;
        for(int i = 0;i < n;i++){
            intList[i] = i + minValue;
        }
        int[] result = new int[count];
        for(int i = 0;i < count; i++){
            int index = random().nextInt(n);
            result[i] = intList[index];
            intList[index] = intList[--n];
        }
        System.out.println(Arrays.toString(result));
        return result;
    }

    public static void testLinkedList(){
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        list.remove(0);
        list.add(4);
        System.out.println(list);
    }

    public static int convert(String a){
        try{
            return Integer.parseInt(a);
        }catch (NumberFormatException e){
            System.out.println("wrong string");
            return 0;
        }
    }

    public static void iterator(){
        Map<Integer, Integer> test = new HashMap<>();
        test.put(1,1);
        test.put(2,2);
        test.put(3,3);
        for(Iterator<Map.Entry<Integer, Integer>> iterator = test.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<Integer, Integer> entry = iterator.next();
            System.out.println(entry.getKey());
            iterator.remove();
        }
        System.out.println(test);
    }

    public static int getServeridByRoleid(long roleid){
        return (int)(roleid & ((1 << 12) - 1));
    }


    public static int randomRange(int min, int max) {

        return min + random().nextInt(max - min);
    }

    public static long decode(String code) {
        if(code.length() > 12)
            code = code.substring(0, 12);
        return Long.parseLong(code, 36);
    }

    public static int position2IndexX(double x) {
        final int i = (int)((x - 0) / 0.0);
        if(i < 0)
            return 0;
        else if(i >= 25)
            return 25 - 1;
        else
            return i;
    }


    public static void testTreeMap(){
        Map<Integer, String> test = new TreeMap<>(); //treemap默认是用key做排序的
        for(int i = 0; i < 100; i++){
            int key = random.nextInt(1000);
            if(!test.containsKey(key)){
                test.put(key, "xiong");
            }
        }
        for(Map.Entry<Integer, String> entry : test.entrySet())
            System.out.println(entry.getKey() + " " + entry.getValue());
    }

    public static void testArray(){
        int[][] array = {
                {1,2,3,4,5},
                {1,2,3,4},
                {1,2,3}
        };
        System.out.println(array[0].length);
        for(int i : array[0]){
            System.out.print(i + " ");
        }
        try{
            int i = array[1][4];
            System.out.println("exist");
        }catch (Exception e){
            System.out.println("not exist");
        }
    }

    public static void sortTest(){
        List<Integer> a = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < 10000; i++ ){
            a.add(r.nextInt(10000));
        }
        long start = System.currentTimeMillis();
        Collections.sort(a);
        System.out.println(System.currentTimeMillis() - start);
    }

}
