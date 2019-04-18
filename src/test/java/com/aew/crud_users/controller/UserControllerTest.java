package com.aew.crud_users.controller;

import com.aew.crud_users.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

    String exampleUserJson = "{\"name\":\"adrian\",\"lastname\":\"wilghenhoff\",\"dni\":\"33356953\",\"address\":\"misiones 776\",\"city\":\"tandil\",\"telephone\":\"2494655632\",\"password\":\"1234\",\"email\":\"adrianwilgenhoff@gmail.com\",\"username\":\"adrian8842\",}";

    @Test

    public void testListAllUsers() throws Exception {

    }

    @Test
    public void testListAllUsersOrdered() {

    }

    @Test
    public void testGetUser() {

    }

    @Test
    public void testCreateUser() throws Exception {

        User mockUser = new User(1L, "username", "password", "name", "lastname", "address", "city", "email",
                "telephone", 123456789L);
    }

    @Test
    public void testDeleteUser() {

    }

    @Test
    public void testUpdateUser() {

    }

}