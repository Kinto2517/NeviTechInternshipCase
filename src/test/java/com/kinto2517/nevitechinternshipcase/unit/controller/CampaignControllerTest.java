package com.kinto2517.nevitechinternshipcase.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.nevitechinternshipcase.controller.CampaignController;
import com.kinto2517.nevitechinternshipcase.dto.CampaignDTO;
import com.kinto2517.nevitechinternshipcase.dto.CampaignSaveRequest;
import com.kinto2517.nevitechinternshipcase.dto.StatusHistoryDTO;
import com.kinto2517.nevitechinternshipcase.enums.CampaignCategory;
import com.kinto2517.nevitechinternshipcase.enums.CampaignStatus;
import com.kinto2517.nevitechinternshipcase.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CampaignControllerTest {

    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private CampaignController campaignController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MockMvc mockMvc;

    public CampaignControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
    }

    @Test
    void getAllCampaigns_shouldReturnAllCampaigns() throws Exception {
        when(campaignService.getAllCampaigns()).thenReturn(Arrays.asList(
                new CampaignDTO(1L, "Campaign 1", "Description 1", CampaignCategory.TSS, CampaignStatus.WAITING_APPROVAL, false, null, null, null),
                new CampaignDTO(2L, "Campaign 2", "Description 2", CampaignCategory.HAYAT, CampaignStatus.ACTIVE, false, null, null, null)
        ));

        mockMvc.perform(get("/api/v1/campaign/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Campaign 1"))
                .andExpect(jsonPath("$[1].title").value("Campaign 2"));

        verify(campaignService, times(1)).getAllCampaigns();
    }

    @Test
    void saveCampaign_shouldReturnSavedCampaign() throws Exception {
        CampaignSaveRequest campaignSaveRequest = new CampaignSaveRequest("Campaign 1", "Description 1", CampaignCategory.TSS, "John Doe");
        when(campaignService.saveCampaign(any(CampaignSaveRequest.class))).thenReturn(new CampaignDTO(1L, "Campaign 1", "Description 1", CampaignCategory.TSS, CampaignStatus.WAITING_APPROVAL, false, null, null, null));

        mockMvc.perform(post("/api/v1/campaign/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campaignSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Campaign 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));

        verify(campaignService, times(1)).saveCampaign(any(CampaignSaveRequest.class));
    }

    @Test
    void getCampaignStatusHistory_shouldReturnCampaignStatusHistory() throws Exception {
        List<StatusHistoryDTO> statusHistoryList = Arrays.asList(
                new StatusHistoryDTO(1L, "WAITING_APPROVAL", null, 1L),
                new StatusHistoryDTO(2L, "ACTIVE", null, 1L)
        );
        when(campaignService.getCampaignStatusHistory()).thenReturn(statusHistoryList);

        mockMvc.perform(get("/api/v1/campaign/status-history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].status").value("WAITING_APPROVAL"))
                .andExpect(jsonPath("$[1].status").value("ACTIVE"));

        verify(campaignService, times(1)).getCampaignStatusHistory();
    }
}
