package com.getjavajob.training.web1803.webapp.config;

import com.getjavajob.training.web1803.service.SocNetUserDetailService;
import com.getjavajob.training.web1803.webapp.security.SocNetAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

//@ComponentScan(basePackages = {"com.getjavajob.training.web1803.dao", "com.getjavajob.training.web1803.service",
//        "com.getjavajob.training.web1803.webapp"})
//@Import(AccountDAO.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    SocNetUserDetailService userDetailService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider());
//        auth.userDetailsService(new SocNetUserDetailService());
//    }

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
//                .antMatchers("/view/", "/jsp/reg.jsp", "/regPage", "/reg", "/resources/**").permitAll()
//                .antMatchers("/removeAccount").hasRole("ADMIN")
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/jsp/403.jsp")
                .and()
                .formLogin()
                .loginPage("/jsp/login.jsp")
                .loginProcessingUrl("/login").failureUrl("/jsp/login.jsp?infoMessage=alert")
                .successHandler(successHandler())
                .and()
                .logout().logoutSuccessUrl("/jsp/login.jsp").deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("SocNetKey").userDetailsService(userDetailService)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(5);
    }

//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(new SocNetUserDetailService());
//        authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }

//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SocNetAuthenticationSuccessHandler successHandler() {
        return new SocNetAuthenticationSuccessHandler();
    }


//    @Bean
//    public SocNetUserDetailService socNetuserDetailService() {
//        return new SocNetUserDetailService();
//    }
//
//    @Bean
//    public AccountDAO accountDAO() {
//        return new AccountDAO();
//    }
//
//    @Bean
//    public AccountService accountService() {
//        return new AccountService();
//    }

}