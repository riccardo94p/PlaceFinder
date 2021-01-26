package com.example.PlaceFinder.services;

import com.example.PlaceFinder.entity.*;
import com.example.PlaceFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsManagerImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority(user.getRole()));

            List<GrantedAuthority> authorities = new ArrayList<>(roles);//getUserAuthority(user.getRoles());

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else throw new UsernameNotFoundException("User "+username+" not found.");
    }
}