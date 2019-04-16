package com.aew.crud_users.serviceImpl;

import java.util.List;

import com.aew.crud_users.model.User;
import com.aew.crud_users.repository.UserRepository;
import com.aew.crud_users.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users. Implements the UserService interface.
 * 
 * @author Adrian E. Wilgenhoff
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByNameAndLastname(String name, String lastname) {
        return userRepository.findByNameAndLastname(name, lastname);
    }

    @Override
    public List<User> findAllUserOrderedByLastname() {
        return userRepository.findAllUserOrderedByLastname();
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean isUserExist(User user) {
        return userRepository.existsById(user.getId());
    }

}