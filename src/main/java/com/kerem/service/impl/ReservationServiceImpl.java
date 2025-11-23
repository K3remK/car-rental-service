package com.kerem.service.impl;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;
import com.kerem.entities.*;
import com.kerem.mapper.CarMapper;
import com.kerem.mapper.ExtraServiceMapper;
import com.kerem.mapper.ReservationMapper;
import com.kerem.repository.ReservationRepository;
import com.kerem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ICarService carService;
    private final ICustomerService customerService;
    private final IExtraServiceService extraServiceService;
    private final ILocationService locationService;
    private final CarMapper carMapper;
    private final ExtraServiceMapper extraServiceMapper;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDto saveReservation(ReservationDtoIU reservationDtoIU) {
        Car reservationCar = carService.findCarById(reservationDtoIU.getCarBarcodeNumber());

        // CHECK IF THE CAR IS OUT_OF_SERVICE OR HAS ANOTHER RESERVATION FOR THE SPECIFIED DATES
        if (reservationCar.getStatus() == Car.CarStatus.OUT_OF_SERVICE || !carService.isAvailable(reservationCar.getBarcode()
        , reservationDtoIU.getPickUpDateAndTime(), reservationDtoIU.getDropOffDateAndTime())) {
            //TODO: throw not acceptable status error
        }

        Customer customer = customerService.getCustomerBySsn(reservationDtoIU.getCustomerSsn());
        Location pickUpLoc = locationService.getLocationById(reservationDtoIU.getPickUpLocationCode());
        Location dropOffLoc = locationService.getLocationById(reservationDtoIU.getDropOffLocationCode());

        Reservation reservation = reservationMapper.map(reservationDtoIU);

        for (long id :  reservationDtoIU.getExtraServiceIds()) {
            ExtraService service = extraServiceService.findById(id);
            reservation.getExtras().add(service);
        }

        reservation.setCar(reservationCar);
        reservation.setStatus(Reservation.Status.ACTIVE);
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setPickUpLocation(pickUpLoc);
        reservation.setDropOffLocation(dropOffLoc);

        Double totalAmount = reservation.getCar().getDailyPrice() *
                calculateDaysInBetween(reservation.getPickUpDateAndTime(),
                        reservation.getDropOffDateAndTime());

        for (ExtraService extraService : reservation.getExtras()) {
            totalAmount += extraService.getTotalPrice();
        }

        reservationRepository.save(reservation);

        ReservationDto  reservationDto = reservationMapper.map(reservation);
        reservationDto.setTotalAmount(totalAmount);
        reservationDto.setCustomerSsn(customer.getSsn());
        reservationDto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());

        return reservationDto;
    }

    @Override
    public List<RentedCarDto> getAllCurrentlyReservedCars() {
        LocalDateTime today = LocalDateTime.now();
        List<Reservation> activeReservationsToday = reservationRepository.getActiveReservationsOn(today);

        List<RentedCarDto> res = new ArrayList<>();

        for (Reservation reservation : activeReservationsToday) {
            Car car = reservation.getCar();
            RentedCarDto resDto = new RentedCarDto();

            // map the car and reservations related fields to RentedCarGetRequestDto
            carMapper.map(car, resDto);
            reservationMapper.mapReservationToRentedCarDto(reservation, resDto);
            resDto.setCustomerName(reservation.getCustomer().getFirstName() + " " + reservation.getCustomer().getLastName());


            // calculate the day count for the reservation
            resDto.setReservationDayCount(calculateDaysInBetween(reservation.getPickUpDateAndTime(),
                    reservation.getDropOffDateAndTime()));
            res.add(resDto);
        }

        return res;
    }

    @Override
    public Boolean addExtraServiceToReservation(Long reservationNumber, Long extraServiceId) {
        Reservation reservation = reservationRepository.findById(reservationNumber).orElse(null);
        ExtraService extraService = extraServiceService.findById(extraServiceId);
        if (reservation == null) {
            // TODO: throw not found exception
        }

        if (reservation.getExtras().contains(extraService)) {
            return false;
        }

        reservation.getExtras().add(extraService);
        reservationRepository.save(reservation);

        return true;
    }

    @Override
    public Boolean returnCar(Long reservationNumber) {

        Reservation reservation = reservationRepository.findById(reservationNumber).orElse(null);

        if (reservation == null) {
            // TODO: throw not found exception
        }

        Car dbCar = reservation.getCar();

        if (dbCar == null) {
            // TODO: throw not found exception
        }

        reservation.setStatus(Reservation.Status.COMPLETED);
        reservation.setDropOffDateAndTime(LocalDateTime.now());

        if (dbCar.getLocation() != reservation.getDropOffLocation())
        {
            dbCar.setLocation(reservation.getDropOffLocation());
        }


        reservationRepository.save(reservation);
        carService.updateCar(dbCar.getBarcode(), carMapper.mapIU(dbCar));

        return true;
    }

    @Override
    public Boolean cancelReservation(Long reservationNumber) {
        Reservation foundReservation = reservationRepository.findById(reservationNumber).orElse(null);
        if (foundReservation == null) {
            // TODO: throw not found exception
        }

        foundReservation.setStatus(Reservation.Status.CANCELLED);
        reservationRepository.save(foundReservation);

        return true;
    }

    @Override
    public Boolean deleteReservation(Long reservationNumber) {
        //TODO: do not use Cascade.DELETE

        return null;
    }

    private Long calculateDaysInBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
