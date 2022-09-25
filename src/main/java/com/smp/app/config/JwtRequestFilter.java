package com.smp.app.config;

import com.smp.app.dao.UserDetailDao;
import com.smp.app.entity.UserDetail;
import com.smp.app.pojo.UserContext;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Order(1000)
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private UserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        UserDetail user = null;
        String emailId = null;
        boolean isTokenValid = true;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                emailId = jwtTokenUtil.getEmailIdFromToken(jwtToken);
                if (null != emailId) {
                    user = userDetailDao.getUserBasedEmail(emailId);
                    if (null == user || !user.getToken().equals(jwtToken)) {
                        isTokenValid = false;
                    }
                } else {
                    isTokenValid = false;
                }

                if (!isTokenValid) {
                    throw new JwtException("Invalid token");
                }
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token : " + jwtToken);
                chain.doFilter(request, response);
                return;
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired token : " + jwtToken);
                chain.doFilter(request, response);
                return;
            } catch (JwtException e) {
                logger.warn(e.getMessage());
                chain.doFilter(request, response);
                return;
            }
        }
        // Once we get the token validate it.
        if (null != user && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(emailId);
            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken emailIdPasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                emailIdPasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                log.info("Request url {} called by {}", request.getRequestURI(), userDetails.getUsername());
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(emailIdPasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);
    }


}
