package com.adminRegister;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean(name = "memberListBean")
@ApplicationScoped
public class MemberListBean implements Serializable {

    private List<AddMemberBean> members = new ArrayList<>();
    private AddMemberBean selectedMember;

    public void addMember(AddMemberBean member) {
        members.add(member);
    }

    public List<AddMemberBean> getMembers() {
        return members;
    }

    public String edit(AddMemberBean member) {
        this.selectedMember = member;
        return "editmember.xhtml?faces-redirect=true";
    }

    public String delete(AddMemberBean member) {
        members.remove(member);
        return "member-list.xhtml?faces-redirect=true";
    }

    public String update() {
        return "member-list.xhtml?faces-redirect=true";
    }

    public AddMemberBean getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(AddMemberBean selectedMember) {
        this.selectedMember = selectedMember;
    }
}
