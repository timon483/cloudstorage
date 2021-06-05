package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthentificationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthentificationService authentificationService;

    public SecurityConfig(AuthentificationService authentificationService){
        this.authentificationService = authentificationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(this.authentificationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeRequests().antMatchers("/signup", "/css/**", "/js/**").permitAll().anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .permitAll().and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout");

        http.formLogin().defaultSuccessUrl("/home", true).and().logout();

    }



}
