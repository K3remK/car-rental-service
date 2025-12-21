package com.kerem.controller;

import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;

public interface IExtraServiceController {
    ResponseEntity<List<ExtraServiceDto>> getAllExtraServices();

    ResponseEntity<ExtraServiceDto> getExtraServiceById(Long id);

    ResponseEntity<ExtraServiceDto> saveExtraService(ExtraServiceDtoIU extraServiceDto);

    ResponseEntity<ExtraServiceDto> updateExtraService(Long id, ExtraServiceDtoIU extraServiceDto);
}
