package com.smart.sso.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


//exclude = { RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class }
/**
* 启动类
* @author:dongjing.chen
* @Description:
* @Company:ctg.cn
* @date:2022年11月29日
 */
@EnableScheduling
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 7200, redisFlushMode = RedisFlushMode.ON_SAVE, redisNamespace = "smart-sso")
@SpringBootApplication(scanBasePackages = {"com.smart.sso.server.*","com.smart.sso.client.*"})
@MapperScan("com.smart.sso.server.dao")
@EnableCaching
public class SmartSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSsoServerApplication.class, args);
    }
    
  
}
