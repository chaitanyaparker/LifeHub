package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Entity.Vault;
import com.example.LifeHub.Enums.VaultCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

    List<Vault> findByUser(Long user);

}
