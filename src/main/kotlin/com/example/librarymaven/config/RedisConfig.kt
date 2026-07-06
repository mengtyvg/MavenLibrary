package com.example.librarymaven.config

import com.example.librarymaven.dto.BookPageResponseDTO
import com.example.librarymaven.dto.BookResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.ApplicationRunner
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration

@Configuration
class RedisConfig {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        val objectMapper = ObjectMapper().registerKotlinModule()
        val typeFactory = objectMapper.typeFactory

        val bookSerializer = Jackson2JsonRedisSerializer(objectMapper, BookResponseDTO::class.java)
        val bookPageSerializer = Jackson2JsonRedisSerializer(objectMapper, BookPageResponseDTO::class.java)
        val bookListSerializer = Jackson2JsonRedisSerializer<List<BookResponseDTO>>(
            objectMapper,
            typeFactory.constructCollectionType(List::class.java, BookResponseDTO::class.java)
        )

        val config = RedisCacheConfiguration.defaultCacheConfig()
            .prefixCacheNameWith("librarymaven:v3:")
            .entryTtl(Duration.ofMinutes(10))

        val cacheConfigurations = mapOf(
            "books" to config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(bookSerializer)
            ),
            "booksList" to config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(bookListSerializer)
            ),
            "booksPagination" to config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(bookPageSerializer)
            )
        )

        val defaultConfig = config
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(bookSerializer)
            )

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultConfig)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build()
    }

    @Bean
    fun removeOldCacheEntries(redisTemplate: StringRedisTemplate): ApplicationRunner {
        return ApplicationRunner {
            listOf(
                "books::*",
                "booksList::*",
                "booksPagination::*",
                "librarymaven:v2:books::*",
                "librarymaven:v2:booksList::*",
                "librarymaven:v2:booksPagination::*"
            ).forEach { pattern ->
                val keys = redisTemplate.keys(pattern)
                if (!keys.isNullOrEmpty()) {
                    redisTemplate.delete(keys)
                }
            }
        }
    }
}
