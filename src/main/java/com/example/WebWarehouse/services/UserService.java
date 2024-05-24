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
import java.util.NoSuchElementException;
import java.util.Optional;


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
        user.setStatus("OFFLINE");
        userRepository.save(user);
        return true;
    }
    public void disconnect(User user){
        var storedUser = userRepository.findByEmail(user.getEmail().replace('_','@'));
        if(storedUser != null){
            storedUser.setStatus("OFFLINE");
            userRepository.save(storedUser);
        }
    }
    public List<User> findConnectedUsers(){
        List<User> users = userRepository.findAllByStatus("ONLINE");
        for (var i=0; i< users.size();i++){
            users.get(i).setEmail(users.get(i).getEmail().replace('@','_'));
        }
        return users;
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

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User connectedWithChat(String email) {
        User user = userRepository.findByEmail(email);
        user.setStatus("ONLINE");
        User nUser =userRepository.save(user);
        return  nUser;
    }

}
