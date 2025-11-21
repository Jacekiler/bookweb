package com.jszarski.bookservice.service;

import com.jszarski.bookservice.model.entity.AppUser;
import com.jszarski.bookservice.model.entity.AppUserRole;
import com.jszarski.bookservice.repository.UserRepository;
import com.jszarski.common.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(appUser -> User.builder()
                        .username(appUser.getUsername())
                        .password(appUser.getPassword())
                        .roles(appUser.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    public void register(UserDTO userDTO, boolean admin) {
        var user = AppUser.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(admin ? AppUserRole.ADMIN : AppUserRole.USER)
                .build();
        userRepository.save(user);
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }
}
