package com.kerem.service.impl;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.carDto.CarDtoIU;
import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import com.kerem.entities.*;
import com.kerem.mapper.*;
import com.kerem.repository.ReservationRepository;
import com.kerem.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ICarService carService;
    private final ICustomerService customerService;
    private final IExtraServiceService extraServiceService;
    private final ILocationService locationService;
    private final CarMapper carMapper;
    private final LocationMapper locationMapper;
    private final ExtraServiceMapper extraServiceMapper;
    private final ReservationMapper reservationMapper;
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public SaveReservationReturnDto saveReservation(ReservationInsertDto reservationInsertDto) {
        CarDto reservationCar = carService.findCarById(reservationInsertDto.getCarBarcodeNumber());

        // CHECK IF THE CAR IS OUT_OF_SERVICE OR HAS ANOTHER RESERVATION FOR THE SPECIFIED DATES
        if (reservationCar.getStatus() == Car.CarStatus.OUT_OF_SERVICE) {
            throw new NotAcceptableStatusException("Car is out of service!");
        }

        if (!carService.isAvailable(reservationCar.getBarcode()
                , reservationInsertDto.getPickUpDateAndTime(), reservationInsertDto.getDropOffDateAndTime())) {
            throw new NotAcceptableStatusException("Car has another reservation for the specified dates!");
        }

        Customer customer = customerMapper.map(customerService.findCustomerBySsn(reservationInsertDto.getSsn()));
        Location pickUpLoc = locationMapper.map(locationService.getLocationById(reservationInsertDto.getPickUpLocationCode()));
        Location dropOffLoc = locationMapper.map(locationService.getLocationById(reservationInsertDto.getDropOffLocationCode()));

        Reservation reservation = reservationMapper.map(reservationInsertDto);

        for (long id :  reservationInsertDto.getExtraServiceIds()) {
            ExtraService service = extraServiceMapper.map(extraServiceService.findById(id));
            reservation.getExtras().add(service);
        }

        reservation.setCar(carMapper.map(reservationCar));
        reservation.setStatus(Reservation.Status.ACTIVE);
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setCustomer(customer);
        reservation.setPickUpLocation(pickUpLoc);
        reservation.setDropOffLocation(dropOffLoc);

        Double totalAmount = calculateTotalAmount(reservation);

        reservationRepository.save(reservation);

        SaveReservationReturnDto reservationDto = reservationMapper.mapToSaveReservationDto(reservation);
        reservationDto.setTotalAmount(totalAmount);
        reservationDto.setCustomerSsn(customer.getSsn());
        reservationDto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());

        return reservationDto;
    }

    @Override
    public List<RentedCarDto> getAllCurrentlyReservedCars() {
        LocalDateTime today = LocalDateTime.now();
        List<Reservation> activeReservationsToday = reservationRepository.getActiveReservationsOn(today);

        if  (activeReservationsToday.isEmpty()) {
            throw new EntityNotFoundException("Could not find any currently active reservations! Hence, There is no currently reserved car!");
        }

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

    @Transactional
    @Override
    public Boolean addExtraServiceToReservation(String reservationNumber, Long extraServiceId) {
        Reservation reservation = reservationRepository.findById(reservationNumber).orElseThrow(() -> new EntityNotFoundException("Reservation not found! ReservationNumber: " + reservationNumber));
        ExtraService extraService = extraServiceMapper.map(extraServiceService.findById(extraServiceId));

        if (reservation.getExtras().contains(extraService)) {
            return false;
        }

        reservation.getExtras().add(extraService);
        reservationRepository.save(reservation);

        return true;
    }

    @Transactional
    @Override
    public Boolean returnCar(String reservationNumber) {

        Reservation reservation = reservationRepository.findById(reservationNumber).orElseThrow(()
                -> new  EntityNotFoundException("Reservation not found! ReservationNumber: " + reservationNumber));

        Car dbCar = reservation.getCar();

        if (dbCar == null) {
            throw new EntityNotFoundException("Car not found!");
        }

        reservation.setStatus(Reservation.Status.COMPLETED);
        reservation.setDropOffDateAndTime(LocalDateTime.now());

        if (dbCar.getLocation() != reservation.getDropOffLocation())
        {
            dbCar.setLocation(reservation.getDropOffLocation());
        }


        reservationRepository.save(reservation);

        CarDtoIU carDtoIU = carMapper.mapIU(dbCar);
        carDtoIU.setLocationCode(dbCar.getLocation().getCode());

        carService.updateCar(dbCar.getBarcode(), carDtoIU);

        return true;
    }

    @Transactional
    @Override
    public Boolean cancelReservation(String reservationNumber) {
        Reservation foundReservation = reservationRepository.findById(reservationNumber).orElseThrow(()
                -> new EntityNotFoundException("Reservation not found! ReservationNumber: " + reservationNumber));

        foundReservation.setStatus(Reservation.Status.CANCELLED);
        reservationRepository.save(foundReservation);

        return true;
    }

    @Transactional
    @Override
    public Boolean deleteReservation(String reservationNumber) {
        Reservation reservation = reservationRepository.findById(reservationNumber).orElseThrow(()
                -> new EntityNotFoundException("Reservation not found! ReservationNumber: " + reservationNumber));

        reservationRepository.delete(reservation);

        return true;
    }

    @Override
    public List<ReservationDto> getAllReservations() {

        List<Reservation> reservations = reservationRepository.findAll();

        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("Reservations not found!");
        }

        for (Reservation reservation : reservations) {
            reservation.getCustomer().setReservations(null);
        }

        return reservations.stream()
                .map(reservationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservationByReservationNumber(String reservationNumber) {

        Reservation dbRes = reservationRepository.findById(reservationNumber).orElseThrow(() -> new EntityNotFoundException("Reservation not found! ReservationNumber: " + reservationNumber));

        dbRes.getCustomer().setReservations(null);

        return reservationMapper.map(dbRes);
    }

    private static Long calculateDaysInBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    private Double calculateTotalAmount(Reservation reservation) {
        Double totalAmount = reservation.getCar().getDailyPrice() *
                calculateDaysInBetween(reservation.getPickUpDateAndTime(),
                        reservation.getDropOffDateAndTime());

        for (ExtraService extraService : reservation.getExtras()) {
            totalAmount += extraService.getTotalPrice();
        }
        return totalAmount;
    }

    public static Double calculateTotalAmount(ReservationDto reservationDto) {
        Double totalAmount = reservationDto.getCar().getDailyPrice() *
                calculateDaysInBetween(reservationDto.getPickUpDateAndTime(),
                        reservationDto.getDropOffDateAndTime());

        for (ExtraServiceDto extraService : reservationDto.getExtras()) {
            totalAmount += extraService.getTotalPrice();
        }
        return totalAmount;
    }
}
