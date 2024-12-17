package com.example.api.services;


import com.example.api.error.ApiError;
import com.example.api.exception.ApiException;
import com.example.api.model.Role;
import com.example.api.model.User;
import com.example.api.repository.RoleRepository;
import com.example.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }


    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    public void AddRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND));
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
