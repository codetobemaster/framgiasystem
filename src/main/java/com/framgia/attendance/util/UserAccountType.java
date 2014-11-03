package com.framgia.attendance.util;

public enum UserAccountType {
    USER(0), ADMIN(1),APPROVER(2);

    private final int userAccount;

    private UserAccountType(int userAccount) {
        this.userAccount = userAccount;
    }

    public int getUserType() {
        return this.userAccount;
    }
}
