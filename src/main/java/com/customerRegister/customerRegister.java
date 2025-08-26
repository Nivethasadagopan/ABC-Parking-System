package com.customerRegister;

import java.io.Serializable;
import com.userstore.UserDatabase; // <-- IMPORT THE NEW CLASS

public class customerRegister implements Serializable {

    private String name;
    private String email;
    private String password;
    private String message;

    public String register() {
        if (!isEmailValid(email)) {
            message = "Invalid email format.";
            return null;
        }

        if (!isPasswordStrong(password)) {
            message = "Password is not strong enough.";
            return null;
        }

        // --- NEW LOGIC ---
        // Check if the user already exists
        if (UserDatabase.getUsers().containsKey(email)) {
            message = "This email address is already registered.";
            return null;
        }
        
        // Save the new user to our central map ✍️
        UserDatabase.getUsers().put(email, password);
        // --- END NEW LOGIC ---

        message = "Registration completed successfully! Please log in.";
        return null; 
    }

    // --- No changes needed below this line ---
    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) { return false; }
        int atSignIndex = email.indexOf('@');
        if (atSignIndex <= 0 || atSignIndex == email.length() - 1) { return false; }
        int dotIndex = email.indexOf('.', atSignIndex);
        if (dotIndex <= atSignIndex + 1 || dotIndex == email.length() - 1) { return false; }
        return true;
    }
    private boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) { return false; }
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        String specialChars = "@#$%^&+=!";
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.indexOf(c) != -1) hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}