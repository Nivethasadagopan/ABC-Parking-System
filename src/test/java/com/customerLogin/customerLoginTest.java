package com.customerLogin;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.userstore.UserDatabase;

public class customerLoginTest {

    private customerLogin bean;

    @Before
    public void setUp() {
        // Create a new bean instance for each test
        bean = new customerLogin();
        
        // Add a mock user to our in-memory "database" for testing purposes
        UserDatabase.getUsers().put("test@user.com", "Password123!");
    }

    @Test
    public void testValidCredentials() {
        bean.setUsername("test@user.com");
        bean.setPassword("Password123!");
        assertTrue("Login should be successful with correct credentials", bean.validateCredentials());
    }

    @Test
    public void testInvalidPassword() {
        bean.setUsername("test@user.com");
        bean.setPassword("WrongPassword");
        assertFalse("Login should fail with an incorrect password", bean.validateCredentials());
    }

    @Test
    public void testInvalidUsername() {
        bean.setUsername("wrong@user.com");
        bean.setPassword("Password123!");
        assertFalse("Login should fail with a non-existent username", bean.validateCredentials());
    }

   
}