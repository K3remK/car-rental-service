package com.kerem.controller.impl;

import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import com.kerem.service.IExtraServiceService;
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

import com.kerem.controller.IExtraServiceController;
import com.kerem.dto.extraServiceDto.ExtraServiceDto;

@RestController
@RequestMapping(path = "/rentacar/api/extra-services", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Extra Service Management", description = "APIs for managing extra services")
public class ExtraServiceControllerImpl implements IExtraServiceController {

        private final IExtraServiceService extraServiceService;

        @GetMapping
        @Override
        @Operation(summary = "Get all extra services", description = "Retrieves a list of all available extra services")
        @ApiResponse(responseCode = "200", description = "Extra services retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Extra Services not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<List<ExtraServiceDto>> getAllExtraServices() {
                return ResponseEntity.ok(extraServiceService.getAllExtraServices());
        }

        @GetMapping(path = "/{id}")
        @Override
        @Operation(summary = "Get extra service by ID", description = "Retrieves a specific extra service by its unique identifier")
        @ApiResponse(responseCode = "200", description = "Extra service retrieved successfully")
        @ApiResponse(responseCode = "404", description = "Extra Service not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<ExtraServiceDto> getExtraServiceById(
                        @Parameter(description = "ID of the extra service to retrieve", required = true) @PathVariable(name = "id") Long id) {
                return ResponseEntity.ok(extraServiceService.findById(id));
        }

        @PostMapping
        @Override
        @Operation(summary = "Create extra service", description = "Creates a new extra service")
        @ApiResponse(responseCode = "200", description = "Extra service created successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        public ResponseEntity<ExtraServiceDto> saveExtraService(
                        @Parameter(description = "Extra service details", required = true) @RequestBody @Valid ExtraServiceDtoIU extraServiceDto) {
                return ResponseEntity.ok(extraServiceService.save(extraServiceDto));
        }

        @PutMapping(path = "/{id}")
        @Override
        @Operation(summary = "Update extra service", description = "Updates an existing extra service")
        @ApiResponse(responseCode = "200", description = "Extra service updated successfully")
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValidationError")))
        @ApiResponse(responseCode = "404", description = "Extra Service not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/StandardError")))
        public ResponseEntity<ExtraServiceDto> updateExtraService(
                        @Parameter(description = "ID of the extra service to update", required = true) @PathVariable(name = "id") Long id,
                        @Parameter(description = "Updated extra service details", required = true) @RequestBody @Valid ExtraServiceDtoIU extraServiceDto) {
                return ResponseEntity.ok(extraServiceService.update(id, extraServiceDto));
        }
}
