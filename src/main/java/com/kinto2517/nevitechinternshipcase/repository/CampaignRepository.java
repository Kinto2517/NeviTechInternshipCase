package com.kinto2517.nevitechinternshipcase.repository;

import com.kinto2517.nevitechinternshipcase.entity.Campaign;
import com.kinto2517.nevitechinternshipcase.enums.CampaignCategory;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Campaign findByTitleAndDescriptionAndCategory(String title, String description, CampaignCategory category);

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = 'WAITING_APPROVAL'")
    Long getWaitingApprovalStatistics();

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = 'ACTIVE'")
    Long getActiveStatistics();

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.status = 'INACTIVE'")
    Long getInactiveStatistics();

}