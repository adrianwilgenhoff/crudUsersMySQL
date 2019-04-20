package com.aew.crud_users.controller;

import java.util.List;

import com.aew.crud_users.exception.BadRequestException;
import com.aew.crud_users.exception.EmailAlreadyUsedException;
import com.aew.crud_users.exception.UserNotFoundException;
import com.aew.crud_users.exception.UsernameAlreadyUsedException;
import com.aew.crud_users.exception.UsersNotFoundException;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing users. Contains information and definition about
 * our Users rest calls.
 * 
 * @author Adrian E. Wilgenhoff
 */

@RestController
@RequestMapping(value = "/api/v1")
@Api(value = "crudUsers", description = "Operations pertaining to users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * GET /user : Retrieve all Users.
     * 
     * @return the ResponseEntity with status 200 (OK) and with body all users.
     * @throws UsersNotFoundException 204 (No Content) if the database is empty.
     */
    @ApiOperation(value = "View a list of users", notes = "Provides an operation to return all users")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No content to show", response = String.class) })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() throws UsersNotFoundException {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            throw new UsersNotFoundException();
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
    @ApiOperation(value = "View a list of ordered users")
    @RequestMapping(value = "/users/sort", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsersOrdered() throws UsersNotFoundException {
        List<User> users = userService.findAllUserOrderedByLastname();
        if (users.isEmpty()) {
            throw new UsersNotFoundException();
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    /**
     * GET /users/:id : get user by id.
     *
     * @param id the user's id to find
     * @return the ResponseEntity with status 200 (OK) and with body the "id" user,
     * @throws UserNotFoundException 404 (Not Found) if the user can not be found.
     */
    @ApiOperation(value = "Search a user with an ID", response = User.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved User", response = User.class),
            @ApiResponse(code = 204, message = "User not found", response = String.class) })

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) throws UserNotFoundException {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * POST /user : Creates a new user. Creates a new user if the mail and username
     * are not already used and will have the auto-generated id.
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
    @ApiOperation(value = "Add a User")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user)
            throws BadRequestException, EmailAlreadyUsedException, UsernameAlreadyUsedException {

        if (user.getId() != null) {
            throw new BadRequestException();
        } else if (userService.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyUsedException();
        } else if (userService.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyUsedException();
        } else {
            userService.saveUser(user);
            UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }

    }

    /**
     * DELETE /user/:username delete the "username" user.
     * 
     * @param username the username of the user to delete
     * @return the ResponseEntity with status 204 (No Content).
     * @throws UserNotFoundException 404 (Not Found) if not found.
     */
    @ApiOperation(value = "Delete a User")
    @RequestMapping(value = "users/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        userService.deleteUserById(user.getId());
        LOG.info("Se ha borrado el usuario con username = " + username);
        return new ResponseEntity<>("User Deleted", HttpStatus.NO_CONTENT);
    }

    /**
     * PUT /user/:username : Updates a user. User must exist for id.
     * 
     * @param username the username of the user to update
     * @param user     the user to update
     * @return the ResponseEntity with status 200 (OK)
     * @throws UserNotFoundException 404 (Not Found) if the user can not be found.
     */

    @ApiOperation(value = "Update a User")
    @RequestMapping(value = "users/{username}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody User user)
            throws UserNotFoundException {

        User currentUser = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
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