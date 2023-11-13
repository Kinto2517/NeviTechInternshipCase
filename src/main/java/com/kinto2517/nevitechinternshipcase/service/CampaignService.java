package com.kinto2517.nevitechinternshipcase.service;

import com.kinto2517.nevitechinternshipcase.dto.CampaignDTO;
import com.kinto2517.nevitechinternshipcase.dto.CampaignSaveRequest;
import com.kinto2517.nevitechinternshipcase.dto.CampaignUpdateRequest;
import com.kinto2517.nevitechinternshipcase.dto.StatusHistoryDTO;
import com.kinto2517.nevitechinternshipcase.entity.Campaign;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;

import java.util.List;
import java.util.Map;

public interface CampaignService {

    List<CampaignDTO> getAllCampaigns();

    CampaignDTO saveCampaign(CampaignSaveRequest campaignSaveRequest);

    CampaignDTO activateCampaign(CampaignUpdateRequest campaignUpdateRequest);

    CampaignDTO deactivateCampaign(CampaignUpdateRequest campaignUpdateRequest);

    Map<String, Long> getCampaignStatistics();

    List<StatusHistoryDTO> getCampaignStatusHistory();
}
