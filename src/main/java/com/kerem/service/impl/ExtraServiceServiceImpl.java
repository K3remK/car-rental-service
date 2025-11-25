package com.kerem.service.impl;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import com.kerem.entities.ExtraService;
import com.kerem.mapper.ExtraServiceMapper;
import com.kerem.repository.ExtraServiceRepository;
import com.kerem.service.IExtraServiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExtraServiceServiceImpl implements IExtraServiceService {

    private final ExtraServiceRepository extraServiceRepository;
    private final ExtraServiceMapper extraServiceMapper;

    @Override
    public List<ExtraServiceDto> findAll() {

        List<ExtraService> extraServices = extraServiceRepository.findAll();

        if (extraServices.isEmpty()) {
            throw new EntityNotFoundException("ExtraService not found");
        }

        return extraServices.stream()
                .map(extraServiceMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ExtraServiceDto findById(Long id) {
        return extraServiceMapper.map(extraServiceRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ExtraService not found! ID:" + id)));
    }

    @Transactional
    @Override
    public ExtraServiceDto update(Long id, ExtraServiceDtoIU extraServiceDtoIU) {
        ExtraService dbEx = extraServiceRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ExtraService not found! ID:" + id));

        extraServiceMapper.map(extraServiceDtoIU, dbEx);

        return extraServiceMapper.map(extraServiceRepository.save(dbEx));
    }

    @Transactional
    @Override
    public ExtraServiceDto save(ExtraServiceDtoIU extraServiceDtoIU) {
        return extraServiceMapper.map(extraServiceRepository.save(extraServiceMapper.map(extraServiceDtoIU)));
    }
}
