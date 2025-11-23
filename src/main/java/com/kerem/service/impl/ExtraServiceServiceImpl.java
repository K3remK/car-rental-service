package com.kerem.service.impl;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.ExtraService;
import com.kerem.mapper.ExtraServiceMapper;
import com.kerem.repository.ExtraServiceRepository;
import com.kerem.service.IExtraServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtraServiceServiceImpl implements IExtraServiceService {

    private final ExtraServiceRepository extraServiceRepository;
    private final ExtraServiceMapper extraServiceMapper;

    @Override
    public ExtraService findById(Long id) {
        ExtraService extraService = extraServiceRepository.findById(id).orElse(null);

        if (extraService == null) {
            // TODO: throw not found exception
        }

        return extraService;
    }
}
