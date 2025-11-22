package com.kerem.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.kerem.dto.reservationDto.RentedCarGetRequestDto;
import com.kerem.dto.reservationDto.ReservationGetRequestDto;
import com.kerem.dto.reservationDto.ReservationInsertRequestDto;
import com.kerem.entities.Car;
import com.kerem.entities.Reservation;
import com.kerem.mapper.CarMapper;
import com.kerem.mapper.ReservationMapper;
import com.kerem.repository.ReservationRepository;
import com.kerem.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final CarMapper carMapper;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationGetRequestDto saveReservation(ReservationInsertRequestDto reservationInsertRequestDto) {
        return null;
    }

    @Override
    public List<RentedCarGetRequestDto> getAllCurrentlyReservedCars() {
        LocalDateTime today = LocalDateTime.now();
        List<Reservation> activeReservationsToday = reservationRepository.getActiveReservationsOn(today);

        List<RentedCarGetRequestDto> res = new ArrayList<>();

        for (Reservation reservation : activeReservationsToday) {
            Car car = reservation.getCar();
            RentedCarGetRequestDto resDto = new RentedCarGetRequestDto();

            // map the car and reservations related fields to RentedCarGetRequestDto
            carMapper.mapCarToRentedCarDto(car, resDto);
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
        return null;
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
