package com.kerem;

import com.kerem.dto.extraServiceDto.ExtraServiceDto;
import com.kerem.dto.extraServiceDto.ExtraServiceDtoIU;
import com.kerem.entities.ExtraService;
import com.kerem.repository.ExtraServiceRepository;
import com.kerem.service.IExtraServiceService;
import com.kerem.starter.CarRentalServiceApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = CarRentalServiceApplication.class)
@ActiveProfiles("test")
@Transactional
public class ExtraServiceServiceTest {

    @Autowired
    private IExtraServiceService extraServiceService;

    @Autowired
    private ExtraServiceRepository extraServiceRepository;

    @Test
    void testSave() {
        // Arrange
        ExtraServiceDtoIU dtoIU = new ExtraServiceDtoIU();
        dtoIU.setName("Baby Seat");
        dtoIU.setDescription("Comfortable seat for babies");
        dtoIU.setTotalPrice(50.0);
        dtoIU.setCategory(ExtraService.ExtraCategory.CHILD);

        // Act
        ExtraServiceDto saved = extraServiceService.save(dtoIU);

        // Assert
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("Baby Seat", saved.getName());
    }

    @Test
    void testFindAll() {
        // Arrange
        ExtraService ex1 = new ExtraService(null, "Wifi", "4G", 20.0, ExtraService.ExtraCategory.TECHNOLOGY);
        extraServiceRepository.save(ex1);

        // Act
        List<ExtraServiceDto> all = extraServiceService.getAllExtraServices();

        // Assert
        Assertions.assertFalse(all.isEmpty());
    }

    @Test
    void testFindById() {
        // Arrange
        ExtraService ex = new ExtraService(null, "Chain", "Snow Chain", 30.0, ExtraService.ExtraCategory.WINTER);
        ExtraService saved = extraServiceRepository.save(ex);

        // Act
        ExtraServiceDto found = extraServiceService.findById(saved.getId());

        // Assert
        Assertions.assertEquals("Chain", found.getName());
    }

    @Test
    void testUpdate() {
        // Arrange
        ExtraService ex = new ExtraService(null, "Old Service", "Desc", 10.0, ExtraService.ExtraCategory.SPECIAL);
        ExtraService saved = extraServiceRepository.save(ex);

        ExtraServiceDtoIU updateDto = new ExtraServiceDtoIU();
        updateDto.setName("New Service");
        updateDto.setDescription("New Desc");
        updateDto.setTotalPrice(15.0);
        updateDto.setCategory(ExtraService.ExtraCategory.SPECIAL);

        // Act
        ExtraServiceDto updated = extraServiceService.update(saved.getId(), updateDto);

        // Assert
        Assertions.assertEquals("New Service", updated.getName());
        Assertions.assertEquals(15.0, updated.getTotalPrice());
    }
}