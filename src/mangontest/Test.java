package mangontest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;

public class Test {
    public static void main(String[] args) {
        //testBatchInsert1();
        //testReplace();
        //  testBatchInsert();
        //  testTravers();
        //  int cap = 100000;
        //  Random random = new Random();
        //  Map<Integer, Integer> map = new HashMap<>(cap);
        //
        //
        //
        //  for (int i = 0; i < 100000; i++) {
        //      map.put(i, i);
        //  }
        //
        //  long now = System.currentTimeMillis();
        //  for (int i = 0; i < 10000000; i++) {
        //      var key = random.nextInt(1000000);
        //      map.get(key);
        //  }
        //  System.out.println(System.currentTimeMillis() - now);


          test1();
        //  testBatchRead();
        //test11();
    }

    public static void test11() {
        int cap = 16000000;
        Map<Integer, Integer> map = new HashMap<>(cap);

        for (int i = 0; i < 16000000; i++) {
            map.put(i, i);
        }
        long value = 0;

        for (int x = 0; x < 10; x++) {

            long now = System.currentTimeMillis();
            for (int j = 0; j < 16000000; j++) {
                map.get(j);
            }
            System.out.println(System.currentTimeMillis() - now);

        }
    }

    public static void test10() {
        int cap = 1000000;
        Map<Integer, Integer>[] map = new HashMap[16];

        for (int i = 0; i < 16; i++) {
            map[i] = new HashMap<>((int) (cap / 0.75));
        }

        for (int i = 0; i < 16; i++) {
            for (int j = i * 1000000; j < (i + 1) * 1000000; j++) {
                map[i].put(j, j);
            }
        }

        for (int x = 0; x < 10; x++) {


            long now = System.currentTimeMillis();


            long value = 0;

            for (int i = 0; i < 16; i++) {

                for (int j = i * 1000000; j < (i + 1) * 1000000; j++) {
                    map[i].get(j);
                }

                //for (int j = 0; j < 1000000; j++) {
                //    //var value = j & 15;
                //    var value = map[j & 15].get(j);
                //    //value.size();
                //    //var value = map[1].get(j);
                //
                //    //var value = map[j & 15].get(j);
                //}
            }


            System.out.println(System.currentTimeMillis() - now);
        }
    }

    public static void test1() {
        Storage storage = new Storage();
//    storage.testWrite(new RoleInfo(1, "xiong", 1));
//    storage.testWrite(new RoleInfo(2, "jieqing", 2));
//    String collectionName = "roleInfo";
//    storage.count(collectionName);
//    storage.testRead(1);
//    storage.testRead(2);
//    storage.deleteOne(collectionName, 1);
//    storage.deleteOne(collectionName, 2);
//    storage.count(collectionName);
//    storage.testRead(1);
//    storage.testRead(2);
        long start = System.currentTimeMillis();
        for (int i = 30000; i < 40000; i++) {
            storage.testRead(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time = " + (end - start));
    }

    public static void testTravers() {
        Storage storage = new Storage();
//    storage.testWrite(new RoleInfo(1, "xiong", 1));
//    storage.testWrite(new RoleInfo(2, "jieqing", 2));
        String collectionName = "roleInfo";
        long start = System.currentTimeMillis();
        storage.travers(collectionName);
        long end = System.currentTimeMillis();
        System.out.println("cost time = " + (end - start));
        //Document document = new Document("roleId", 3)
        //        .append("roleName", "wang")
        //        .append("sex", 3);
        //storage.replace(collectionName, 1, document);
        //storage.travers(collectionName);
    }

    public static void testBatchRead() {
        long start = System.currentTimeMillis();
        Storage storage = new Storage();
        var roleIds = new ArrayList<Integer>();
        for (int i = 10000; i < 20000; i++) {
            roleIds.add(i);
        }
        ////storage.testBatchRead(roleIds);
        ////var roleIds2 = new ArrayList<Integer>();
        //for (int i = 31000; i < 32000; i++) {
        //    roleIds.add(i);
        //}
        ////storage.testBatchRead(roleIds2);
        //var roleIds3 = new ArrayList<Integer>();
        //for (int i = 32000; i < 33000; i++) {
        //    roleIds3.add(i);
        //}
        storage.testBatchRead(roleIds);
        long end = System.currentTimeMillis();
        System.out.println("cost time = " + (end - start));
    }

    public static void testReplace() {
        Storage storage = new Storage();
        String collectionName = "roleInfo1";
        Document document = new Document("roleId", 1)
            .append("roleName", "test")
            .append("sex", 3);
        storage.replaceUpsert(collectionName, 1, document);
        storage.travers(collectionName);
    }

    public static void testBatchInsert() {
        Storage storage = new Storage();
        long start = System.currentTimeMillis();
        for (long i = 10000; i < 30000; i++) {
            storage.testWrite(new RoleInfo(i, "xiong", 1));
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time = " + (end - start));
    }

    public static void testBatchInsert1() {
        Storage storage = new Storage();
        String collectionName = "roleInfo";
        long start = System.currentTimeMillis();
        for (long i = 0; i < 2; i++) {
            storage.testWrite(new RoleInfo(i, "xiong", 1));
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time = " + (end - start));
    }

}
