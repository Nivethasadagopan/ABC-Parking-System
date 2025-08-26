package com.adminRegister;

import java.io.Serializable;
import com.userstore.UserDatabase;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class adminRegister implements Serializable {

    private String adminname;
    private String adminemail;
    private String adminpassword;
    private String message;
    private boolean acceptPrivacy;  

    public String register() {
        if (!acceptPrivacy) {
            message = "You must accept the Privacy Statement.";
            return null;
        }

        if (adminemail == null || adminpassword == null || adminemail.isEmpty() || adminpassword.isEmpty()) {
            message = "Email and Password are required!";
            return null;
        }

        // ✅ Normalize email to lowercase
        String emailKey = adminemail.toLowerCase();

        if (UserDatabase.getAdmins().containsKey(emailKey)) {
            message = "This email is already registered!";
            return null;
        }

        // ✅ Store in UserDatabase
        UserDatabase.getAdmins().put(emailKey, adminpassword);
        message = "Registration successful! Please login.";

        // ✅ Redirect to login page
        return "adminLogin.xhtml?faces-redirect=true";
    }

    // Getters & setters
    public String getAdminname() { return adminname; }
    public void setAdminname(String adminname) { this.adminname = adminname; }
    public String getAdminemail() { return adminemail; }
    public void setAdminemail(String adminemail) { this.adminemail = adminemail; }
    public String getAdminpassword() { return adminpassword; }
    public void setAdminpassword(String adminpassword) { this.adminpassword = adminpassword; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isAcceptPrivacy() { return acceptPrivacy; }
    public void setAcceptPrivacy(boolean acceptPrivacy) { this.acceptPrivacy = acceptPrivacy; }
}
