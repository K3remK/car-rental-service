package com.kerem.service;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import com.kerem.entities.ExtraService;

import java.util.List;

public interface IExtraServiceService {
    // Get
    List<ExtraServiceDto> findAll();
    ExtraServiceDto findById(Long id);

    // Update, Save
    ExtraServiceDto update(Long id, ExtraServiceDtoIU extraServiceDtoIU);
    ExtraServiceDto save(ExtraServiceDtoIU extraServiceDtoIU);
    ExtraService getReferenceById(Long id);
}
