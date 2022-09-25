package com.smp.app.config;

import com.smp.app.dao.UserDetailDao;
import com.smp.app.entity.UserDetail;
import com.smp.app.pojo.UserInfo;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailDao userDetailDao;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        if (StringUtils.hasText(emailId)) {
            UserDetail userDetail = userDetailDao.getUserBasedEmail(emailId);
            if (emailId.equals(userDetail.getUserEmailId())) {
                return new UserInfo(emailId, userDetail.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(userDetail.getUserRule().getRuleName())), userDetail.getUserId(),
                    userDetail.getUserName(), userDetail.getUserRule().getRuleId());

            } else {
                throw new UsernameNotFoundException("User not found: " + emailId);
            }
        } else {
            return null;
        }
    }
}
