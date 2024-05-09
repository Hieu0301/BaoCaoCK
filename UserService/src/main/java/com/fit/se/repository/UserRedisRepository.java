package com.fit.se.repository;

import com.fit.se.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRedisRepository {

    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;

    @Autowired
    public UserRedisRepository(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    public void saveUser(User user) {
        hashOperations.put("USER", user.getId(), user);
    }

    public List<User> findAll() {
        return hashOperations.values("USER");
    }

    //@Cacheable(value = "users", key = "#id")
    public User findById(int id) {
        return (User) hashOperations.get("USER", id);
    }

    public void update(User user) {
        hashOperations.put("USER", user.getId(), user);
    }

    public void deleteById(Integer id) {
        hashOperations.delete("USER", id);
    }

}
