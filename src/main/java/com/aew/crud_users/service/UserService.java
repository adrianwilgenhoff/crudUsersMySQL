package com.aew.crud_users.service;

import java.util.List;

import com.aew.crud_users.model.User;

/**
 * Service interface for managing users.
 *
 * @author Adrian E. Wilgenhoff
 */

public interface UserService {

    User findById(long id);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByNameAndLastname(String name, String lastname);

    List<User> findAllUserOrderedByLastname();

    List<User> findAllUsers();

    void saveUser(User user);

    void deleteUserById(long id);

    void updateUser(User user);

    public boolean isUserExist(User user);
}