package com.adminRegister;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "addMemberBean")
@SessionScoped
public class AddMemberBean implements Serializable {

    private String company;
    private String email;
    private String address;
    private String password;
    private int numberOfSpaces;
    private String message;

    public String save() {
        if (company == null || company.isEmpty() ||
            email == null || email.isEmpty() ||
            address == null || address.isEmpty() ||
            password == null || password.isEmpty() ||
            numberOfSpaces <= 0) {

            message = "All fields are required!";
            return null;
        }

        // ✅ Get MemberListBean and store
        MemberListBean memberList = FacesContext.getCurrentInstance()
                .getApplication()
                .evaluateExpressionGet(FacesContext.getCurrentInstance(),
                        "#{memberListBean}", MemberListBean.class);

        memberList.addMember(this.copy());

        message = "Member added successfully!";
        return "addmember-success.xhtml?faces-redirect=true";
    }

    // Helper copy method
    public AddMemberBean copy() {
        AddMemberBean copy = new AddMemberBean();
        copy.setCompany(this.company);
        copy.setEmail(this.email);
        copy.setAddress(this.address);
        copy.setPassword(this.password);
        copy.setNumberOfSpaces(this.numberOfSpaces);
        return copy;
    }

    // Getters / Setters
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getNumberOfSpaces() { return numberOfSpaces; }
    public void setNumberOfSpaces(int numberOfSpaces) { this.numberOfSpaces = numberOfSpaces; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
