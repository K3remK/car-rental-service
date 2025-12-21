package com.kerem.controller.impl;

import com.kerem.controller.IReservationController;
import com.kerem.dto.carDto.RentedCarDto;
import com.kerem.dto.reservationDto.ReservationDto;
import com.kerem.dto.reservationDto.ReservationInsertDto;
import com.kerem.dto.reservationDto.SaveReservationReturnDto;
import com.kerem.service.IReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping(path = "/rentacar/api/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Reservation Management", description = "APIs for managing car reservations")
public class ReservationControllerImpl implements IReservationController {

        private final IReservationService reservationService;

        @PostMapping
        @Override
        @Operation(summary = "Create reservation", description = "Creates a new reservation")
        @ApiResponse(responseCode = "200", description = "Reservation created successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "406", description = "Reservation not acceptable", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<SaveReservationReturnDto> saveReservation(
                        @Parameter(description = "Reservation details", required = true) @RequestBody @Valid ReservationInsertDto reservationInsertDto) {
                return ResponseEntity.ok(reservationService.saveReservation(reservationInsertDto));
        }

        @GetMapping(path = "/rented-cars")
        @Override
        @Operation(summary = "Get currently reserved cars", description = "Retrieves a list of all currently reserved cars")
        @ApiResponse(responseCode = "200", description = "Reserved cars retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Reserved cars not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<List<RentedCarDto>> getAllCurrentlyReservedCars() {
                return ResponseEntity.ok(reservationService.getAllCurrentlyReservedCars());
        }

        @PutMapping(path = "/{reservationNumber}")
        @Override
        @Operation(summary = "Add extra service", description = "Adds an extra service to an existing reservation")
        @ApiResponse(responseCode = "200", description = "Extra service added successfully")
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<Boolean> addExtraServiceToReservation(
                        @Parameter(description = "Reservation number", required = true) @PathVariable(name = "reservationNumber") String reservationNumber,
                        @Parameter(description = "ID of the extra service to add", required = true) @RequestParam(name = "extraServiceId") Long extraServiceId) {
                return ResponseEntity
                                .ok(reservationService.addExtraServiceToReservation(reservationNumber, extraServiceId));
        }

        @PutMapping(path = "/{reservationNumber}/return")
        @Override
        @Operation(summary = "Return car", description = "Marks a rented car as returned")
        @ApiResponse(responseCode = "200", description = "Car returned successfully")
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<Boolean> returnCar(
                        @Parameter(description = "Reservation number", required = true) @PathVariable(name = "reservationNumber") String reservationNumber) {
                return ResponseEntity.ok(reservationService.returnCar(reservationNumber));
        }

        @PutMapping(path = "/{reservationNumber}/cancel")
        @Override
        @Operation(summary = "Cancel reservation", description = "Cancels an existing reservation")
        @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully")
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<Boolean> cancelReservation(
                        @Parameter(description = "Reservation number to cancel", required = true) @PathVariable(name = "reservationNumber") String reservationNumber) {
                return ResponseEntity.ok().body(reservationService.cancelReservation(reservationNumber));
        }

        @DeleteMapping("/{reservationNumber}")
        @Override
        @Operation(summary = "Delete reservation", description = "Deletes a reservation record")
        @ApiResponse(responseCode = "200", description = "Reservation deleted successfully")
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<Boolean> deleteReservation(
                        @Parameter(description = "Reservation number to delete", required = true) @PathVariable(name = "reservationNumber") String reservationNumber) {
                return ResponseEntity.ok().body(reservationService.deleteReservation(reservationNumber));
        }

        @GetMapping
        @Override
        @Operation(summary = "Get all reservations", description = "Retrieves a list of all reservations")
        @ApiResponse(responseCode = "200", description = "Reservations retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Reservations not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<List<ReservationDto>> getAllReservations() {
                return ResponseEntity.ok(reservationService.getAllReservations());
        }

        @GetMapping(path = "/{reservationNumber}")
        @Override
        @Operation(summary = "Get reservation by number", description = "Retrieves a specific reservation by its unique number")
        @ApiResponse(responseCode = "200", description = "Reservation retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<ReservationDto> getReservationByReservationNumber(
                        @Parameter(description = "Reservation number", required = true) @PathVariable(name = "reservationNumber") String reservationNumber) {
                return ResponseEntity.ok(reservationService.getReservationByReservationNumber(reservationNumber));
        }
}
