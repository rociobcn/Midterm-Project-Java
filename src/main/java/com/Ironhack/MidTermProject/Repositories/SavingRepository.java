package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.Account.Saving;
import com.Ironhack.MidTermProject.Models.Account.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
}
