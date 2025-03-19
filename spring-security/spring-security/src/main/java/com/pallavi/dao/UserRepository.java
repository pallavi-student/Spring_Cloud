package com.pallavi.spring_security.dao;
import org.springframework.data.jpa.repository.JpaRepository;
public class UserRepository {
    User findByUsername(String username);
}
