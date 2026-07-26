package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.LoginActivity;
import com.example.LifeHub.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {


    List<LoginActivity> findByUserOrderByLoginAtDesc(User user);
}
