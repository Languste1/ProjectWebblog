package com.webprojekt.webblog.Security;

public enum UserPermission {
    ENTRY_READ("entry:read"),
    ENTRY_WRITE("entry:write"),
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),
    COMMENT_MYCOMMENT_WRITE("comment:write"),
    UPGRADE("rol:write"),
    BANN("rol:write"),
    DOWNGRADE("rol:write");
    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
