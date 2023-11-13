package com.kinto2517.nevitechinternshipcase.repository;

import com.kinto2517.nevitechinternshipcase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName (String name);
}