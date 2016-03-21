package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
