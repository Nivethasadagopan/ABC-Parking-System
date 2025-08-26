package com.memberLogin;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemberLoginBeanTest {

    private MemberLoginBean loginBean;

    @Before
    public void setUp() {
        loginBean = new MemberLoginBean();
    }

    @Test
    public void testLoginSuccess() {
        loginBean.setUsername("john@email.com");
        loginBean.setPassword("JohnPass123@");

        String result = loginBean.login();
        assertEquals("memberDashboard.xhtml?faces-redirect=true", result);
        assertNotNull(loginBean.getMember());
        assertEquals("John", loginBean.getMember().getCompanyName());
    }

    @Test
    public void testLoginEmptyFields() {
        loginBean.setUsername("");
        loginBean.setPassword("");

        String result = loginBean.login();
        assertNull(result);
        assertNull(loginBean.getMember());
    }

    @Test
    public void testLoginInvalidEmailFormat() {
        loginBean.setUsername("invalidEmail");
        loginBean.setPassword("somePassword");

        String result = loginBean.login();
        assertNull(result);
        assertNull(loginBean.getMember());
    }

    @Test
    public void testLoginWrongCredentials() {
        loginBean.setUsername("john@email.com");
        loginBean.setPassword("WrongPassword");

        String result = loginBean.login();
        assertNull(result);
        assertNull(loginBean.getMember());
    }
}
