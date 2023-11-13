package com.kinto2517.nevitechinternshipcase.controller;

import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;
import com.kinto2517.nevitechinternshipcase.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final CampaignService campaignService;

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    public DashboardController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/classifieds/statistics")
    public ResponseEntity<Map<String, Long>> getCampaignStatistics() {
        return ResponseEntity.ok().body(campaignService.getCampaignStatistics());
    }

}
