package com.kerem.service.impl;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.entities.Car;
import com.kerem.mapper.CarMapper;
import com.kerem.repository.CarRepository;
import com.kerem.repository.specification.CarSpecification;
import com.kerem.service.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements ICarService {


    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarDto> findCarsWithParams(String carCategory,
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
                .map(carMapper::mapGet)
                .collect(Collectors.toList());
    }

    @Override
    public CarDto saveCar(CarDtoIU newCar) {
        Car car = carMapper.map(newCar);
        Car dbCar = carRepository.save(car);

        return carMapper.mapGet(dbCar);
    }

    @Override
    public CarDto updateCar(UUID barcode, CarDtoIU carDtoIU) {
        Car car = carRepository.findById(barcode).orElse(null);

        if (car == null) {
            // TODO: throw not found exception
        }

        carMapper.map(carDtoIU, car);
        carRepository.save(car);

        return carMapper.mapGet(car);
    }


    @Override
    public Boolean deleteCar(String barcode) {
        return null;
    }
}
