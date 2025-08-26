package com.adminLogin;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.userstore.UserDatabase;

@ManagedBean(name = "adminLogin")   // ✅ explicitly name the bean
@SessionScoped
public class adminLogin implements Serializable {

    private String email;
    private String password;
    private String message;
    private boolean loggedIn = false;

    // --- Validate ---
    private boolean validateCredentials() {
        if (email == null || password == null) {
            return false;
        }
        String storedPassword = UserDatabase.getAdmins().get(email.toLowerCase());
        return storedPassword != null && storedPassword.equals(password);
    }

    // --- Login ---
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (validateCredentials()) {
            loggedIn = true;
            context.addMessage(null, new FacesMessage("Login successful!"));
            return "AdminDashboard.xhtml?faces-redirect=true";
        } else {
            loggedIn = false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password", null));
            return null;
        }
    }

    // --- Logout ---
    public String logout() {
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .invalidateSession();
        loggedIn = false;
        message = null;
        return "adminLogin.xhtml?faces-redirect=true";
    }

    // --- Restrict direct access ---
    public void redirectIfNotLoggedIn() throws java.io.IOException {
        if (!loggedIn) {
            FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect("adminLogin.xhtml");
        }
    }

    // --- Getters & Setters ---
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isLoggedIn() { return loggedIn; }
}
