package com.managementLogin;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.memberLogin.Member;

@ManagedBean(name = "managementLoginBean")
@SessionScoped
public class ManagementLoginBean {
    private String username;
    private String password;

    // ✅ Member list for management
    private List<Member> registeredMembers = new ArrayList<>();

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Member> getRegisteredMembers() {
        return registeredMembers;
    }

    public void addMember(Member member) {
        registeredMembers.add(member);
    }

    public boolean isValidLogin(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            return false;
        }
        return "management@email.com".equals(username) && "ManagementPass123!".equals(password);
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            context.addMessage("loginForm:username", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Please fill in all required fields", null));
            return null;
        }

        if (isValidLogin(username, password)) {
            context.addMessage(null, new FacesMessage("Login successful"));
            return "managementDashboard.xhtml?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
            return null;
        }
    }
}
