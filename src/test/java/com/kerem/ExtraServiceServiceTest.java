package com.kerem;

import com.kerem.entities.ExtraService;
import com.kerem.repository.ExtraServiceRepository;
import com.kerem.service.impl.ExtraServiceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExtraServiceServiceTest {

    @Mock
    private ExtraServiceRepository extraServiceRepository;

    @InjectMocks
    private ExtraServiceServiceImpl extraServiceService;

    @Test
    @DisplayName("Find ExtraService by ID - Success")
    void testFindExtraServiceById_Success() {
        ExtraService mockExtraService = new ExtraService();
        mockExtraService.setId(1L);

        when(extraServiceRepository.findById(mockExtraService.getId())).thenReturn(Optional.of(mockExtraService));

        ExtraService result =  extraServiceService.findById(mockExtraService.getId());

        assertNotNull(result);
        assertEquals(mockExtraService.getId(), result.getId());
        verify(extraServiceRepository).findById(mockExtraService.getId());
    }

    @Test
    @DisplayName("Get customer by SSN - Throws Not Found Exception")
    void testGetCustomerBySsn_NotFound() {
        Long nonExistentID = 1L;

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                extraServiceService.findById(nonExistentID)
        );

        assertEquals("ExtraService not found! ID:" + nonExistentID, exception.getMessage());
        verify(extraServiceRepository).findById(nonExistentID);
    }
}
