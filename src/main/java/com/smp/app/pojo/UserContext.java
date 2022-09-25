package com.smp.app.pojo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface UserContext {

    Integer getUserId();

    Integer getUserType();

    Integer getUserRuleType();

    String getUserName();

    String getUserEmailId();

    Collection<? extends GrantedAuthority> getRole();

    String getFirstRole();
}
