package cn.e3mall.Jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	@Test
	public void testJedis() throws Exception {
		// 第一步：创建一个Jedis对象，需要指定服务端的host和port
		Jedis jedis = new Jedis("192.168.70.81", 6379);
		// 第二步：使用Jedis操作数据库，每个redis命令对应一个方法
		String key = "test123";
		jedis.set(key, "my first Jedis test");
		String value = jedis.get(key);
		System.out.println(value);
		// 第三部：关闭Jedis
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception {
		// 第一步：创建一个JedisPool对象。需要指定服务端的ip及端口。
		JedisPool jedisPool = new JedisPool("192.168.70.81", 6379);
		// 第二步：从JedisPool中获得Jedis对象。
		Jedis jedis = jedisPool.getResource();
		// 第三步：使用Jedis操作redis服务器。
		String key = "test123";
		jedis.set(key, "my second Jedis test");
		String value = jedis.get(key);
		System.out.println(value);
		// 第四步：操作完毕后关闭jedis对象，连接池回收资源。
		jedis.close();
		// 第五步：关闭JedisPool对象。
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster() throws Exception {
		// 第一步：使用JedisCluster对象。需要一个Set<HostAndPort>参数。Redis节点的列表。
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.70.81", 7001));
		nodes.add(new HostAndPort("192.168.70.81", 7002));
		nodes.add(new HostAndPort("192.168.70.81", 7003));
		nodes.add(new HostAndPort("192.168.70.81", 7004));
		nodes.add(new HostAndPort("192.168.70.81", 7005));
		nodes.add(new HostAndPort("192.168.70.81", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 第二步：直接使用JedisCluster对象操作redis。在系统中单例存在。
		jedisCluster.set("hello", "100");
		String result = jedisCluster.get("hello");
		// 第三步：打印结果
		System.out.println(result);
		// 第四步：系统关闭前，关闭JedisCluster对象。
		jedisCluster.close();
	}
	
	@Test
	public void testJedisClient() throws Exception {
		//初始化Spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//从容器中获得JedisClient对象
		JedisClient jedisClient = ac.getBean(JedisClient.class);
		jedisClient.set("first", "200");
		String result = jedisClient.get("first");
		System.out.println(result);

	}
}
