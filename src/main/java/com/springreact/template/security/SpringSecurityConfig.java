package com.springreact.template.security;

import com.springreact.template.db.User;
import com.springreact.template.db.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    public SpringSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/login", "/error", "/built/**", "/images/**").permitAll()
                        // TODO: example - block users endpoint generally for all Roles to prevent altering the data in any way from others
                        //.antMatchers(HttpMethod.PUT, "/api/users/**").denyAll()// access("hasAnyAuthority('ROLE_ROOT')")
                        .antMatchers("/api/profile/**").denyAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        // Redirect to Login Endpoint if not authenticated
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
                                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                httpServletResponse.getOutputStream().println("" +
                                        "<!DOCTYPE html>" +
                                        "<html lang=\"en\">" +
                                        "<head>" +
                                            "<meta name=\"viewport\" content=\"width-device-width, initial-scale=1.0\">" +
                                            /// TODO: FOR DEPLOYMENT REPLACE DOMAIN URL HERE (1)
                                            "<meta http-equiv=\"refresh\" content=\"5;url=http://localhost:8081/login\">" +
                                            "<title>taskify login</title>" +
                                        "</head>" +
                                        "<body style=\"padding-left: 0.5rem;\">" +
                                            "<p>" +
                                                "You are being redirected to the Login Page.<br>" +
                                                /// TODO: FOR DEPLOYMENT REPLACE DOMAIN URL HERE (2)
                                                "If nothing happens <a href=\"http://localhost:8081/login\">click here</a>." +
                                            "</p>" +
                                        "</body>" +
                                        "</html>");
                            }
                        })
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login().defaultSuccessUrl("/")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        Authentication a = SecurityContextHolder.getContext().getAuthentication();

                        if (a instanceof OAuth2AuthenticationToken) {
                            OAuth2User oauth2user = ((OAuth2AuthenticationToken) a).getPrincipal();

                            // get fields of interest
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", oauth2user.getAttribute("name"));
                            map.put("email", oauth2user.getAttribute("email"));

                            // check if user logs in the first time -> true: save to database
                            if (userRepository.findUserByEmail(map.get("email").toString()) == null) {
                                User user = new User(map.get("name").toString(), map.get("email").toString());
                                userRepository.save(user);
                            }
                        }
                        super.onAuthenticationSuccess(request, response, authentication);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletRequest.getSession().setAttribute("error.message", e.getMessage());
                        handler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                    }
                });
    }
}