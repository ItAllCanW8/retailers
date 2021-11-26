package com.itechart.retailers.service;

import com.itechart.retailers.model.Status;
import com.itechart.retailers.model.User_TEST;
import com.itechart.retailers.model.UserAuthDetails;
import com.itechart.retailers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User_TEST user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("NOT EXISTS"));
        return new UserAuthDetails(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getAuthorities(),
                user.getStatus().equals(Status.ACTIVE)
        );
    }
}
