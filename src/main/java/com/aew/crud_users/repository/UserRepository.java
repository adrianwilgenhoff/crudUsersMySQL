package com.aew.crud_users.repository;

import java.util.List;

import com.aew.crud_users.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the User entity. Also defines the operations
 * that every UserService should implement.
 * 
 * @author Adrian E. Wilgenhoff
 * 
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Get the "id" user.
     * 
     * @param id the id of the user to find
     * @return User
     */
    User findById(long id);

    /**
     * Get the "email" user.
     * 
     * @param email the email of the user to find
     * @return User
     */
    public User findByEmail(String email);

    /**
     * Get the "username" user.
     * 
     * @param username the username of the user to find
     * @return User
     */
    public User findByUsername(String username);

    /**
     * Get the "name and lastname" user.
     * 
     * @param name     the name of the user to find
     * @param lastname the lastname of the user to find
     * @return User
     */
    public User findByNameAndLastname(String name, String lastname);

    /**
     * Get a list of user.
     * 
     * @return List<User> Return a list ordered of users.
     */
    @Query("SELECT u FROM User u ORDER BY lastname")
    public List<User> findAllUserOrderedByLastname();

}