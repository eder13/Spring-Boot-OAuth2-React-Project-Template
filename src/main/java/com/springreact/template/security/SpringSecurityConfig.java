package com.springreact.template.security;

import com.springreact.template.db.Users;
import com.springreact.template.db.UsersRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/main.css", "/built/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        // Return unauthenticated Webpage if not logged in (could also transform this into json this way)
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
                                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                httpServletResponse.getOutputStream().println("<script>window.location.href = \"http://localhost:8081/\"</script>");
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

                        if(a instanceof OAuth2AuthenticationToken) {
                            OAuth2User user = ((OAuth2AuthenticationToken) a).getPrincipal();

                            // parse JSON
                            Gson gson = new Gson();
                            String json = gson.toJson(user.getAttributes());

                            /// TODO: Check which Provider Login was - here hardcoded github example
                            GithubUser githubUser = gson.fromJson(json, GithubUser.class);
                            Users newUser = new Users(githubUser.getName(), githubUser.getEmail(), false);

                            ExampleMatcher userMatcher = ExampleMatcher.matching().withIgnorePaths("userID").withMatcher("email", ignoreCase());
                            Example<Users> usersExample = Example.of(newUser, userMatcher);
                            boolean userExists = usersRepository.exists(usersExample);

                            if(!userExists) {
                                usersRepository.save(newUser);
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