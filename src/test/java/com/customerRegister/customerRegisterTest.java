package com.customerRegister;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class customerRegisterTest {

    private customerRegister bean;

    @Before
    public void setUp() {
        bean = new customerRegister();
    }

    @Test
    public void testSuccessfulRegistration() {
        // Set valid credentials
        bean.setEmail("valid.user@example.com");
        bean.setPassword("StrongPass123!");
        
        // Call the register method
        bean.register();
        
        // Check for the success message
        assertEquals("Registration completed!", bean.getMessage());
    }

    @Test
    public void testInvalidEmailFormat() {
        // Set an invalid email
        bean.setEmail("invalid-email");
        bean.setPassword("StrongPass123!");
        
        bean.register();
        
        // Check for the email error message
        assertEquals("Invalid email format.", bean.getMessage());
    }

    @Test
    public void testWeakPassword() {
        // Set a weak password
        bean.setEmail("valid.user@example.com");
        bean.setPassword("weak");
        
        bean.register();
        
        // Check for the password error message
        assertEquals("Password is not strong enough.", bean.getMessage());
    }
    
    @Test
    public void testEmptyEmail() {
        // Set an empty email
        bean.setEmail("");
        bean.setPassword("StrongPass123!");
        
        bean.register();
        
        assertEquals("Invalid email format.", bean.getMessage());
    }
    
    @Test
    public void testEmptyPassword() {
        // Set an empty password
        bean.setEmail("valid.user@example.com");
        bean.setPassword("");
        
        bean.register();
        
        assertEquals("Password is not strong enough.", bean.getMessage());
    }
}