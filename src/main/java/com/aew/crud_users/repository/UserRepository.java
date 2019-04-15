package com.aew.crud_users.repository;

import java.util.List;

import com.aew.crud_users.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the User entity.
 * 
 * @author Adrian E. Wilgenhoff
 * 
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    public User findByEmail(String email);

    public User findByUsername(String username);

    public User findByNameAndLastname(String name, String lastname);

    @Query("SELECT u FROM User u ORDER BY lastname")
    public List<User> findAllUserOrderedByLastname();

}