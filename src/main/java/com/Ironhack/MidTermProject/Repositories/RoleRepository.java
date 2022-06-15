package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
