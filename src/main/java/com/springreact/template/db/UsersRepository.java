package com.springreact.template.db;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query(value = "SELECT u FROM Users u")
    List<User> listAllUsers();
}
