package com.kerem.controller.impl;

import com.kerem.controller.ILocationController;
import com.kerem.dto.locationDto.LocationDto;
import com.kerem.dto.locationDto.LocationDtoIU;
import com.kerem.service.ILocationService;
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
@RequestMapping(path = "/rentacar/api/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Location Management", description = "APIs for managing rental locations")
public class LocationControllerImpl implements ILocationController {

        private final ILocationService locationService;

        @GetMapping
        @Override
        @Operation(summary = "Get all locations", description = "Retrieves a list of all rental locations")
        @ApiResponse(responseCode = "200", description = "Locations retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Locations not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))

        public ResponseEntity<List<LocationDto>> getAllLocations() {
                return ResponseEntity.ok(locationService.getAllLocations());
        }

        @GetMapping(path = "/{code}")
        @Override
        @Operation(summary = "Get location by code", description = "Retrieves a specific location by its code")
        @ApiResponse(responseCode = "200", description = "Location retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Location not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))

        public ResponseEntity<LocationDto> getLocationByCode(
                        @Parameter(description = "Code of the location", required = true) @PathVariable(name = "code") Long code) {
                return ResponseEntity.ok(locationService.getLocationById(code));
        }

        @PostMapping
        @Override
        @Operation(summary = "Create location", description = "Creates a new rental location")
        @ApiResponse(responseCode = "200", description = "Location created successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))

        public ResponseEntity<LocationDto> saveLocation(
                        @Parameter(description = "Location details", required = true) @RequestBody @Valid LocationDtoIU locationDto) {
                return ResponseEntity.ok(locationService.saveLocation(locationDto));
        }

        @PutMapping(path = "/{code}")
        @Override
        @Operation(summary = "Update location", description = "Updates an existing location")
        @ApiResponse(responseCode = "200", description = "Location updated successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "404", description = "Location not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<LocationDto> updateLocation(
                        @Parameter(description = "Code of the location to update", required = true) @PathVariable(name = "code") Long code,
                        @Parameter(description = "Updated location details", required = true) @RequestBody @Valid LocationDtoIU locationDto) {
                return ResponseEntity.ok(locationService.updateLocation(code, locationDto));
        }
}
