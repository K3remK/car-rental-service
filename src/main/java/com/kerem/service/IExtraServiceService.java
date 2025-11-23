package com.kerem.service;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.ExtraService;

public interface IExtraServiceService {
    ExtraService findById(Long id);
}
