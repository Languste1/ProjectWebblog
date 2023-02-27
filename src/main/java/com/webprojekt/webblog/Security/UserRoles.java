package com.webprojekt.webblog.Security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.webprojekt.webblog.Security.UserPermission.*;

public enum UserRoles {
    USER(Sets.newHashSet (COMMENT_READ,ENTRY_READ,COMMENT_MYCOMMENT_WRITE)),
    ADMIN(Sets.newHashSet (COMMENT_READ,COMMENT_WRITE,ENTRY_READ,ENTRY_WRITE,
            UPGRADE,BANN,DOWNGRADE,COMMENT_MYCOMMENT_WRITE)),
    BANNED(Sets.newHashSet ()),
    MODERATOR(Sets.newHashSet (COMMENT_READ,COMMENT_WRITE,ENTRY_READ,ENTRY_WRITE,
            BANN,COMMENT_MYCOMMENT_WRITE));
    private final Set<UserPermission> permissions;

    UserRoles(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }
}
