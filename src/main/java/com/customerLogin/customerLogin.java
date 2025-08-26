package com.customerLogin;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import com.userstore.UserDatabase;

public class customerLogin implements Serializable {
    private String username;
    private String password;

    /**
     * This is the new, easily testable method containing the core logic.
     * @return true if username and password are valid, false otherwise.
     */
    public boolean validateCredentials() {
        if (username == null || password == null) {
            return false;
        }
        String storedPassword = UserDatabase.getUsers().get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    /**
     * This method handles the JSF-specific actions (messages and navigation).
     * It now calls our new logic method.
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (validateCredentials()) {
            context.addMessage(null, new FacesMessage("Login successful!"));
            return "index.xhtml?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
            return null;
        }
    }

    // --- Getters and Setters ---
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}