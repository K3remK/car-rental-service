package com.kerem.service.impl;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.entities.ExtraService;
import com.kerem.mapper.ExtraServiceMapper;
import com.kerem.repository.ExtraServiceRepository;
import com.kerem.service.IExtraServiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtraServiceServiceImpl implements IExtraServiceService {

    private final ExtraServiceRepository extraServiceRepository;
    private final ExtraServiceMapper extraServiceMapper;

    @Override
    public ExtraService findById(Long id) {
        return extraServiceRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ExtraService not found! ID:" + id));
    }
}
