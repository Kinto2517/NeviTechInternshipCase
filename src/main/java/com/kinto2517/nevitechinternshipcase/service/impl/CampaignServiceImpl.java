package com.kinto2517.nevitechinternshipcase.service.impl;

import com.kinto2517.nevitechinternshipcase.dto.CampaignDTO;
import com.kinto2517.nevitechinternshipcase.dto.CampaignSaveRequest;
import com.kinto2517.nevitechinternshipcase.dto.CampaignUpdateRequest;
import com.kinto2517.nevitechinternshipcase.dto.StatusHistoryDTO;
import com.kinto2517.nevitechinternshipcase.entity.Campaign;
import com.kinto2517.nevitechinternshipcase.entity.StatusHistory;
import com.kinto2517.nevitechinternshipcase.entity.User;
import com.kinto2517.nevitechinternshipcase.enums.CampaignCategory;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;
import com.kinto2517.nevitechinternshipcase.mapper.CampaignMapper;
import com.kinto2517.nevitechinternshipcase.mapper.StatusHistoryMapper;
import com.kinto2517.nevitechinternshipcase.repository.CampaignRepository;
import com.kinto2517.nevitechinternshipcase.repository.StatusHistoryRepository;
import com.kinto2517.nevitechinternshipcase.repository.UserRepository;
import com.kinto2517.nevitechinternshipcase.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final StatusHistoryRepository statusHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

    @Autowired
    public CampaignServiceImpl(CampaignRepository campaignRepository, UserRepository userRepository,
                               StatusHistoryRepository statusHistoryRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();
        if (campaigns.isEmpty()) {
            logger.error("No campaigns found");
            throw new RuntimeException("No campaigns found");
        }
        logger.info("Campaigns found");
        return CampaignMapper.INSTANCE.campaignsToCampaignDTOs(campaigns);
    }

    @Override
    public CampaignDTO saveCampaign(CampaignSaveRequest campaignSaveRequest) {

        String title = campaignSaveRequest.title();
        String description = campaignSaveRequest.description();
        CampaignCategory category = campaignSaveRequest.category();
        String userName = campaignSaveRequest.userName();

        Campaign existingCampaign = campaignRepository.findByTitleAndDescriptionAndCategory(title, description, category);

        if (existingCampaign != null) {
            if (existingCampaign.isDuplicate()) {
                logger.error("This campaign is already a duplicate");
                throw new RuntimeException("This campaign is already a duplicate");
            }
            existingCampaign.setDuplicate(true);
            campaignRepository.save(existingCampaign);
            return CampaignMapper.INSTANCE.campaignToCampaignDTO(existingCampaign);
        }

        Campaign campaign = CampaignMapper.INSTANCE.campaignSaveRequestToCampaign(campaignSaveRequest);
        User user = userRepository.findByName(userName);

        if (CampaignCategory.TSS.equals(category) || CampaignCategory.OSS.equals(category) || CampaignCategory.OTHER.equals(category)) {
            campaign.setStatus(CampaignStatus.WAITING_APPROVAL);
        } else if (CampaignCategory.HAYAT.equals(category)) {
            campaign.setStatus(CampaignStatus.ACTIVE);
        }

        campaign.setUser(user);
        userRepository.save(user);
        campaignRepository.save(campaign);

        saveStatusToHistory(campaign, campaign.getStatus());

        logger.info("Campaign saved");
        return CampaignMapper.INSTANCE.campaignToCampaignDTO(campaign);
    }

    private void saveStatusToHistory(Campaign campaign, CampaignStatus status) {
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setCampaign(campaign);
        statusHistory.setStatus(status);
        statusHistoryRepository.save(statusHistory);
    }

    @Override
    public CampaignDTO activateCampaign(CampaignUpdateRequest campaignUpdateRequest) {
        String title = campaignUpdateRequest.title();
        String description = campaignUpdateRequest.description();
        CampaignCategory category = campaignUpdateRequest.category();

        Campaign existingCampaign = campaignRepository.findByTitleAndDescriptionAndCategory(title, description, category);

        if (existingCampaign != null) {
            if (existingCampaign.isDuplicate()) {
                logger.error("This campaign is already a duplicate, you can't update it!");
                throw new RuntimeException("This campaign is already a duplicate, you can't update it!");
            }
            if (existingCampaign.getStatus().equals(CampaignStatus.WAITING_APPROVAL)) {
                saveStatusToHistory(existingCampaign, CampaignStatus.ACTIVE);
                campaignRepository.save(existingCampaign);
                existingCampaign.setStatus(CampaignStatus.ACTIVE);

                CampaignDTO campaignDTO = CampaignMapper.INSTANCE.campaignToCampaignDTO(existingCampaign);
                logger.info("Campaign activated");
                return campaignDTO;
            } else {
                logger.error("This campaign is already active, you can't update it!");
                throw new RuntimeException("This campaign is already active, you can't update it!");
            }
        }
        logger.error("Campaign not found!");
        throw new RuntimeException("Campaign not found!");
    }

    @Override
    public CampaignDTO deactivateCampaign(CampaignUpdateRequest campaignUpdateRequest) {
        String title = campaignUpdateRequest.title();
        String description = campaignUpdateRequest.description();
        CampaignCategory category = campaignUpdateRequest.category();

        Campaign existingCampaign = campaignRepository.findByTitleAndDescriptionAndCategory(title, description, category);

        if (existingCampaign != null) {
            if (existingCampaign.isDuplicate()) {
                logger.error("This campaign is already a duplicate, you can't update it!");
                throw new RuntimeException("This campaign is already a duplicate, you can't update it!");
            }
            if (existingCampaign.getStatus().equals(CampaignStatus.ACTIVE)
                    || existingCampaign.getStatus().equals(CampaignStatus.WAITING_APPROVAL)) {

                existingCampaign.setStatus(CampaignStatus.INACTIVE);
                campaignRepository.save(existingCampaign);
                saveStatusToHistory(existingCampaign, CampaignStatus.INACTIVE);
                CampaignDTO campaignDTO = CampaignMapper.INSTANCE.campaignToCampaignDTO(existingCampaign);
                logger.info("Campaign deactivated");
                return campaignDTO;

            } else if (existingCampaign.getStatus().equals(CampaignStatus.INACTIVE)) {
                logger.error("This campaign is already inactive approval, you can't update it!");
                throw new RuntimeException("This campaign is already inactive approval, you can't update it!");
            }
        }
        logger.error("Campaign not found!");
        throw new RuntimeException("Campaign not found!");
    }

    @Override
    public Map<String, Long> getCampaignStatistics() {
        Map<String, Long> statisticsMap = new HashMap<>();

        Long waitingApprovalCount = campaignRepository.getWaitingApprovalStatistics();
        statisticsMap.put("WAITING_APPROVAL", waitingApprovalCount);

        Long activeCount = campaignRepository.getActiveStatistics();
        statisticsMap.put("ACTIVE", activeCount);

        Long inactiveCount = campaignRepository.getInactiveStatistics();
        statisticsMap.put("INACTIVE", inactiveCount);

        logger.info("Campaign statistics found");
        return statisticsMap;
    }

    @Override
    public List<StatusHistoryDTO> getCampaignStatusHistory() {
        List<StatusHistory> statusHistories = statusHistoryRepository.findAll();
        if (statusHistories.isEmpty()) {
            logger.error("No status history found");
            throw new RuntimeException("No status history found");
        }
        logger.info("Status history found");
        return StatusHistoryMapper.INSTANCE.statusHistoriesToStatusHistoryDTOs(statusHistories);
    }

}
