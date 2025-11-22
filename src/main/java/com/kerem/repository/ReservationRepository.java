package com.kerem.repository;

import com.kerem.dto.reservationDto.RentedCarGetRequestDto;
import com.kerem.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT r FROM Reservation r WHERE r.pickUpDateAndTime <= :date AND r.dropOffDateAndTime >= :date AND r.status = 'ACTIVE'")
    List<Reservation> getActiveReservationsOn(LocalDateTime date);
}
