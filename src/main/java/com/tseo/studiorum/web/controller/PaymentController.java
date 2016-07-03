package com.tseo.studiorum.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Payment;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.service.PaymentService;
import com.tseo.studiorum.service.StudentService;
import com.tseo.studiorum.web.dto.PaymentDTO;

@RestController
@RequestMapping(value = "api/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    StudentService studentService;
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PaymentDTO>> getPayments() {
        List<Payment> payments = paymentService.findaAll();
        List<PaymentDTO> paymentsDTO = new ArrayList<PaymentDTO>();
        for (Payment payment : payments) {
            paymentsDTO.add(new PaymentDTO(payment));
        }

        return new ResponseEntity<>(paymentsDTO, HttpStatus.OK);

    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {
        if (paymentDTO.getStudentDTO() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Student student = studentService.findOne(paymentDTO.getStudentDTO().getId());
        if (student == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Payment payment = new Payment();
        payment.setPurpose(paymentDTO.getPurpose());
        payment.setBankAcc(paymentDTO.getBankAcc());
        payment.setPrice(paymentDTO.getPrice());
        payment.setStudent(student);
        payment.setRecipient(paymentDTO.getRecipient());

        payment = paymentService.save(payment);
        student.getPayments().add(payment);
        studentService.save(student);
        return new ResponseEntity<>(new PaymentDTO(payment), HttpStatus.OK);

    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<PaymentDTO> updatePayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentService.findOne(paymentDTO.getId());
        if (payment == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        payment.setBankAcc(paymentDTO.getBankAcc());
        payment.setPrice(paymentDTO.getPrice());
        payment.setPurpose(paymentDTO.getPurpose());
        payment.setRecipient(paymentDTO.getRecipient());
        
        paymentService.save(payment);
        return new ResponseEntity<>(new PaymentDTO(payment), HttpStatus.OK);

    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        Payment payment = paymentService.findOne(id);
        
        if (payment == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
        	Student student = payment.getStudent();
        	student.getPayments().remove(payment);
        	studentService.save(student);
            paymentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }
    }


}
