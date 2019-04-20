package com.aew.crud_users.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.aew.crud_users.model.User;
import com.aew.crud_users.repository.UserRepository;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepositoryMock;

    private User luz;
    private User adrian;
    private List<User> users;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        adrian = User.builder().name("adrian").lastname("wilgenhoff").address("misiones 776").city("tandil")
                .dni(33356953L).email("adrianwilgenhoff@gmail.com").password("password").telephone("2494655632")
                .username("usernameA").id(1L).build();
        luz = User.builder().name("maria luz").lastname("gomez").address("jbjusto 695").city("tandil").dni(12345678L)
                .email("marialuz@gmail.com").password("password").telephone("2494984512").username("usernameL").id(2L)
                .build();
        users = Arrays.asList(adrian, luz);
    }

    @Test
    public void testFindById() {

        when(userRepositoryMock.findById(anyLong())).thenReturn(adrian);
        User user = userService.findById(2L);
        assertNotNull(user);
        assertEquals("usernameA", user.getUsername());
        verify(userRepositoryMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testFindByUsername() {

        when(userRepositoryMock.findByUsername(any(String.class))).thenReturn(adrian);
        User user = userService.findByUsername("usernameA");
        assertNotNull(user);
        assertEquals("usernameA", user.getUsername());
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testFindByUsernameNotExist() {

        when(userRepositoryMock.findByUsername(any(String.class))).thenReturn(null);
        User user = userService.findByUsername("usernameA");
        assertNull(user);
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testFindyByIdNotFound() {
        when(userRepositoryMock.findById(anyLong())).thenReturn(null);
        User user = userService.findById(2L);
        assertNull(user);
        verify(userRepositoryMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testfindAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(users);
        List<User> users = userService.findAllUsers();
        assertThat(users, Matchers.hasSize(2));

        verify(userRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testSaveUsers() {

        userService.saveUser(adrian);
        verify(userRepositoryMock, times(1)).save(adrian);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void testUpdateUserAndUserNotExists() {

        userService.updateUser(adrian);
        verify(userRepositoryMock, times(1)).save(adrian);
        verifyNoMoreInteractions(userRepositoryMock);
    }

}