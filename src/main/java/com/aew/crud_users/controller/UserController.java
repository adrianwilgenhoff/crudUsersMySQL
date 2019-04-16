package com.aew.crud_users.controller;

import java.util.List;

import com.aew.crud_users.errors.BadRequestException;
import com.aew.crud_users.errors.EmailAlreadyUsedException;
import com.aew.crud_users.errors.UserNotFoundException;
import com.aew.crud_users.errors.UsernameAlreadyUsedException;
import com.aew.crud_users.errors.UsersNotFoundException;
import com.aew.crud_users.model.User;
import com.aew.crud_users.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * REST controller for managing users. Contains information and definition about
 * our Users rest calls.
 * 
 * @author Adrian E. Wilgenhoff
 */

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    private static final String USER_USERNAME = "/user/{username}";
    private static final String USER = "/user";
    private static final String USER_ORD = "/user/ord";
    private static final String USER_ID = "/user/{id}";

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * GET /user : Retrieve all Users. Return a list of the all users registered in
     * database.
     * 
     * @return the ResponseEntity with status 200 (OK) and with body all users.
     * @throws UsersNotFoundException 204 (No Content) if the database is empty.
     */
    @RequestMapping(value = USER, method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() throws UsersNotFoundException {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            throw new UsersNotFoundException("There are not any user registered");
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    /**
     * GET /user/ord : Retrieve all Users ordered. Return a list of the all users
     * registered in database ordered by <code>lastname<code>.
     * 
     * @return the ResponseEntity with status 200 (OK) and with body all users
     *         ordered.
     * @throws UsersNotFoundException 204 (No Content) if the database is empty.
     */
    @RequestMapping(value = USER_ORD, method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsersOrdered() throws UsersNotFoundException {
        List<User> users = userService.findAllUserOrderedByLastname();
        if (users.isEmpty()) {
            throw new UsersNotFoundException("There are not any user registered");
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    /**
     * GET /users/:id : get the "id" user.
     *
     * @param id the id of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "id" user,
     * @throws UserNotFoundException 404 (Not Found) if the user can not be found.
     */
    @RequestMapping(value = USER_ID, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) throws UserNotFoundException {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found for this id");
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * POST /user : Creates a new user. Creates a new user if the mail and username
     * are not already used.
     * 
     * @param user      the user to create
     * @param ucBuilder
     * @return the ResponseEntity with status 201 (Created) and the path new user.
     * @throws BadRequestException          400 (Bad Request) if the new user
     *                                      content an id.
     * @throws EmailAlreadyUsedException    409 (Conflict) if the email is already
     *                                      in use.
     * @throws UsernameAlreadyUsedException 409 (Conflict) if the username is
     *                                      already in use.
     */
    @RequestMapping(value = USER, method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder)
            throws BadRequestException, EmailAlreadyUsedException, UsernameAlreadyUsedException {

        if (user.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID");
            // Lowercase the user login before comparing with database
        } else if (userService.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyUsedException("Email is already in use!");
        } else if (userService.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyUsedException("Username is already in use!");
        } else {
            userService.saveUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }

    }

    /**
     * DELETE /user/:username delete the "username" user.
     * 
     * @param username the username of the user to delete
     * @return the ResponseEntity with status 204 (No Content).
     * @throws UserNotFoundException 404 (Not Found) if the user can not be found.
     */
    @RequestMapping(value = USER_USERNAME, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Username not found!");
        }
        userService.deleteUserById(user.getId());
        LOG.info("Se ha borrado el usuario con username = " + username);
        return new ResponseEntity<>("User Deleted", HttpStatus.NO_CONTENT);
    }

    /**
     * PUT /user/:username : Updates an existing User.
     * 
     * @param username the username of the user to update
     * @param user     the user to update
     * @return the ResponseEntity with status 200 (OK)
     * @throws UserNotFoundException 404 (Not Found) if the user can not be found.
     */
    @RequestMapping(value = USER_USERNAME, method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody User user)
            throws UserNotFoundException {

        User currentUser = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Username not found!");
        }
        currentUser.setAddress(user.getAddress());
        currentUser.setPassword(user.getPassword());
        currentUser.setTelephone(user.getTelephone());
        currentUser.setCity(user.getCity());
        currentUser.setName(user.getName());
        currentUser.setLastname(user.getLastname());
        currentUser.setDni(user.getDni());
        currentUser.setEmail(user.getEmail());
        userService.updateUser(currentUser);
        return new ResponseEntity<>("A user is updated", HttpStatus.OK);
    }

}