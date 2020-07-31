package com.ceezyyy.securitydemo.securityEnum;

import com.ceezyyy.securitydemo.securityEnum.UserPermission;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum UserRole {

    // 使用 guava 工具类简化代码
    ADMIN(Sets.newHashSet(UserPermission.READ)),
    VISITOR(Sets.newHashSet(UserPermission.CREATE, UserPermission.READ, UserPermission.UPDATE, UserPermission.DELETE));

    private final Set<UserPermission> permissionSet;

    UserRole(Set<UserPermission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<UserPermission> getPermissionSet() {
        return permissionSet;
    }

    // authorities of role
//    public Set<GrantedAuthority> getGrantedAuthorities() {
//
//    }

}
