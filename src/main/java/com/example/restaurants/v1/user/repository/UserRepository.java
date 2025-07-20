package com.example.restaurants.v1.user.repository;

import com.example.restaurants.v1.user.entity.User;
import com.example.restaurants.v1.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByRole(Role role);
  List<User> findByIdAndRole(Long id, Role role);
  Optional<User> findByNumber(String number);
  Optional<User> findByEmail(String email);
}

