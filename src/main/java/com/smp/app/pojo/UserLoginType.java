package com.smp.app.pojo;

public enum UserLoginType {
    MANAGEMENT("Management", 1), REVIEWER("Reviewer", 2), LEAD_REVIEWER("Lead Reviewer", 3), SUPERADMIN("SuperAdmin", 4);

    String role;
    Integer roleId;

    UserLoginType(String role, Integer roleId){
        this.role = role;
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
