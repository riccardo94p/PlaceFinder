package com.example.PlaceFinder.config;

import com.example.PlaceFinder.services.UserDetailsManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    UserDetailsManagerImpl userDetailsManager;

    @Bean
    public UserDetailsService myUserDetails() {
        return new UserDetailsManagerImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = myUserDetails();

        //For simplicity we are not using password encryption. The NoOpPasswordEncoder means that no
        //password encoder will be used therefore password will be stored in plaintext
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/login", "/style.css").permitAll()
                    .antMatchers("/main/**").hasAnyAuthority("STUDENT", "PROF")
                    .antMatchers("/reservation/**").hasAnyAuthority("STUDENT", "PROF")
                    .antMatchers("/user/**").hasAnyAuthority("STUDENT", "PROF")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")//.failureUrl("/login?error=true")
                    .permitAll()
                    .and()
                .logout().permitAll();
                    /*.and()
                .exceptionHandling().accessDeniedPage("/403");*/
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**");//, "/css/**", "/images/**");
    }
}