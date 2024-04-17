package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Role;
import com.example.WebWarehouse.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User getById(Long id);
    List<User> findByRole(String role);
    List<User> findByRoleAndBusy(Role role, boolean busy);
}
