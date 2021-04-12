package redistest;

import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.Jedis;

public class Test {

  public static void main(String[] args) {
    RedisConfig config = new RedisConfig("10.227.18.178", 6379);
    Jedis connect = config.getCon();
    // System.out.println(connect.get("xiong"));
    // testMap(connect);
    // testList(connect);
    // testSortedSet(connect);
    //testScript(connect)
      connect.set("test", "xiong");
  }

  public static void testMap(Jedis jedis) {
    jedis.hset("users", "xiong", "0;0;0");
    jedis.hset("users", "xiong", "1;1;1"); //如果对应的field已经存在，那么执行更新操作
    System.out.println(jedis.hget("users", "xiong"));
    jedis.hsetnx("users", "jie", "0;0;0");
    jedis.hsetnx("users", "jie", "1;1;1"); //如果对应的field已经，存在，那么直接返回
    System.out.println(jedis.hget("users", "jie"));
  }

  public static void testSet(Jedis jedis) {

  }

  public static void testList(Jedis jedis) {
    // jedis.rpush("list", "aaa");
    // jedis.lpush("list", "bbb");
    System.out.println(jedis.lrange("list", 0, -1)); //获取list的所有值
  }

  public static void testSortedSet(Jedis jedis) {
    // jedis.zadd("rank", 1, "xiong");
    // jedis.zadd("rank", 10, "jie");
    // jedis.zadd("rank", 5, "qing");
    System.out.println(jedis.zrange("rank", 0, -1));
    System.out.println(jedis.zrank("rank", "xiong"));
    System.out.println(jedis.zcard("rank")); //返回数量
    System.out.println(jedis.zrevrangeWithScores("rank", 0, 0));
    System.out.println(jedis.zrangeWithScores("rank", 0, 0));

  }

  public static void testScript(Jedis jedis) {
    // String script = "local old = redis.call('get', KEYS[1]); if old == nil then redis.call('set', KEYS[1], ARGV[1]); "
    //     + "return nil else return old end";
    // List<String> keys = new ArrayList<>();
    // keys.add("scriptKey");
    // List<String> values = new ArrayList<>();
    // values.add("scriptValue");
    // System.out.println(jedis.eval(script, keys, values));
    System.out.println(jedis.scriptLoad("return KEYS[1]")); //sha 4a2267357833227dd98abdedb8cf24b15a986445
    // System.out.println(jedis.evalsha("4a2267357833227dd98abdedb8cf24b15a986445", 1, "xiong"));
  }

}
