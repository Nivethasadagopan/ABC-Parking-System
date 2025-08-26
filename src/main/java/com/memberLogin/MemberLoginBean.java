package com.memberLogin;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.adminRegister.MemberListBean;
import com.adminRegister.AddMemberBean;

@ManagedBean(name = "memberLoginBean")
@SessionScoped
public class MemberLoginBean implements Serializable {

    private String email;
    private String password;
    private String message;

    public String login() {
        // Get member list from session
        FacesContext context = FacesContext.getCurrentInstance();
        MemberListBean memberList = context.getApplication()
                .evaluateExpressionGet(context, "#{memberListBean}", MemberListBean.class);

        // Validate entered email & password
        for (AddMemberBean member : memberList.getMembers()) {
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                message = "Login successful!";
                return "memberDashboard.xhtml?faces-redirect=true"; // ✅ Correct filename
            }
        }
        message = "Invalid email or password!";
        return null; // Stay on login page
    }

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
