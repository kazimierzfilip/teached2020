package com.teachedapp.repository;

import com.teachedapp.dao.Payment;
import com.teachedapp.dao.PaymentStatus;
import com.teachedapp.respository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void findAllWaitingForPayment() {
        List<Payment> paymentList = paymentRepository.findAllByStatus(PaymentStatus.WAITING_FOR_PAYMENT);
        assertThat(paymentList).extracting(Payment::getStatus).containsOnly(PaymentStatus.WAITING_FOR_PAYMENT);
    }
}
