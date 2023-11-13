package com.kinto2517.nevitechinternshipcase.mapper;

import com.kinto2517.nevitechinternshipcase.dto.StatusHistoryDTO;
import com.kinto2517.nevitechinternshipcase.entity.StatusHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface StatusHistoryMapper {

    StatusHistoryMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(StatusHistoryMapper.class);

    @Mapping(source = "campaign.id", target = "campaignId")
    StatusHistoryDTO statusHistoryToStatusHistoryDTO(StatusHistory statusHistory);

    List<StatusHistoryDTO> statusHistoriesToStatusHistoryDTOs(List<StatusHistory> statusHistories);

}
