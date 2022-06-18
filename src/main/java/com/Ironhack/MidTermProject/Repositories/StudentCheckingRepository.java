package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.Account.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
}
