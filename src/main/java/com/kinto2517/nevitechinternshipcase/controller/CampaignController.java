package com.kinto2517.nevitechinternshipcase.controller;

import com.kinto2517.nevitechinternshipcase.dto.CampaignDTO;
import com.kinto2517.nevitechinternshipcase.dto.CampaignSaveRequest;
import com.kinto2517.nevitechinternshipcase.dto.CampaignUpdateRequest;
import com.kinto2517.nevitechinternshipcase.dto.StatusHistoryDTO;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;
import com.kinto2517.nevitechinternshipcase.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns() {
        logger.info("getAllCampaigns() called");
        return ResponseEntity.ok().body(campaignService.getAllCampaigns());
    }

    @PostMapping("/save")
    public ResponseEntity<CampaignDTO> saveCampaign(CampaignSaveRequest campaignSaveRequest) {
        logger.info("saveCampaign() called");
        return ResponseEntity.ok().body(campaignService.saveCampaign(campaignSaveRequest));
    }

    @PutMapping("/activate")
    public ResponseEntity<CampaignDTO> activateCampaign(CampaignUpdateRequest campaignUpdateRequest) {
        logger.info("activateCampaign() called");
        return ResponseEntity.ok().body(campaignService.activateCampaign(campaignUpdateRequest));
    }

    @PutMapping("/deactivate")
    public ResponseEntity<CampaignDTO> deactivateCampaign(CampaignUpdateRequest campaignUpdateRequest) {
        logger.info("deactivateCampaign() called");
        return ResponseEntity.ok().body(campaignService.deactivateCampaign(campaignUpdateRequest));
    }

    @GetMapping("/status-history")
    public ResponseEntity<List<StatusHistoryDTO>> getCampaignStatusHistory() {
        logger.info("getCampaignStatusHistory() called");
        return ResponseEntity.ok().body(campaignService.getCampaignStatusHistory());
    }
}
