package com.smp.app.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class UserContextImpl implements UserContext {

    private final UsernamePasswordAuthenticationToken authentication;
    @Autowired
    private ObjectMapper objectMapper;

    public UserContextImpl() {
        this.authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Integer getUserId() {
        Map<String, Object> details = this.getAttributes();
        return (Integer) details.get("userId");
    }

    @Override
    public Integer getUserType() {
        Map<String, Object> details = this.getAttributes();
        return (Integer) details.get("userId");
    }

    @Override
    public Integer getUserRuleType() {
        Map<String, Object> details = this.getAttributes();
        return (Integer) details.get("roleId");
    }

    @Override
    public String getUserName() {
        Map<String, Object> details = this.getAttributes();
        return (String) details.get("name");
    }

    @Override
    public String getUserEmailId() {
        Map<String, Object> details = this.getAttributes();
        return (String) details.get("username");
    }

    @Override
    public Collection<? extends GrantedAuthority> getRole() {
        if (null != this.authentication) {
            return this.authentication.getAuthorities();
        }
        return Arrays.asList();
    }

    @Override
    public String getFirstRole() {
        if (null != this.authentication) {
            Collection<? extends GrantedAuthority> roles = this.authentication.getAuthorities();
            for (GrantedAuthority role : roles) {
                return role.getAuthority();
            }
        }
        return "";
    }

    private Map<String, Object> getAttributes() {
        return objectMapper.convertValue(this.authentication.getPrincipal(), Map.class);
    }
}
