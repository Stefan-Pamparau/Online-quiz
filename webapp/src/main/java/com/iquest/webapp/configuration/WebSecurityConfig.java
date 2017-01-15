package com.iquest.webapp.configuration;

import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import com.iquest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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
@Profile(value = {"development", "production"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_AUTHORITY = "ADMIN";
    private static final String ADMIN_OR_CLIENT_AUTHORITY = "ADMIN, CLIENT";

    private UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/admin/**").permitAll()
                .antMatchers("/admin/**").hasAuthority(ADMIN_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/client/add/friend").permitAll()
                .antMatchers("/client/add/friend").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)
                .antMatchers(HttpMethod.OPTIONS, "/client/get/clientWithQuizzes").permitAll()
                .antMatchers("/client/get/clientWithQuizzes").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)
                .antMatchers(HttpMethod.OPTIONS, "/client/**").permitAll()
                .antMatchers("/client/**").hasAuthority(ADMIN_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/examQuiz/**").permitAll()
                .antMatchers("/examQuiz/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/gamefiedQuiz/**").permitAll()
                .antMatchers("/gamefiedQuiz/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/lobby/**").permitAll()
                .antMatchers("/lobby/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/multipleChoiceAnswer/**").permitAll()
                .antMatchers("/multipleChoiceAnswer/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/multipleChoiceQuestion/**").permitAll()
                .antMatchers("/multipleChoiceQuestion/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/simpleAnswer/**").permitAll()
                .antMatchers("/simpleAnswer/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/simpleQuestion/**").permitAll()
                .antMatchers("/simpleQuestion/**").hasAnyAuthority(ADMIN_OR_CLIENT_AUTHORITY)

                .antMatchers(HttpMethod.OPTIONS, "/reports/**").permitAll()
                .antMatchers("/reports/**").hasAuthority(ADMIN_AUTHORITY)

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

                if (user != null && user.getConfirmed()) {
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