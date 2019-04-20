package com.aew.crud_users.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.aew.crud_users.exception.UserNotFoundException;
import com.aew.crud_users.exception.UsersNotFoundException;
import com.aew.crud_users.model.User;
import com.aew.crud_users.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerIT {

        // Un test de intregracion seria si logro hacer que antes de cada test guardo
        // los datos en la BD y luego de cada test borro esos datos. Solo usaria el
        // mockMvc y controlaria. No usaria Mock ni InjectMocks.

        @InjectMocks
        private UserController userController;

        @Mock
        private UserService userServiceMock;

        private MockMvc mockMvc;

        private List<User> users;
        private User luz;
        private User adrian;

        @Before
        public void setUp() throws Exception {

                mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
                adrian = User.builder().name("adrian").lastname("wilgenhoff").address("misiones 776").city("tandil")
                                .dni(33356953L).email("adrianwilgenhoff@gmail.com").password("password")
                                .telephone("2494655632").username("usernameA").id(1L).build();
                luz = User.builder().name("maria luz").lastname("gomez").address("jbjusto 695").city("tandil")
                                .dni(12345678L).email("marialuz@gmail.com").password("password").telephone("2494984512")
                                .username("usernameL").id(2L).build();
                users = Arrays.asList(adrian, luz);

                // save user en la database.
                // userRepository.save(adrian);
                // userRepository.save(luz);

        }

        @After
        public void tearDown() {
                // delete all the test data created from the database
                // tal vez lo pueda hacer con @Rollback
                // userRepository.deleteById(adrian.getId());
                // userRepository.deleteById(adrian.getId());
        }

        @Test
        public void testGetAllUsers() throws Exception {
                when(userServiceMock.findAllUsers()).thenReturn(users);
                mockMvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name", is("adrian")))
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[1].name", is("maria luz")));

                verify(userServiceMock, times(1)).findAllUsers();
                verifyNoMoreInteractions(userServiceMock);
        }

        @Test(expected = UsersNotFoundException.class)
        public void whenNonExistingUserShouldReturnUsersNotFound() throws Exception {

                when(userController.listAllUsers()).thenThrow(UsersNotFoundException.class);
                mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testGetById() throws Exception {
                when(userServiceMock.findById(luz.getId())).thenReturn(luz);
                mockMvc.perform(get("/api/v1/users/{id}", luz.getId()).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(jsonPath("$.id", is(2))).andExpect(jsonPath("$.name", is("maria luz")));

                verify(userServiceMock, times(1)).findById(luz.getId());
                verifyNoMoreInteractions(userServiceMock);
        }

        @Test(expected = UserNotFoundException.class)
        public void whenNonExistingUserByIdShouldNotFound() throws Exception {

                when(userController.getUser(anyLong())).thenThrow(UserNotFoundException.class);
                // 1st forma de comprobar que se lanza la excepcion
                // userController.getUser(1);
                // 2th forma de comprobar que se lanza la excepcion
                mockMvc.perform(get("/api/v1/users/{id}", 1L)).andExpect(status().isNotFound());
                verify(userServiceMock, times(1)).findById(1L);
                verifyNoMoreInteractions(userServiceMock);
        }

        @Test
        public void testListAllUsersOrdered() {

        }

        @Test
        public void testCreateUserWithBadData() throws Exception {

                // ingreso un dato con una longitud erronea, para eso uso la sig linea
                String nameLong = TestUtil.createStringWithLength(501);
                User user = User.builder().name(nameLong).lastname("wilgenhoff").address("misiones 776").city("tandil")
                                .dni(33356953L).email("adrianwilgenhoff@gmail.com").password("password")
                                .telephone("2494655632").username("usernameA").id(1L).build();
                mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(TestUtil.convertObjectToJsonBytes(user))).andExpect(status().isBadRequest());
                // .andExpect(jsonPath("$.errors", hasSize(2)))
                // .andExpect(jsonPath("$.errors.code", is("Size")))
                // .andExpect(jsonPath("$.errors[*].path", containsInAnyOrder("title",
                // "description")))
                // .andExpect(jsonPath("$.errors[*].message", containsInAnyOrder(
                // "The maximum length of the description is 500 characters.",
                // "el tamaño tiene que estar entre 1 y 50")));
                verifyZeroInteractions(userServiceMock);
        }

        @Test
        public void testCreateUser() throws Exception {

                User user = User.builder().name("adrian").lastname("wilgenhoff").address("misiones 776").city("tandil")
                                .dni(33356953L).email("adrianwil@gmail.com").password("password")
                                .telephone("2494655632").username("usernameA").id(null).build();
                ResponseEntity<Void> response = userController.createUser(user);
                // when(userServiceMock.saveUser(any(User.class))).thenReturn(response);
                // userServiceMock.saveUser(user);
                mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(TestUtil.convertObjectToJsonBytes(user)))
                                // .andExpect(redirectedUrl("/api/v1/users/1"))
                                .andExpect(status().isCreated());
                URI uri = new URI("/api/v1/users/");
                assertEquals(uri, response.getHeaders().getLocation());
                verify(userServiceMock, times(1)).saveUser(user);
        }

        @Test
        public void testDeleteUser() throws Exception {

                when(userServiceMock.findByUsername(any(String.class))).thenReturn(adrian);
                MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/usernameA"))
                                .andReturn();
                int status = mvcResult.getResponse().getStatus();
                assertEquals(204, status);
                String content = mvcResult.getResponse().getContentAsString();
                assertEquals(content, "User Deleted");
        }

        @Test
        public void testUpdateUser() throws Exception {

                when(userServiceMock.findByUsername(any(String.class))).thenReturn(adrian);
                byte[] inputJson = TestUtil.convertObjectToJsonBytes(adrian);
                MvcResult mvcResult = mockMvc
                                .perform(MockMvcRequestBuilders.put("/api/v1/users/1")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                                .andReturn();
                int status = mvcResult.getResponse().getStatus();
                assertEquals(200, status);
                String content = mvcResult.getResponse().getContentAsString();
                assertEquals(content, "A user is updated");
        }

}