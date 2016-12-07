package com.iquest.webapp.configuration;

import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ADMIN_AUTHORITY = "ADMIN";
    public static final String HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT = "hasAuthority(ADMIN) or hasAuthority(CLIENT)";
    private UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/admin").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers("/client").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers("/examQuizController").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .antMatchers("/gamefiedQuizController").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .antMatchers("/lobbyController").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .antMatchers("/multipleChoiceAnswer").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .antMatchers("/multipleChoiceQuestion").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .antMatchers("/simpleAnswer").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .antMatchers("/simpleQuestion").access(HAS_AUTHORITY_ADMIN_OR_HAS_AUTHORITY_CLIENT)
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(createAuthenticationProvider());
    }

    private AuthenticationProvider createAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String email = authentication.getName();
                String password = authentication.getCredentials().toString();

                User user = userService.findByEmailAndPassword(email, password);

                if (user != null) {
                    Authentication auth;
                    if (UserType.ADMIN == user.getUserType()) {
                        auth = new UsernamePasswordAuthenticationToken(email, password, AuthorityUtils.createAuthorityList("ADMIN"));
                    } else {
                        auth = new UsernamePasswordAuthenticationToken(email, password, AuthorityUtils.createAuthorityList("CLIENT"));
                    }
                    return auth;
                }

                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }
}