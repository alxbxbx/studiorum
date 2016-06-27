package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Payment;
import com.tseo.studiorum.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment findOne(Integer id) {
        return paymentRepository.findOne(id);
    }

    public List<Payment> findaAll() {
        return paymentRepository.findAll();
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void remove(Integer id) {
        paymentRepository.delete(id);
    }

}
