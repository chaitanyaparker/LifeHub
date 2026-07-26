package com.example.LifeHub.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class RedisTestController {

    private final StringRedisTemplate stringRedisTemplate;


    @GetMapping
    public String test() {
        return stringRedisTemplate.getConnectionFactory()
                .getConnection()
                .ping();
    }

    @GetMapping("/info")
    public Properties info() {
        return stringRedisTemplate.getConnectionFactory()
                .getConnection()
                .serverCommands()
                .info();
    }

    @GetMapping("/set")
    public String set() {
        try {
            stringRedisTemplate.opsForValue().set("hello", "world");
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}