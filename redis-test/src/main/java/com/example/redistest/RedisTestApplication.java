package com.example.redistest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class RedisTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(RedisTestApplication.class, args);

//		ConfigurableApplicationContext application1 = new SpringApplicationBuilder(RedisTestApplication.class).run(args);
		Environment env = application.getEnvironment();
		String host = env.getProperty("redis.host");
//		redisConnect(host);
//		redisString(host);
//		redisList(host);
		getRedisKeys(host);
	}

	// 连接
	public static Jedis redisConnect(String host) {
		System.out.println("redis - host:"+host);
		Jedis jedis = new Jedis(host);
		System.out.println("Connection to server sucessfully");
		System.out.println("Server is running: "+jedis.ping());
		return jedis;
	}

	// 字符串示例
	public static void redisString(String host) {
		Jedis jedis = redisConnect(host);
		jedis.set("name","danwei");
		String result = jedis.get("name");
		System.out.println( String.format("redis String 输出结果：{0}：{1}", "name", result));
	}

	/**
	 * 列表
	 * @param host
	 */
	public static void redisList(String host){
		Jedis jedis = redisConnect(host);
		jedis.lpush("slist","xiaomi");
		jedis.lpush("slist","huawei");
		jedis.lpush("slist","oppo");
		List<String> sList = jedis.lrange("slist",0,2);
		System.out.println("列表为"+sList);
	}

	public static void getRedisKeys(String host) {
		Jedis jedis = redisConnect(host);
		// 获取数据并输出
		Set<String> keys = jedis.keys("*");
		Iterator<String> it=keys.iterator() ;
		while(it.hasNext()){
			String key = it.next();
			System.out.println(key);
		}
	}

}
