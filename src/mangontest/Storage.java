package mangontest;


import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOptions;
import java.util.*;
import org.bson.Document;

/*
 如果json格式如下
  {
   "name" : "MongoDB",
   "type" : "database",
   "count" : 1,
   "versions": [ "v3.2", "v3.0", "v2.6" ],
   "info" : { x : 203, y : 102 }
  }
  对应的document为
   Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
 */

/**
 * BsonDocument是底层数据存储接口，我们常用的Document也是实现了Bson接口
 */

public class Storage {
    private MongoClient client;
    private MongoDatabase database;
    private Map<String, MongoCollection<Document>> collectionMap = new HashMap<>();

    public Storage() {
        //按host和port连接
        //this.client = MongoClients.create("mongodb://10.227.18.178:8017,10.227.18.178:8016,10.227.18.178:8015/test?replicaset=rstest");
        this.client = MongoClients.create();
        this.database = client.getDatabase("xiongDatabase");
    }

    private MongoCollection<Document> getCollection(String collectionName) {
        var result = collectionMap.get(collectionName);
        if (result == null) {
            //如果不存在，会自动创建 创建的时候带一些参数，可以指定创建的collection的内容
            result = this.database.getCollection(collectionName);
            collectionMap.put(collectionName, result);
        }
        return result;
    }

    public void count(String collectionName) {
        System.out.println(collectionName + " num is " + getCollection(collectionName).countDocuments());
    }

    public void travers(String collectionName) {
        MongoCursor<Document> cursor = getCollection(collectionName).find().batchSize(10000).cursor();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    public Document queryByRoleId(String collectionName, int key) {
        return getCollection(collectionName).find(Filters.eq("roleId", key)).first();
    }

    public void deleteOne(String collectionName, int key) {
        getCollection(collectionName).deleteOne(Filters.eq("roleId", key));
    }

    //替换
    public void replace(String collectionName, int key, Document value) {
        getCollection(collectionName).replaceOne(Filters.eq("roleId", key), value);
    }

    private static final ReplaceOptions REPLACE_OPTIONS = new ReplaceOptions().upsert(true);


    public void replaceUpsert(String collectionName, int key, Document value) {
        getCollection(collectionName).replaceOne(Filters.eq("_id", key), value, REPLACE_OPTIONS);
    }

    public void createIndex(String collectionName) {
        //创建索引可以极大的提高查询效率
        //btree
        //使用 db.collectionName.find().explain()可以查看索引使用情況
        getCollection(collectionName).createIndex(Indexes.ascending("roleId"));
    }

    public void testWrite(RoleInfo roleInfo) {
        Document document = new Document("_id", roleInfo.roleId)
            .append("roleName", roleInfo.roleName)
            .append("sex", roleInfo.sex);
        getCollection("roleInfo").insertOne(document);
    }


    public void testRead(int roleId) {
        Document doc = queryByRoleId("roleInfo", roleId);
        if (doc == null) {
            System.out.println("roleid = " + roleId + " is null");
            return;
        }
        RoleInfo roleInfo = new RoleInfo(doc.getLong("roleId"), doc.getString("roleName"), doc.getInteger("sex"));
        System.out.println(roleInfo.toString());
    }

    public void testBatchRead(List<Integer> roleIds) {
        var collection = getCollection("roleInfo");
        var cursor = collection.find(Filters.in("_id", roleIds)).cursor();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
                //cursor.next().toJson();
            }
        } finally {
            cursor.close();
        }
    }

}
