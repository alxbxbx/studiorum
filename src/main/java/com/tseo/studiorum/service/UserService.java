package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.User;
import com.tseo.studiorum.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findOneByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User findOne(Integer id) {
        return userRepository.findOne(id);

    }

    public List<User> findAll() {
        return userRepository.findAll();

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void remove(Integer id) {
        userRepository.delete(id);
    }
    
}
