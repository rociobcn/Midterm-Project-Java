package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User, String> {
}
