package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.Bills;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.BillsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillsRepository extends JpaRepository<Bills, Long> {

    List<Bills> findByUser(User user);

    List<Bills> findByUserAndBillsStatus(User user, BillsStatus billsStatus);

}