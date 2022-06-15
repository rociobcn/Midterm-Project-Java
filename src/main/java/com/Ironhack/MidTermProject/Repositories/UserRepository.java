package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
