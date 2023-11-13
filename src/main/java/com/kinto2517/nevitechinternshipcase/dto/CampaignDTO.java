package com.kinto2517.nevitechinternshipcase.dto;

import com.kinto2517.nevitechinternshipcase.enums.CampaignCategory;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;

import java.time.LocalDateTime;

public record CampaignDTO(
    Long id,
    String title,
    String description,
    CampaignCategory category,
    CampaignStatus status,
    boolean duplicate,
    String userName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
