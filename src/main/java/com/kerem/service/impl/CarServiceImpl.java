package com.kerem.service.impl;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.SearchCarParamsDto;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.entities.Car;
import com.kerem.entities.Location;
import com.kerem.mapper.CarMapper;
import com.kerem.mapper.LocationMapper;
import com.kerem.repository.CarRepository;
import com.kerem.repository.specification.CarSpecification;
import com.kerem.service.ICarService;
import com.kerem.service.ILocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements ICarService {


    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ILocationService locationService;
    private final LocationMapper locationMapper;

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
    public CarDto findCarById(UUID barcode) {
        return carMapper.mapGet(carRepository.findById(barcode).orElseThrow(()
                -> new EntityNotFoundException("Car not found! Barcode: " + barcode)));
    }

    @Transactional
    @Override
    public CarDto updateCar(UUID barcode, CarDtoIU carDtoIU) {
        Car car = carRepository.findById(barcode).orElseThrow(()
                ->  new EntityNotFoundException("Car not found! Barcode: " + barcode));

        carMapper.map(carDtoIU, car);

        Location loc = locationMapper.map(locationService.getLocationById(carDtoIU.getLocationCode()));
        car.setLocation(loc);
        carRepository.save(car);

        return carMapper.mapGet(car);
    }

    @Transactional
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

    @Transactional
    @Override
    public CarDto saveCar(CarDtoIU carDtoIU) {
        LocationDto loc = locationService.getLocationById(carDtoIU.getLocationCode());
        Car car = carMapper.map(carDtoIU);
        car.setLocation(locationMapper.map(loc));
        return carMapper.mapGet(carRepository.save(car));
    }
}
