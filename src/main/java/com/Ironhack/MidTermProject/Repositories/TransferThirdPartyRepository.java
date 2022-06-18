package com.Ironhack.MidTermProject.Repositories;

import com.Ironhack.MidTermProject.Models.Account.Transfer;
import com.Ironhack.MidTermProject.Models.Account.TransferThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferThirdPartyRepository extends JpaRepository<TransferThirdParty, Long> {
}
