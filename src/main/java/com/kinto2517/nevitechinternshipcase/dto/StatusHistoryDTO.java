package com.kinto2517.nevitechinternshipcase.dto;

import com.kinto2517.nevitechinternshipcase.entity.Campaign;

import java.time.LocalDateTime;

public record StatusHistoryDTO(
        Long id,
        String status,
        LocalDateTime statusChangedAt,
        Long campaignId
) {
}
