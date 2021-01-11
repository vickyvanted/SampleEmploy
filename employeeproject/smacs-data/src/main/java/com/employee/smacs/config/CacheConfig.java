package com.employee.smacs.config;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	@Value("${SMACS_REDIS_CACHE_HOSTNAME}")
	private String hostname;
	
	@Value("${SMACS_REDIS_CACHE_PORT}")
	private int port;
	
	@Value("${SMACS_REDIS_CACHE_DURATION_INSEC}")
	private int cacheDuration;
	
	@Value("${SMACS_REDIS_CACHE_DURATION_LIVEDATA}")
	private int cacheLiveDuration;
	
	@Value("${SMACS_REDIS_CACHE_DURATION_REPORTS_INSEC}")
	private int cacheReportDuration;
	
	@Value("${SMACS_REDIS_CACHE_DURATION_COUNT_INSEC}")
	private int countCacheDuration;
	

	private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
	public static String siteKey="";
	
	
	static {
		try {

			FileReader reader = new FileReader(System.getProperty("CONFIG_FILE"));  
			Properties p = new Properties();  
			p.load(new FileInputStream(System.getProperty("CONFIG_FILE")));
			siteKey=p.getProperty("CLM_KEY");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

	    // Defaults
	    redisConnectionFactory.setHostName(hostname);
	    redisConnectionFactory.setPort(port);
	    
	    logger.debug("redisConnectionFactory {} ",redisConnectionFactory);
	    logger.debug("siteKey {} ",siteKey);
	    return redisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
    
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// redisTemplate.setHashValueSerializer(new LdapFailAwareRedisObjectSerializer());
		//    
		//    if (this.defaultRedisSerializer != null) {
		//    	redisTemplate.setDefaultSerializer(this.defaultRedisSerializer);
		//    }
    
	    redisTemplate.setConnectionFactory(cf);
	    
	    logger.debug("redisTemplate {} ",redisTemplate);
	    return redisTemplate;
	}

	@SuppressWarnings("rawtypes")
	@Bean(name ="cacheManager")
	@Primary
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
	    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
	
	    // Number of seconds before expiration. Defaults to unlimited (0)
	    cacheManager.setDefaultExpiration(cacheDuration);
	    //cacheManager.setDefaultExpiration(0);
	    logger.debug("cacheManager {} ",cacheManager);
	    return cacheManager;
	}
  
	@SuppressWarnings("rawtypes")
	@Bean(name ="liveCacheManager")
	public CacheManager liveCacheManager(RedisTemplate redisTemplate) {
	    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
	
	    // Number of seconds before expiration. Defaults to unlimited (0)
	    cacheManager.setDefaultExpiration(cacheLiveDuration);
	    //cacheManager.setDefaultExpiration(0);
	    logger.debug("cacheLiveManager {} ",cacheManager);
	    return cacheManager;
	}
  
	@SuppressWarnings("rawtypes")
	@Bean(name ="reportsCacheManager")
	public CacheManager reportsCacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(cacheReportDuration);
		// cacheManager.setDefaultExpiration(0);
		logger.debug("reportsCacheManager {} ",cacheManager);
		return cacheManager;
	}
  
	
	@SuppressWarnings("rawtypes")
	@Bean(name ="commonCountCacheManager")
	public CacheManager commonCountCacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(countCacheDuration);
		logger.debug("commonCountCacheManager {} ",cacheManager);
		return cacheManager;
	}
  
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object o, Method method, Object... objects) {
		        // This will generate a unique key of the class name, the method name,
		        // and all method parameters appended.
		        StringBuilder sb = new StringBuilder();
		        sb.append(o.getClass().getName());
		        sb.append(method.getName());
		        sb.append(siteKey);
		    	logger.debug("sb {} ",sb);
		        for (Object obj : objects) {
		          sb.append(obj.toString());
		        }
		        
		        return sb.toString();
			}
		};
	}
}
