package com.kerem;

import com.kerem.dto.carDto.CarDto;
import com.kerem.dto.reservationDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationDtoIU;
import com.kerem.entities.*;
import com.kerem.mapper.CarMapper;
import com.kerem.mapper.ExtraServiceMapper;
import com.kerem.mapper.ReservationMapper;
import com.kerem.repository.ReservationRepository;
import com.kerem.service.impl.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    private CarServiceImpl carService;

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private ExtraServiceServiceImpl extraServiceService;

    @Mock
    private LocationServiceImpl locationService;

    @Mock
    private CarMapper carMapper;

    @Mock
    private ExtraServiceMapper extraServiceMapper;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private ReservationDto createReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCustomerSsn(reservation.getCustomer().getSsn());
        reservationDto.setCustomerName(reservation.getCustomer().getFirstName() + " " + reservation.getCustomer().getLastName());
        reservationDto.setReservationNumber(reservation.getReservationNumber());
        reservationDto.setDropOffLocation(reservation.getDropOffLocation());
        reservationDto.setPickUpDateAndTime(reservation.getPickUpDateAndTime());
        reservationDto.setPickUpLocation(reservation.getPickUpLocation());
        reservationDto.setDropOffDateAndTime(reservation.getDropOffDateAndTime());
        return reservationDto;
    }

    private Reservation createReservation(ReservationDtoIU reservationDtoIU) {
        Reservation reservation = new Reservation();
        reservation.setDropOffDateAndTime(reservationDtoIU.getDropOffDateAndTime());
        reservation.setPickUpDateAndTime(reservationDtoIU.getPickUpDateAndTime());
        return reservation;
    }

    // --- SAVE RESERVATION TESTS ---

    @Test
    @DisplayName(value = "Save Reservation Test - Success")
    void testSaveReservation_Success() {
        // Arrange
        ReservationDtoIU reservationDtoIU = new ReservationDtoIU();
        reservationDtoIU.setCustomerSsn("1");
        reservationDtoIU.setDropOffLocationCode(1L);
        reservationDtoIU.setPickUpLocationCode(2L);
        reservationDtoIU.setCarBarcodeNumber(UUID.randomUUID());
        reservationDtoIU.setExtraServiceIds(List.of(1L,2L));
        reservationDtoIU.setPickUpDateAndTime(LocalDateTime.now());
        reservationDtoIU.setDropOffDateAndTime(LocalDateTime.now().plusDays(5));
        reservationDtoIU.setCarBarcodeNumber(UUID.randomUUID());

        Car reservationCar = new Car();
        reservationCar.setBarcode(reservationDtoIU.getCarBarcodeNumber());
        reservationCar.setDailyPrice(100D);

        Customer reservationCustomer = new Customer();
        reservationCustomer.setFirstName("John");
        reservationCustomer.setLastName("Doe");

        Location dropOffLocation = new Location();
        Location pickUpLocation = new Location();

        ExtraService extraService1 = new ExtraService();
        extraService1.setTotalPrice(100D);

        ExtraService extraService2 = new ExtraService();
        extraService2.setTotalPrice(100D);

        Reservation expectedReservation = createReservation(reservationDtoIU);
        expectedReservation.setCustomer(reservationCustomer);
        expectedReservation.setCar(reservationCar);
        expectedReservation.setDropOffLocation(dropOffLocation);
        expectedReservation.setPickUpLocation(pickUpLocation);
        ReservationDto expectedReservationDto = createReservationDto(expectedReservation);

        when(carService.findCarById(reservationDtoIU.getCarBarcodeNumber())).thenReturn(reservationCar);
        when(customerService.getCustomerBySsn(reservationDtoIU.getCustomerSsn())).thenReturn(reservationCustomer);
        when(locationService.getLocationById(reservationDtoIU.getPickUpLocationCode())).thenReturn(pickUpLocation);
        when(locationService.getLocationById(reservationDtoIU.getDropOffLocationCode())).thenReturn(dropOffLocation);
        when(extraServiceService.findById(1L)).thenReturn(extraService1);
        when(extraServiceService.findById(2L)).thenReturn(extraService2);
        when(reservationMapper.map(reservationDtoIU)).thenReturn(expectedReservation);
        when(reservationRepository.save(expectedReservation)).thenReturn(expectedReservation);
        when(reservationMapper.map(expectedReservation)).thenReturn(expectedReservationDto);
        when(carService.isAvailable(reservationCar.getBarcode(), reservationDtoIU.getPickUpDateAndTime(), reservationDtoIU.getDropOffDateAndTime())).thenReturn(true);

        // Act
        ReservationDto result = reservationService.saveReservation(reservationDtoIU);

        // Assert & Verify
        assertNotNull(result);
        assertEquals(100D + 100D + 100D * 5, result.getTotalAmount());
        assertEquals(reservationCustomer.getFirstName() + " " + reservationCustomer.getLastName(), result.getCustomerName());
        assertEquals(reservationCustomer.getSsn(), result.getCustomerSsn());
        verify(carService).findCarById(reservationDtoIU.getCarBarcodeNumber());
        verify(carService).isAvailable(reservationDtoIU.getCarBarcodeNumber(), reservationDtoIU.getPickUpDateAndTime(), reservationDtoIU.getDropOffDateAndTime());
        verify(customerService).getCustomerBySsn(reservationDtoIU.getCustomerSsn());
        verify(locationService).getLocationById(reservationDtoIU.getPickUpLocationCode());
        verify(locationService).getLocationById(reservationDtoIU.getDropOffLocationCode());
        verify(extraServiceService).findById(1L);
        verify(extraServiceService).findById(2L);
        verify(reservationMapper).map(reservationDtoIU);
        verify(reservationMapper).map(expectedReservation);
        verify(reservationRepository).save(expectedReservation);
    }

    @Test
    @DisplayName(value = "Save Reservation Test - Fail: CarOutOfServiceShouldException")
    public void testSaveReservation_Fail_CarOutOfServiceShouldException() {
        // Arrange
        ReservationDtoIU reservationDtoIU = new ReservationDtoIU();
        reservationDtoIU.setCarBarcodeNumber(UUID.randomUUID());

        Car reservationCar = new Car();
        reservationCar.setStatus(Car.CarStatus.OUT_OF_SERVICE);

        when(carService.findCarById(reservationDtoIU.getCarBarcodeNumber())).thenReturn(reservationCar);

        // Act, Assert & Verify
        assertThrows(NotAcceptableStatusException.class, () -> {
            reservationService.saveReservation(reservationDtoIU);
        });
        verify(carService).findCarById(reservationDtoIU.getCarBarcodeNumber());
        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName(value = "Save Reservation Test - Fail: CarIsNotAvailable")
    public void testSaveReservation_Fail_CarIsNotAvailable() {
        ReservationDtoIU reservationDtoIU = new ReservationDtoIU();
        reservationDtoIU.setCarBarcodeNumber(UUID.randomUUID());

        Car reservationCar = new Car();
        reservationCar.setStatus(Car.CarStatus.IN_SERVICE);
        reservationCar.setBarcode(reservationDtoIU.getCarBarcodeNumber());

        when(carService.findCarById(reservationDtoIU.getCarBarcodeNumber())).thenReturn(reservationCar);
        when(carService.isAvailable(any(), any(), any())).thenReturn(false);

        assertThrows(NotAcceptableStatusException.class, () -> {
            reservationService.saveReservation(reservationDtoIU);
        });
        verify(carService).findCarById(reservationDtoIU.getCarBarcodeNumber());
        verify(reservationRepository, never()).save(any());
        verify(carService).isAvailable(reservationDtoIU.getCarBarcodeNumber(), reservationDtoIU.getPickUpDateAndTime(), reservationDtoIU.getDropOffDateAndTime());
    }


    // --- GET CURRENTLY RESERVED CARS TESTS ---

    @Test
    @DisplayName(value = "Get All Currently Reserved Cars - Success")
    public void testGetAllCurrentlyReservedCars_Success() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setPickUpDateAndTime(LocalDateTime.now().minusDays(1));
        reservation.setDropOffDateAndTime(LocalDateTime.now().plusDays(1));

        Car reservationCar = new Car();
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        reservation.setCar(reservationCar);
        reservation.setCustomer(customer);

        when(reservationRepository.getActiveReservationsOn(any(LocalDateTime.class))).thenReturn(List.of(reservation));

        // Mock mappers to ensure they don't crash the loop
        doNothing().when(carMapper).map(any(Car.class), any(RentedCarDto.class));
        doNothing().when(reservationMapper).mapReservationToRentedCarDto(any(Reservation.class), any(RentedCarDto.class));

        // Act
        List<RentedCarDto> result =  reservationService.getAllCurrentlyReservedCars();

        // Assert & Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.getFirst().getReservationDayCount());
        assertEquals("John Doe", result.getFirst().getCustomerName());
    }

    @Test
    @DisplayName(value = "Get All Currently Reserved Cars - Empty Throw Exception")
    public void testGetAllCurrentlyReservedCars_EmptyThrowException() {
        when(reservationRepository.getActiveReservationsOn(any(LocalDateTime.class))).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> {
            reservationService.getAllCurrentlyReservedCars();
        });
        verify(reservationRepository).getActiveReservationsOn(any(LocalDateTime.class));
    }

    // --- ADD EXTRA SERVICE TESTS ---

    @Test
    @DisplayName(value = "Add Extra Service - Success")
    public void addExtraService_Success() {
        // Arrange
        String reservationNumber = "22222222";
        Long extraServiceId = 1L;

        Reservation reservation = new Reservation();
        reservation.setExtras(new ArrayList<>());

        ExtraService extraService = new ExtraService();
        extraService.setId(extraServiceId);

        when(reservationRepository.findById(reservationNumber)).thenReturn(Optional.of(reservation));
        when(extraServiceService.findById(extraServiceId)).thenReturn(extraService);

        // Act

        Boolean result = reservationService.addExtraServiceToReservation(reservationNumber, extraServiceId);

        // Assert & Verify
        assertTrue(result);
        assertEquals(1, reservation.getExtras().size());
        assertEquals(extraService,  reservation.getExtras().getFirst());
        verify(extraServiceService).findById(extraServiceId);
        verify(reservationRepository).findById(reservationNumber);
        verify(reservationRepository).save(reservation);
    }

    @Test
    @DisplayName(value = "Add Extra Service - Already Exists")
    public void addExtraService_AlreadyExists() {
        // Arrange
        String reservationNumber = "22222222";
        Long extraServiceId = 1L;

        ExtraService extraService = new ExtraService();
        extraService.setId(extraServiceId);

        Reservation reservation = new Reservation();
        reservation.setReservationNumber(reservationNumber);
        reservation.setExtras(List.of(extraService));

        when(reservationRepository.findById(reservationNumber)).thenReturn(Optional.of(reservation));
        when(extraServiceService.findById(extraServiceId)).thenReturn(extraService);

        // Act

        Boolean result = reservationService.addExtraServiceToReservation(reservationNumber, extraServiceId);

        // Assert & Verify
        assertFalse(result);
        verify(reservationRepository, never()).save(any());
    }


    // --- RETURN CAR TESTS

    @Test
    @DisplayName(value = "Return Car - Success")
    void  returnCar_Success() {
        // Arrange
        String reservationNumber = "22222222";


        Reservation reservation = new Reservation();
        Location dropOffLocation = new Location();
        dropOffLocation.setCode(1L);
        dropOffLocation.setLocationName("Drop Off");
        Car car = new Car();
        reservation.setCar(car);
        reservation.setDropOffLocation(dropOffLocation);

        when(reservationRepository.findById(reservationNumber)).thenReturn(Optional.of(reservation));

        // Act

        Boolean result = reservationService.returnCar(reservationNumber);

        // Assert & Verify
        assertTrue(result);
        assertEquals(dropOffLocation, reservation.getCar().getLocation());
        assertEquals(Reservation.Status.COMPLETED, reservation.getStatus());
        assertNotNull(reservation.getDropOffDateAndTime());
        verify(reservationRepository).save(any());
        verify(carService).updateCar(any(), any());
    }

    // --- CANCEL RESERVATION TESTS ---

    @Test
    @DisplayName("Cancel Reservation - Success")
    void testCancelReservation_Success() {
        // Arrange
        String resNum = "RES-123";
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.Status.ACTIVE);

        when(reservationRepository.findById(resNum)).thenReturn(Optional.of(reservation));

        // Act
        Boolean result = reservationService.cancelReservation(resNum);

        // Assert
        assertTrue(result);
        assertEquals(Reservation.Status.CANCELLED, reservation.getStatus());
        verify(reservationRepository).save(reservation);
    }

    @Test
    @DisplayName("Cancel Reservation - Not Found")
    void testCancelReservation_NotFound() {
        when(reservationRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> reservationService.cancelReservation("inv"));
    }

    // --- DELETE RESERVATION TESTS ---

    @Test
    @DisplayName("Delete Reservation - Success")
    void testDeleteReservation_Success() {
        // Arrange
        String resNum = "RES-123";
        Reservation reservation = new Reservation();

        when(reservationRepository.findById(resNum)).thenReturn(Optional.of(reservation));

        // Act
        Boolean result = reservationService.deleteReservation(resNum);

        // Assert
        assertTrue(result);
        verify(reservationRepository).delete(reservation);
    }
}
