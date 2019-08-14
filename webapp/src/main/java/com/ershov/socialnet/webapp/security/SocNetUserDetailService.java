package com.ershov.socialnet.webapp.security;

import com.ershov.socialnet.common.Account;
import com.ershov.socialnet.dao.exceptions.DaoNameException;
import com.ershov.socialnet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@ComponentScan(basePackages = "com.getjavajob.training.web1803.service")
public class SocNetUserDetailService implements UserDetailsService {

    private AccountService accountService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public SocNetUserDetailService(AccountService accountService, BCryptPasswordEncoder encoder) {
        this.accountService = accountService;
        this.encoder = encoder;
    }

    public SocNetUserDetailService() {
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account;
        try {
            account = accountService.getByEmail(username);
        } catch (DaoNameException e) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        return new User(account.getEmail(), encoder.encode(account.getPassword()), true, true, true,
                true, getGrantedAuthorities(account));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().toString()));
        return authorities;
    }
}
