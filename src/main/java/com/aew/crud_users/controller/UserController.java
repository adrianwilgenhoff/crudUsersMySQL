package com.aew.crud_users.controller;

import java.util.List;

import com.aew.crud_users.errors.UserNotFoundException;
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
 * REST controller for managing users Controla las diferentes peticiones http
 * para operar con los usuarios tales como la creacion, obtencion, actualizacion
 * y eliminacion de usuarios.
 * 
 * @author Adrian E. Wilgenhoff
 */

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private static final String USER = "/user";
    private static final String USER_ORD = "/user/ord";
    private static final String USER_ID = "/user/{id}";

    @Autowired
    private UserService userService;

    /**
     * Devuelve una lista completa de todos los usuarios registrados em la base de
     * datos.
     * 
     * @return List<User> lista de los usuarios registrados.
     * @throws UsersNotFoundException
     */

    @RequestMapping(value = USER, method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() throws UsersNotFoundException {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            throw new UsersNotFoundException("No hay ningun usuario registrado");
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    /**
     * Devuelve una lista completa ordenada por apellido de todos los usuarios
     * registrados em la base de datos.
     * 
     * @return List<User> lista ordenada de los usuarios registrados.
     * @throws UsersNotFoundException
     */

    @RequestMapping(value = USER_ORD, method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsersOrdered() throws UsersNotFoundException {
        List<User> users = userService.findAllUserOrderedByLastname();
        if (users.isEmpty()) {
            throw new UsersNotFoundException("No hay ningun usuario registrado");
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    /**
     * Busca y devuelve un usuario a travez de su ID.
     * 
     * @param id valor requerido para busca un usuario.
     * @return User si existe devuelve un usuario.
     * @throws UserNotFoundException devuelve esta excepcion si no encuentra el
     *                               usuario para el id ingresada.
     */

    @RequestMapping(value = USER_ID, method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) throws UserNotFoundException {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException("No existe un usuario para el ID ingresado");
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Crea un nuevo usuario y lo guarda en su base de datos
     * 
     * @param user      usuario con sus datos en formato JSON
     * @param ucBuilder
     * @return
     */

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        // user.setId(5L);
        System.out.println(user.getId());
        if (userService.isUserExist(user)) {
            // El usuario ya existe
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}