package com.kinto2517.nevitechinternshipcase.unit.service;

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
import com.kinto2517.nevitechinternshipcase.service.impl.CampaignServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StatusHistoryRepository statusHistoryRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCampaigns_shouldReturnCampaignDTOList() {
        List<Campaign> campaigns = Arrays.asList(
                new Campaign(1L, "Campaign 1", "Description 1", CampaignCategory.TSS, CampaignStatus.WAITING_APPROVAL, false, new User(), null, null, null),
                new Campaign(2L, "Campaign 2", "Description 2", CampaignCategory.HAYAT, CampaignStatus.ACTIVE, false, new User(), null, null, null)
        );
        when(campaignRepository.findAll()).thenReturn(campaigns);

        List<CampaignDTO> campaignDTOs = campaignService.getAllCampaigns();

        assertEquals(2, campaignDTOs.size());
        assertEquals("Campaign 1", campaignDTOs.get(0).title());
        assertEquals("Campaign 2", campaignDTOs.get(1).title());
    }
    @Captor
    private ArgumentCaptor<Campaign> campaignCaptor;
    @Test
    void saveCampaign_shouldCreateNewCampaign() {
        CampaignSaveRequest saveRequest = new CampaignSaveRequest("New Campaign", "New Description", CampaignCategory.TSS, "user123");
        User user = new User(1L, "John Doe", "john.doe", "password123", null);
        when(userRepository.findByName("user123")).thenReturn(user);
        when(campaignRepository.findByTitleAndDescriptionAndCategory(anyString(), anyString(), any())).thenReturn(null);

        CampaignDTO savedCampaignDTO = campaignService.saveCampaign(saveRequest);

        assertNotNull(savedCampaignDTO);
        assertEquals("New Campaign", savedCampaignDTO.title());
        assertEquals("New Description", savedCampaignDTO.description());
        assertEquals(CampaignStatus.WAITING_APPROVAL, savedCampaignDTO.status());

        verify(campaignRepository, times(1)).save(campaignCaptor.capture());
        Campaign capturedCampaign = campaignCaptor.getValue();

        assertEquals("New Campaign", capturedCampaign.getTitle());
        assertEquals("New Description", capturedCampaign.getDescription());
        assertEquals(CampaignStatus.WAITING_APPROVAL, capturedCampaign.getStatus());
        assertEquals(user, capturedCampaign.getUser());
    }

    @Test
    void saveCampaign_shouldUpdateExistingDuplicateCampaign() {
        CampaignSaveRequest saveRequest = new CampaignSaveRequest("Existing Campaign", "Description", CampaignCategory.TSS, "user123");
        User user = new User(1L, "John Doe", "john.doe", "password123", null);
        Campaign existingCampaign = new Campaign(1L, "Existing Campaign", "Description", CampaignCategory.TSS, CampaignStatus.WAITING_APPROVAL, false, user, null, null, null);

        when(userRepository.findByName("user123")).thenReturn(user);
        when(campaignRepository.findByTitleAndDescriptionAndCategory(anyString(), anyString(), any())).thenReturn(existingCampaign);

        CampaignDTO savedCampaignDTO = campaignService.saveCampaign(saveRequest);

        assertNotNull(savedCampaignDTO);
        assertEquals("Existing Campaign", savedCampaignDTO.title());
        assertEquals("Description", savedCampaignDTO.description());
        assertEquals(CampaignStatus.WAITING_APPROVAL, savedCampaignDTO.status());

        verify(campaignRepository, times(1)).save(campaignCaptor.capture());
        Campaign capturedCampaign = campaignCaptor.getValue();

        assertTrue(capturedCampaign.isDuplicate());
        assertEquals("Existing Campaign", capturedCampaign.getTitle());
        assertEquals("Description", capturedCampaign.getDescription());
        assertEquals(CampaignStatus.WAITING_APPROVAL, capturedCampaign.getStatus());
        assertEquals(user, capturedCampaign.getUser());
    }

    @Test
    void activateCampaign_shouldActivateExistingCampaign() {
        CampaignUpdateRequest updateRequest = new CampaignUpdateRequest("Existing Campaign", "Description", CampaignCategory.TSS);
        User user = new User(1L, "John Doe", "john.doe", "password123", null);
        Campaign existingCampaign = new Campaign(1L, "Existing Campaign", "Description", CampaignCategory.TSS, CampaignStatus.WAITING_APPROVAL, false, user, null, null, null);

        when(campaignRepository.findByTitleAndDescriptionAndCategory(anyString(), anyString(), any())).thenReturn(existingCampaign);

        CampaignDTO activatedCampaignDTO = campaignService.activateCampaign(updateRequest);

        assertNotNull(activatedCampaignDTO);
        assertEquals("Existing Campaign", activatedCampaignDTO.title());
        assertEquals("Description", activatedCampaignDTO.description());
        assertEquals(CampaignStatus.ACTIVE, activatedCampaignDTO.status());

        verify(campaignRepository, times(1)).save(existingCampaign);
        verify(statusHistoryRepository, times(1)).save(any(StatusHistory.class));

        assertEquals(CampaignStatus.ACTIVE, existingCampaign.getStatus());
    }

    @Test
    void deactivateCampaign_shouldDeactivateExistingCampaign() {
        CampaignUpdateRequest updateRequest = new CampaignUpdateRequest("Existing Campaign", "Description", CampaignCategory.TSS);
        User user = new User(1L, "John Doe", "john.doe", "password123", null);
        Campaign existingCampaign = new Campaign(1L, "Existing Campaign", "Description", CampaignCategory.TSS, CampaignStatus.ACTIVE, false, user, null, null, null);

        when(campaignRepository.findByTitleAndDescriptionAndCategory(anyString(), anyString(), any())).thenReturn(existingCampaign);

        CampaignDTO deactivatedCampaignDTO = campaignService.deactivateCampaign(updateRequest);

        assertNotNull(deactivatedCampaignDTO);
        assertEquals("Existing Campaign", deactivatedCampaignDTO.title());
        assertEquals("Description", deactivatedCampaignDTO.description());
        assertEquals(CampaignStatus.INACTIVE, deactivatedCampaignDTO.status());

        verify(campaignRepository, times(1)).save(existingCampaign);
        verify(statusHistoryRepository, times(1)).save(any(StatusHistory.class));

        assertEquals(CampaignStatus.INACTIVE, existingCampaign.getStatus());
    }
}
