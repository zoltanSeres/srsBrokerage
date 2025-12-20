package com.example.srsBrokerage.Repository;

import com.example.srsBrokerage.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
