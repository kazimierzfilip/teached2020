package com.teachedapp.respository;

import com.teachedapp.dao.Payment;
import com.teachedapp.dao.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByStatus(PaymentStatus paymentStatus);
}