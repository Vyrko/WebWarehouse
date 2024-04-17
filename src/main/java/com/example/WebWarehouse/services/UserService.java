package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.Role;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean saveUser(User user) throws IOException {
        if (userRepository.findByEmail(user.getEmail()) !=null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Save new user email: {}", user.getEmail());
        userRepository.save(user);
        return true;
    }
    public User findUserByEmail (String email){return userRepository.findByEmail(email);}
    public boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
    public List<User> findAll(){return userRepository.findAll();}

    public List<User> findByUserRole(String role) {
        return userRepository.findByRole(role);
    }
    public List<User> findByRoleAndBusy(Role role, boolean busy){
        return userRepository.findByRoleAndBusy(role,false);
    }

    public void setBusy(boolean busy, User user) {
        user.setBusy(busy);
        userRepository.save(user);
    }
}
