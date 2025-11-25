package com.kerem;

import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import com.kerem.entities.*;
import com.kerem.repository.*;
import com.kerem.service.IReservationService;
import com.kerem.starter.CarRentalServiceApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = CarRentalServiceApplication.class)
@ActiveProfiles("test")
@Transactional
public class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ExtraServiceRepository extraServiceRepository;

    private Car savedCar;
    private Customer savedCustomer;
    private Location pickUpLoc;
    private Location dropOffLoc;
    private ExtraService extra1;

    @BeforeEach
    void setUp() {
        // 1. Setup Location
        pickUpLoc = locationRepository.save(new Location(null, "PickUp Point"));
        dropOffLoc = locationRepository.save(new Location(null, "DropOff Point"));

        // 2. Setup Car
        Car car = new Car();
        car.setBrand("Audi");
        car.setModel("A3");
        car.setLicensePlateNumber("34AUD123");
        car.setDailyPrice(100.0);
        car.setTransmissionType(Car.TransmissionType.AUTOMATIC);
        car.setCategoryType(Car.Category.COMPACT);
        car.setCategoryDescription("Compact");
        car.setStatus(Car.CarStatus.IN_SERVICE);
        car.setLocation(pickUpLoc);
        savedCar = carRepository.save(car);

        // 3. Setup Customer
        Customer customer = new Customer();
        customer.setSsn("99999999999");
        customer.setFirstName("Test");
        customer.setLastName("Client");
        customer.setPhoneNumber("5559998877");
        customer.setDrivingLicenseNumber("DLTEST99");
        savedCustomer = customerRepository.save(customer);

        // 4. Setup Extra Service
        extra1 = extraServiceRepository.save(new ExtraService(null, "GPS", "Nav", 10.0, ExtraService.ExtraCategory.TECHNOLOGY));
    }

    @Test
    void testSaveReservation() {
        // Arrange
        ReservationInsertDto insertDto = new ReservationInsertDto();
        insertDto.setCarBarcodeNumber(savedCar.getBarcode());
        insertDto.setCustomerSsn(savedCustomer.getSsn());
        insertDto.setPickUpLocationCode(pickUpLoc.getCode());
        insertDto.setDropOffLocationCode(dropOffLoc.getCode());

        // Dates: Tomorrow to Day After Tomorrow (1 day)
        LocalDateTime pickUp = LocalDateTime.now().plusDays(1);
        LocalDateTime dropOff = LocalDateTime.now().plusDays(2);
        insertDto.setPickUpDateAndTime(pickUp);
        insertDto.setDropOffDateAndTime(dropOff);

        List<Long> extras = new ArrayList<>();
        extras.add(extra1.getId());
        insertDto.setExtraServiceIds(extras);

        // Act
        SaveReservationReturnDto result = reservationService.saveReservation(insertDto);

        // Assert
        Assertions.assertNotNull(result.getReservationNumber());
        // Price: 1 day * 100 + 10 (extra) = 110
        Assertions.assertEquals(110.0, result.getTotalAmount());
        Assertions.assertEquals(savedCustomer.getSsn(), result.getCustomerSsn());
    }

    @Test
    void testSaveReservation_CarNotAvailable() {
        // Create an existing reservation overlapping dates
        Reservation existing = new Reservation();
        existing.setCar(savedCar);
        existing.setCustomer(savedCustomer);
        existing.setPickUpDateAndTime(LocalDateTime.now().plusDays(1));
        existing.setDropOffDateAndTime(LocalDateTime.now().plusDays(3));
        existing.setStatus(Reservation.Status.ACTIVE);
        reservationRepository.save(existing);

        // Try to book same dates
        ReservationInsertDto insertDto = new ReservationInsertDto();
        insertDto.setCarBarcodeNumber(savedCar.getBarcode());
        insertDto.setPickUpDateAndTime(LocalDateTime.now().plusDays(1));
        insertDto.setDropOffDateAndTime(LocalDateTime.now().plusDays(2));
        insertDto.setCustomerSsn(savedCustomer.getSsn());
        insertDto.setPickUpLocationCode(pickUpLoc.getCode());
        insertDto.setDropOffLocationCode(dropOffLoc.getCode());
        insertDto.setExtraServiceIds(new ArrayList<>());

        // Act & Assert
        Assertions.assertThrows(NotAcceptableStatusException.class, () -> reservationService.saveReservation(insertDto));
    }

    @Test
    void testReturnCar() {
        // Arrange: Create Active Reservation
        Reservation reservation = new Reservation();
        reservation.setCar(savedCar);
        reservation.setCustomer(savedCustomer);
        reservation.setPickUpLocation(pickUpLoc);
        reservation.setDropOffLocation(dropOffLoc);
        reservation.setPickUpDateAndTime(LocalDateTime.now().minusDays(2));
        reservation.setDropOffDateAndTime(LocalDateTime.now().minusDays(1)); // Was supposed to return yesterday
        reservation.setStatus(Reservation.Status.ACTIVE);
        reservation = reservationRepository.save(reservation);

        // Act
        Boolean success = reservationService.returnCar(reservation.getReservationNumber());

        // Assert
        Assertions.assertTrue(success);
        Reservation updatedRes = reservationRepository.findById(reservation.getReservationNumber()).orElse(null);
        Assertions.assertNotNull(updatedRes);
        Assertions.assertEquals(Reservation.Status.COMPLETED, updatedRes.getStatus());

        // Verify Car Location updated to dropOffLoc
        Car updatedCar = carRepository.findById(savedCar.getBarcode()).orElse(null);
        Assertions.assertNotNull(updatedCar);
        Assertions.assertEquals(dropOffLoc.getCode(), updatedCar.getLocation().getCode());
    }

    @Test
    void testCancelReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setCar(savedCar);
        reservation.setCustomer(savedCustomer);
        reservation.setStatus(Reservation.Status.ACTIVE);
        reservation = reservationRepository.save(reservation);

        // Act
        Boolean result = reservationService.cancelReservation(reservation.getReservationNumber());

        // Assert
        Assertions.assertTrue(result);
        Reservation canceled = reservationRepository.findById(reservation.getReservationNumber()).orElse(null);
        Assertions.assertNotNull(canceled);
        Assertions.assertEquals(Reservation.Status.CANCELLED, canceled.getStatus());
    }

    @Test
    void testGetAllCurrentlyReservedCars() {
        // Arrange: Reservation active TODAY
        Reservation reservation = new Reservation();
        reservation.setCar(savedCar);
        reservation.setCustomer(savedCustomer);
        // Spans across now
        reservation.setPickUpDateAndTime(LocalDateTime.now().minusDays(1));
        reservation.setDropOffDateAndTime(LocalDateTime.now().plusDays(1));
        reservation.setStatus(Reservation.Status.ACTIVE);
        reservationRepository.save(reservation);

        // Act
        List<RentedCarDto> rentedCars = reservationService.getAllCurrentlyReservedCars();

        // Assert
        Assertions.assertFalse(rentedCars.isEmpty());
        Assertions.assertEquals(savedCar.getBrand(), rentedCars.getFirst().getBrand());
    }

    @Test
    void testAddExtraServiceToReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setCar(savedCar);
        reservation.setCustomer(savedCustomer);
        reservation.setExtras(new ArrayList<>());
        reservation = reservationRepository.save(reservation);

        // Act
        Boolean result = reservationService.addExtraServiceToReservation(reservation.getReservationNumber(), extra1.getId());

        // Assert
        Assertions.assertTrue(result);
        Reservation updated = reservationRepository.findById(reservation.getReservationNumber()).orElse(null);
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(1, updated.getExtras().size());
        Assertions.assertEquals("GPS", updated.getExtras().getFirst().getName());
    }
}