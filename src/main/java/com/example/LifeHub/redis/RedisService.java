package com.example.LifeHub.redis;

import com.example.LifeHub.DTO.Redis.TemporaryUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, TemporaryUserDTO> redisTemplate;

    public void saveTemporaryUser(TemporaryUserDTO temp) {

        String key = "REGISTER:" + temp.getEmail();

        redisTemplate.opsForValue().set(
                key,
                temp,
                Duration.ofMinutes(15)
        );
    }

    public TemporaryUserDTO getTemporaryUser(String email) {

        String key = "REGISTER:" + email;

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return (TemporaryUserDTO) value;
    }

    public void deleteTemporaryUser(String email) {

        redisTemplate.delete("REGISTER:" + email);
    }
}