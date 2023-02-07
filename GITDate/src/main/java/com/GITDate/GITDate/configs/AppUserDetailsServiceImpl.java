package com.GITDate.GITDate.configs;

import com.GITDate.GITDate.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// TODO: Step 3: ApplicationUserDetailsServiceImpl implements UserDetailsService
@Service("userDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {
    // TODO: Step 3a: AutoWire ApplicationUserRepository

    @Autowired
    AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) appUserRepository.findByUsername(username);
    }

}
