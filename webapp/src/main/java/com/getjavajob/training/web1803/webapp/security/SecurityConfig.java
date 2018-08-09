package com.getjavajob.training.web1803.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@ComponentScan(basePackages = "com.getjavajob.training.web1803.*")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SocNetAuthenticationSuccessHandler successHandler;

    @Autowired
    public SecurityConfig(SocNetAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Override
    public void configure(WebSecurity web) {
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SocNetUserDetailService userDetailService = new SocNetUserDetailService();
        http.authorizeRequests()
                .antMatchers("/view/", "/WEB-INF/jsp/reg.jsp", "/regPage", "/reg", "/resources/**").permitAll()
                .antMatchers("/removeAccount").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/errorAccess")
                .and()
                .formLogin()
                .loginPage("/loginPage").permitAll()
                .loginProcessingUrl("/login").failureUrl("/loginPage?infoMessage=alert")
                .successHandler(successHandler)
                .and()
                .logout().logoutSuccessUrl("/loginPage").deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("SocNetKey").userDetailsService(userDetailService)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(5);
    }

    @Bean
    public SocNetAuthenticationSuccessHandler successHandler() {
        return new SocNetAuthenticationSuccessHandler();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}