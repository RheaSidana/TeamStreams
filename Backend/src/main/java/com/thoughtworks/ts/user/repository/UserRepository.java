package com.thoughtworks.ts.user.repository;

import com.thoughtworks.ts.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM TS_USER WHERE ts_user.email = ?1 LIMIT 1", nativeQuery = true)
    User findByEmail(String e);

}
