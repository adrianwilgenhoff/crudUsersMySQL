package com.aew.crud_users.service;

import java.util.List;

import com.aew.crud_users.model.User;

/**
 * Service interface for managing users. Defines operations that every
 * UserServiceImpl should implement.
 *
 * @author Adrian E. Wilgenhoff
 */
public interface UserService {

    /**
     * Get the "id" user.
     * 
     * @param id id of the user to find.
     * @return user
     */
    User findById(long id);

    /**
     * Get the "email" user.
     * 
     * @param email email of the user to find.
     * @return user
     */
    User findByEmail(String email);

    /**
     * Get the "username" user.
     * 
     * @param username username of the user to find.
     * @return user
     */
    User findByUsername(String username);

    /**
     * Returns a user that has a specific first name y lastname.
     * 
     * @param name     name of the user to find.
     * @param lastname lastname of the user to find.
     * @return List<User>
     */
    User findByNameAndLastname(String name, String lastname);

    /**
     * Get a list ordered users.
     * 
     * @return user
     */
    List<User> findAllUserOrderedByLastname();

    /**
     * Get a list all user.
     * 
     * @return List<User>
     */
    List<User> findAllUsers();

    /**
     * Save a new User.
     *
     * @param user user that will be added.
     */
    void saveUser(User user);

    /**
     * Delete a specific User from database.
     * 
     * @param id id of the user to delete.
     */
    void deleteUserById(long id);

    /**
     * Update all information for a specific user.
     *
     * @param user user to update.
     */
    void updateUser(User user);

    /**
     * Find a specific User.
     * 
     * @param user user to find.
     * @return Return True if exist a user or false.
     */
    public boolean isUserExist(User user);
}