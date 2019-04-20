package com.aew.crud_users.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.aew.crud_users.exception.UserNotFoundException;
import com.aew.crud_users.model.User;
import com.aew.crud_users.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTest {

        @InjectMocks
        private UserController userController;

        @Mock
        private UserService userService;

        private List<User> users;
        private User luz;
        private User adrian;

        @Before
        public void setUp() throws Exception {

                MockitoAnnotations.initMocks(this);
                adrian = User.builder().name("adrian").lastname("wilgenhoff").address("misiones 776").city("tandil")
                                .dni(33356953L).email("adrianwilgenhoff@gmail.com").password("password")
                                .telephone("2494655632").username("usernameA").id(1L).build();
                luz = User.builder().name("maria luz").lastname("gomez").address("jbjusto 695").city("tandil")
                                .dni(12345678L).email("marialuz@gmail.com").password("password").telephone("2494984512")
                                .username("usernameL").id(2L).build();
                users = Arrays.asList(adrian, luz);
        }

        @Test
        public void testGetUser() throws UserNotFoundException {

                when(userService.findById(anyLong())).thenReturn(adrian);
                ResponseEntity<User> response = userController.getUser(1L);
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(adrian, response.getBody());
                assertEquals(adrian.getId(), response.getBody().getId());
                verify(userService).findById(1L);

        }

        @Test
        public void testListAllUsers() throws Exception {

                when(userService.findAllUsers()).thenReturn(users);
                ResponseEntity<List<User>> response = userController.listAllUsers();
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(2, response.getBody().size());
                verify(userService).findAllUsers();
        }

        @Test
        public void testListAllUsersOrdered() {

        }

        @Test
        public void testCreateUser() throws Exception {

                User userWithoutId = User.builder().name("adrian").lastname("wilgenhoff").address("misiones 776")
                                .city("tandil").dni(33356953L).email("adrianwilgenhoff@gmail.com").password("password")
                                .telephone("2494655632").username("usernameA").build();
                ResponseEntity<Void> response = userController.createUser(userWithoutId);
                assertNotNull(response);
                assertNotNull(response.getHeaders());
                assertEquals(response.getStatusCode(), HttpStatus.CREATED);
                verify(userService).saveUser(userWithoutId);

        }

        @Test
        public void testDeleteUser() throws UserNotFoundException {

                when(userService.findByUsername(anyString())).thenReturn(adrian);
                userController.deleteUser(adrian.getUsername());
                verify(userService).deleteUserById(adrian.getId());
        }

        @Test
        public void testUpdateUser() throws UserNotFoundException {

                when(userService.findByUsername(any(String.class))).thenReturn(adrian);
                ResponseEntity<String> response = userController.updateUser("usernameA", adrian);
                assertNotNull(response);
                assertEquals(response.getStatusCode(), HttpStatus.OK);
                assertEquals(response.getBody(), "A user is updated");
                verify(userService).updateUser(adrian);
        }

}