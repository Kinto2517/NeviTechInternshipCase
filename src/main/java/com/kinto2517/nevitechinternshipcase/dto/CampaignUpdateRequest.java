package com.kinto2517.nevitechinternshipcase.dto;

import com.kinto2517.nevitechinternshipcase.enums.CampaignCategory;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;

public record CampaignUpdateRequest(
        String title,
        String description,
        CampaignCategory category
) {
}
