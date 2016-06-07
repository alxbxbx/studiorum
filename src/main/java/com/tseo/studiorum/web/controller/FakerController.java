package com.tseo.studiorum.web.controller;

import com.github.javafaker.Faker;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.User;
import com.tseo.studiorum.service.StudentService;
import com.tseo.studiorum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "api/faker")
public class FakerController {

  @Autowired
  UserService userService;

  @Autowired
  StudentService studentService;

  @RequestMapping(method = RequestMethod.GET)
  public void getImport() {

    this.cleanAll();

    Faker faker = new Faker();

    for (int i = 0; i < 250; i++) {
      User user = new User();
      user.setLastName(faker.name().lastName());
      user.setName(faker.name().firstName());
      user.setPassword("user" + i);
      user.setRole("user");
      user.setUserName("user" + i);
      user = userService.save(user);
    }

    for (int i = 0; i < 250; i++) {
      Student student = new Student();
      student.setGender("male");
      student.setAddress(faker.address().streetAddress() + ", " + faker.address().city());
      student.setDateOfBirth(new Date());
      student.setJMBG(faker.idNumber().valid());
      student.setStudentId("RS" + i + "/2016");
      student.setName(faker.name().firstName());
      student.setLastName(faker.name().lastName());
      student.setRole("student");
      student.setPassword("student" + i);
      student.setUserName("student" + i);
      studentService.save(student);
    }

  }

  private void cleanAll() {

  }

}
