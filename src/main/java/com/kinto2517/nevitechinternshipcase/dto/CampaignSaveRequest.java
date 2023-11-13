package com.kinto2517.nevitechinternshipcase.dto;

import com.kinto2517.nevitechinternshipcase.enums.CampaignCategory;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;

import java.time.LocalDateTime;

public record CampaignSaveRequest(
        String title,
        String description,
        CampaignCategory category,
        String userName
) {
}
