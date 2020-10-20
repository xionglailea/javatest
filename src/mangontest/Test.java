package mangontest;

import org.bson.Document;

public class Test {
  public static void main(String[] args) {
    testBatchInsert1();
    //testReplace();
  }

  public static void test1() {
    Storage storage = new Storage();
//    storage.testWrite(new RoleInfo(1, "xiong", 1));
//    storage.testWrite(new RoleInfo(2, "jieqing", 2));
    String collectionName = "roleInfo";
    storage.count(collectionName);
    storage.testRead(1);
    storage.testRead(2);
    storage.deleteOne(collectionName, 1);
    storage.deleteOne(collectionName, 2);
    storage.count(collectionName);
    storage.testRead(1);
    storage.testRead(2);
  }

  public static void testTravers() {
    Storage storage = new Storage();
//    storage.testWrite(new RoleInfo(1, "xiong", 1));
//    storage.testWrite(new RoleInfo(2, "jieqing", 2));
    String collectionName = "roleInfo";
    storage.travers(collectionName);
    Document document = new Document("roleId", 3)
            .append("roleName", "wang")
            .append("sex", 3);
    storage.replace(collectionName, 1, document);
    storage.travers(collectionName);
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
    String collectionName = "roleInfo";
    long start = System.currentTimeMillis();
    for (long i = 4; i < 10004; i++) {
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
