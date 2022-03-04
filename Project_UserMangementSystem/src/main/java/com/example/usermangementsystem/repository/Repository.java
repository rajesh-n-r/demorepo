package com.example.usermangementsystem.repository;

import com.example.usermangementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<User, Integer> {
}
