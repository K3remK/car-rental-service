package com.kerem.service.impl;

import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;
import com.kerem.entities.Car;
import com.kerem.entities.Reservation;
import com.kerem.mapper.CarMapper;
import com.kerem.mapper.ReservationMapper;
import com.kerem.repository.ReservationRepository;
import com.kerem.service.ICarService;
import com.kerem.service.IReservationService;
import lombok.RequiredArgsConstructor;
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
    private final CarMapper carMapper;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDto saveReservation(ReservationDtoIU reservationDtoIU) {
        return null;
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
        return null;
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
        return null;
    }

    @Override
    public Boolean deleteReservation(Long reservationNumber) {
        //TODO: do not use Casecade.DELETE
        return null;
    }

    private Long calculateDaysInBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
