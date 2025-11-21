package com.kerem.service.impl;

import com.kerem.dto.carDto.CarGetRequestDto;
import com.kerem.entities.Car;
import com.kerem.mapper.CarMapper;
import com.kerem.repository.CarRepository;
import com.kerem.repository.specification.CarSpecification;
import com.kerem.service.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements ICarService {


    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarGetRequestDto> findCarsWithParams(String carCategory,
                                                     String carBrand,
                                                     String transmissionType,
                                                     String status,
                                                     Double minPrice,
                                                     Double maxPrice,
                                                     String licensePlateNumber,
                                                     Long maxMileage,
                                                     String model,
                                                     Date pickUpDate,
                                                     Date dropOffDate,
                                                     Integer numberOfSeats,
                                                     Long pickUpLocationCode) {

        Specification<Car> spec = Specification.<Car>unrestricted()
                .and(CarSpecification.isAvailable(pickUpDate, dropOffDate))
                .and(CarSpecification.hasCategory(carCategory))
                .and(CarSpecification.hasBrand(carBrand))
                .and(CarSpecification.hasTransmissionType(transmissionType))
                .and(CarSpecification.hasStatus(status))
                .and(CarSpecification.priceInBetween(minPrice, maxPrice))
                .and(CarSpecification.hasLicensePlate(licensePlateNumber))
                .and(CarSpecification.hasModel(model))
                .and(CarSpecification.hasMileageLessThan(maxMileage))
                .and(CarSpecification.numberOfSeatsGreaterThan(numberOfSeats))
                .and(CarSpecification.hasPickUpLocation(pickUpLocationCode));

        // 2. Execute
        List<Car> foundCars = carRepository.findAll(spec);

        if (foundCars.isEmpty()) {
            // TODO: throw not found exception and catch it in the GlobalExceptionHandler
        }

        // 4. Map to DTOs
        return foundCars.stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteCar(String barcode) {
        return null;
    }
}
