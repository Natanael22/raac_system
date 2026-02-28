package br.com.raac_system.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
                RedisTemplate<String, Object> template = new RedisTemplate<>();
                template.setConnectionFactory(connectionFactory);

                GenericJackson2JsonRedisSerializer jsonSerializer = GenericJackson2JsonRedisSerializer.builder()
                                .build();

                // Serializators
                template.setKeySerializer(new StringRedisSerializer());
                template.setHashKeySerializer(new StringRedisSerializer());
                template.setValueSerializer(jsonSerializer);
                template.setHashValueSerializer(jsonSerializer);

                return template;
        }

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
                GenericJackson2JsonRedisSerializer jsonSerializer = GenericJackson2JsonRedisSerializer.builder()
                                .build();

                RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(60)) // Default TTL
                                .serializeKeysWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                                .fromSerializer(jsonSerializer));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(config)
                                .build();
        }
}
