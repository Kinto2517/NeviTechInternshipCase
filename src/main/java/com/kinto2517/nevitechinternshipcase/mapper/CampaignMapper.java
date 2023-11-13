package com.kinto2517.nevitechinternshipcase.mapper;

import com.kinto2517.nevitechinternshipcase.dto.CampaignDTO;
import com.kinto2517.nevitechinternshipcase.dto.CampaignSaveRequest;
import com.kinto2517.nevitechinternshipcase.entity.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CampaignMapper {

    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    @Mapping(source = "user.name", target = "userName")
    CampaignDTO campaignToCampaignDTO(Campaign campaign);

    Campaign campaignSaveRequestToCampaign(CampaignSaveRequest campaignSaveRequest);

    List<CampaignDTO> campaignsToCampaignDTOs(List<Campaign> campaigns);
}