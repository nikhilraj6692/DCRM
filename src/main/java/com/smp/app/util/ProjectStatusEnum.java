package com.smp.app.util;

public enum ProjectStatusEnum {

    OPEN(1), MANAGEMENT_COMPLETED(2), REVIEWER_COMPLETED(3), LEAD_REVIEWER_COMPLETED(4);

    private final int id;

    ProjectStatusEnum(int id) {
        this.id = id;
    }

    public static String getProjectStatus(int id) {
        String state = null;
        if (id == OPEN.getId()) {
            state = "Open";
        }
        if (id == MANAGEMENT_COMPLETED.getId()) {
            state = "Management completed";
        }
        if (id == REVIEWER_COMPLETED.getId()) {
            state = "Reviewer completed";
        }
        if (id == LEAD_REVIEWER_COMPLETED.getId()) {
            state = "Lead reviewer completed";
        }

        return state;
    }

    public int getId() {
        return id;
    }
}
