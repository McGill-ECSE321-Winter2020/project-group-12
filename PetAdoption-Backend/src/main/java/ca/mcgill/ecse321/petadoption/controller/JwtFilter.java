package ca.mcgill.ecse321.petadoption.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// Job: intercept a request and look at the header to get the JWT
public class JwtFilter extends GenericFilterBean {
    private JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // intercepts req, checks if JWT is valid and if it finds a valid one, then it gets the user details and stores them in sec context
    // security context is used to store the details of the currently authenticated user  So, if you have to get the username or any other user details, you need to get this SecurityContext first. -----> UPDATE GET USERS CONTROLLER METHODS
    // security context holder is a helper class which provides access to the security context
    // VERY IMP AND HELPFUL SOURCE: https://www.javacodegeeks.com/2018/02/securitycontext-securitycontextholder-spring-security.html
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) // takes in a filter chain cuz we might pass on to the next filter in the chain if we have multiple filters we wna apply per request
            throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) req);
        // this call to validate ma elo 3aze.... minor fix yalla
        // to give authentication u gotta make sure the token in the db is still there ....
        // related to the user that is
        try {
            if (token != null && jwtProvider.validateToken(token)) { // if token is not null and it isnt expired and its valid (ie isnt empty or just whitespace either)
                Authentication auth = jwtProvider.getAuthentication(token); // returning the UsernamePasswordAuthenticationToken
                SecurityContextHolder.getContext().setAuthentication(auth); // now we have a record of who is currently logged in
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(req, res);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
