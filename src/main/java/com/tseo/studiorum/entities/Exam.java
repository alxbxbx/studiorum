package com.tseo.studiorum.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
public class Exam {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer points;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn(name = "DUTY_ID")
    private Duty duty;

    private Boolean pass;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Student student;

    public Exam() {
    }

    public Exam(Integer id, Integer points, Duty duty, Boolean pass, Student student) {
        super();
        this.id = id;
        this.points = points;
        this.duty = duty;
        this.pass = pass;
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Duty getDuty() {
        return duty;
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }

}
