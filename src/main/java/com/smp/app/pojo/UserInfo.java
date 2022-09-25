package com.smp.app.pojo;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/*
POJO class to hold user claims present in JWT token
 */
@Getter
public class UserInfo extends User {

    private static final long serialVersionUID = -3531439484732724601L;

    private final Integer userId;

    private final String name;

    private final Integer roleId;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer userId,
        String name, Integer roleId) {
        super(username, password, authorities);
        this.userId = userId;
        this.name = name;
        this.roleId = roleId;
    }
}