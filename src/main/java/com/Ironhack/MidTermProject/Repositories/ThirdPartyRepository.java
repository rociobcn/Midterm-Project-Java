package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.User.AccountHolder;
import com.Ironhack.MidTermProject.Models.User.ThirdParty;
import com.Ironhack.MidTermProject.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<User, String> {
    Optional<ThirdParty> findByUsername(String name);
}
