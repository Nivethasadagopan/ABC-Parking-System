package com.managementLogin;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



public class ManagementLoginBeanTest {

    private ManagementLoginBean bean;

    @Before
    public void setUp() {
        bean = new ManagementLoginBean();
    }

    @Test
    public void testEmptyUsernameAndPassword() {
        assertFalse(bean.isValidLogin("", ""));
    }

    @Test
    public void testInvalidCredentials() {
        assertFalse(bean.isValidLogin("wrong@email.com", "WrongPass!"));
    }

    @Test
    public void testValidCredentials() {
        assertTrue(bean.isValidLogin("management@email.com", "ManagementPass123!"));
    }
}
