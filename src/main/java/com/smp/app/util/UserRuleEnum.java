package com.smp.app.util;

public enum UserRuleEnum {

    MANAGEMENT(1), REVIEWER(2), LEAD_REVIEWER(3), SUPER_ADMIN(4);

    private final int id;

    UserRuleEnum(int id) {
        this.id = id;
    }

    public static String getRuleName(int id) {
        String state = null;
        if (id == MANAGEMENT.getId()) {
            state = "Management";
        }
        if (id == REVIEWER.getId()) {
            state = "Reviewer";
        }
        if (id == LEAD_REVIEWER.getId()) {
            state = "Lead Reviewer";
        }
        if(id == SUPER_ADMIN.getId()){
            state = "SuperAdmin";
        }
        return state;
    }

    public int getId() {
        return id;
    }
}
