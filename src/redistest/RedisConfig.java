package redistest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig {

  private String ip;
  private int port;
  private JedisPoolConfig jedisPoolConfig;
  private JedisPool pool;

  public RedisConfig(String ip, int port) {
    this.ip = ip;
    this.port = port;
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(20);
    jedisPoolConfig.setMaxIdle(10);
    jedisPoolConfig.setMinIdle(1);
    jedisPoolConfig.setMaxWaitMillis(500);
    pool = new JedisPool(jedisPoolConfig, ip);
  }

  public Jedis getCon() {
    return pool.getResource();
  }

}
