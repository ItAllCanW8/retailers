package com.itechart.retailers.security.service;

import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("NOT EXISTS"));
        return new UserDetailsImpl(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user),
                user.isActive()
        );
    }

    private List<SimpleGrantedAuthority> getAuthorities(User user) {
        String role = user.getRole().getRole();
        List<String> authorities = RoleAuthorityMapper.valueOf(role).getAuthorities();
        return authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
