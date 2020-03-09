package ca.mcgill.ecse321.petadoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtProvider jwtProvider;

    // this is because we cannot perform @autowired authManager anymore cuz its only compatible w/ older Spring Boot
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable() // this disables cross-site request forgery where an attacker manages to get an authenticated user to perform a malicious request to his liking using diff links and such
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // tell it to use the filterChain that intercepts requests and checks the authorization header for a jwt
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/login/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/register/").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtProvider)); // this specifies to add our custom filter before the usernamepasswordauthenticationfilter
    }

}
