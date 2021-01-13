package com.springreact.template.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    // "Safe Guard" so that when the user calls /userId only his/her id can be accessed
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(
            @Param("email") String email
    );
}
