package com.smp.app.config;

import com.smp.app.dao.UserDetailDao;
import com.smp.app.pojo.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.ttl}")
    private long tokenTtl;

    @Value("${jwt.refresh.token.ttl}")
    private long refreshTokenTtl;

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // retrieve name from jwt token
    public String getEmailIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        // Decrypt token to getClaimsFromToken
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    public TokenResponse generateToken(Authentication authentication) {

        // Encrypt subject using generic AES algo
        String encryptedSubj = authentication.getName();

        String actualToken = Jwts.builder().setSubject(encryptedSubj).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + tokenTtl)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        String refreshToken = Jwts.builder().setSubject(encryptedSubj).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenTtl))
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        log.info("Actual and refresh token will expire at {} and {} respectively",
            new Date(System.currentTimeMillis() + tokenTtl), new Date(System.currentTimeMillis() + refreshTokenTtl));

        return new TokenResponse(actualToken, refreshToken);
    }

    public TokenResponse generateToken(String emailId, String token) {
        UserDetails details = jwtUserDetailsService.loadUserByUsername(emailId);
        if (validateToken(token, details)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return generateToken(authentication);
        } else {
            throw new JwtException("Invalid token");
        }
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String emailId = getEmailIdFromToken(token);
        return (emailId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String token, String emailId) {
        final String emailFromClaims = getEmailIdFromToken(token);
        return (emailId.equals(emailFromClaims) && !isTokenExpired(token));
    }

    public void removeToken(String authToken, HttpServletRequest request, HttpServletResponse response) {
        // validate token and remove it, if it is validated
        /*UserDetails userDetails = userDetailDao.getUserBasedEmail();
        if (user != null) {
            user.setAuthToken(null);
            user.setRefreshToken(null);
            userRepository.save(user);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }*/
    }
}