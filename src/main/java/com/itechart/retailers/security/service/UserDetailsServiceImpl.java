package com.itechart.retailers.security.service;

import com.itechart.retailers.model.User;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("NOT EXISTS"));
        return new UserDetailsImpl(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities(),
                user.isActive()
        );
    }
}
