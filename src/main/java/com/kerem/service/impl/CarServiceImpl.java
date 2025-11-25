package com.kerem.service.impl;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchCarParamsDto;
import com.kerem.entities.Car;
import com.kerem.mapper.CarMapper;
import com.kerem.repository.CarRepository;
import com.kerem.repository.specification.CarSpecification;
import com.kerem.service.ICarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements ICarService {


    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarDto> findCarsWithParams(SearchCarParamsDto searchCarParamsDto) {
        Specification<Car> spec = Specification.<Car>unrestricted()
                .and(CarSpecification.isAvailable(searchCarParamsDto.getPickUpDate(), searchCarParamsDto.getDropOffDate()))
                .and(CarSpecification.hasCategory(searchCarParamsDto.getCarCategory()))
                .and(CarSpecification.hasBrand(searchCarParamsDto.getBrand()))
                .and(CarSpecification.hasTransmissionType(searchCarParamsDto.getTransmissionType()))
                .and(CarSpecification.hasStatus(searchCarParamsDto.getStatus()))
                .and(CarSpecification.priceInBetween(searchCarParamsDto.getMinPrice(), searchCarParamsDto.getMaxPrice()))
                .and(CarSpecification.hasLicensePlate(searchCarParamsDto.getLicensePlate()))
                .and(CarSpecification.hasModel(searchCarParamsDto.getModel()))
                .and(CarSpecification.hasMileageLessThan(searchCarParamsDto.getMaxMileage()))
                .and(CarSpecification.numberOfSeatsGreaterThan(searchCarParamsDto.getNumberOfSeats()))
                .and(CarSpecification.hasPickUpLocation(searchCarParamsDto.getPickUpLocationCode()));

        // 2. Execute
        List<Car> foundCars = carRepository.findAll(spec);

        if (foundCars.isEmpty()) {
            throw new EntityNotFoundException("Couldn't find any cars with parameters!");
        }

        // 4. Map to DTOs
        return foundCars.stream()
                .map(carMapper::mapGet)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isAvailable(UUID id, LocalDateTime pickUpDateAndTime, LocalDateTime dropOffDateAndTime) {
        Specification<Car> spec = Specification.<Car>unrestricted()
                .and(CarSpecification.hasId(id))
                .and(CarSpecification.isAvailable(pickUpDateAndTime, dropOffDateAndTime));

        return carRepository.exists(spec);
    }

    @Override
    public Car findCarById(UUID barcode) {
        return carRepository.findById(barcode).orElseThrow(()
                -> new EntityNotFoundException("Car not found! Barcode: " + barcode));
    }

    @Override
    public CarDto updateCar(UUID barcode, CarDtoIU carDtoIU) {
        Car car = carRepository.findById(barcode).orElseThrow(()
                ->  new EntityNotFoundException("Car not found! Barcode: " + barcode));

        carMapper.map(carDtoIU, car);
        carRepository.save(car);

        return carMapper.mapGet(car);
    }

    @Override
    public Boolean deleteCar(UUID barcode) {

       Car car = carRepository.findById(barcode).orElseThrow(()
               -> new EntityNotFoundException("Car not found! Barcode: " + barcode));

       Specification<Car> spec = Specification.<Car>unrestricted()
               .and(CarSpecification.hasId(barcode))
               .and(CarSpecification.hasBeenUsedForAnyReservation());

       List<Car> foundCars = carRepository.findAll(spec);

       if  (foundCars.contains(car)) {
           throw new NotAcceptableStatusException("Car has been used for a reservation! Barcode: " + barcode);
       }

       carRepository.delete(car);


        return true;
    }
}
