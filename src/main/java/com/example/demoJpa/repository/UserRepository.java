package com.example.demoJpa.repository;

import com.example.demoJpa.domain.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApplication, Integer> {

}
