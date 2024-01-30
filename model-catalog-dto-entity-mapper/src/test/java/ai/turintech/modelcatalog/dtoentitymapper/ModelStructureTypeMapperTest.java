package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class ModelStructureTypeMapperTest {

  @Autowired private ModelStructureTypeMapper modelStructureTypeMapper;

  @Test
  public void testToEntity() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.randomUUID());
    modelStructureTypeDTO.setName("Tree-based");

    ModelStructureType modelStructureType = modelStructureTypeMapper.from(modelStructureTypeDTO);

    assertEquals(modelStructureTypeDTO.getId(), modelStructureType.getId());
    assertEquals(modelStructureTypeDTO.getName(), modelStructureType.getName());
  }

  @Test
  public void testToDTO() {
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setId(UUID.randomUUID());
    modelStructureType.setName("Neural Network");

    ModelStructureTypeDTO modelStructureTypeDTO = modelStructureTypeMapper.to(modelStructureType);

    assertEquals(modelStructureType.getId(), modelStructureTypeDTO.getId());
    assertEquals(modelStructureType.getName(), modelStructureTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelStructureTypeDTO updatedModelStructureTypeDTO = new ModelStructureTypeDTO();
    updatedModelStructureTypeDTO.setId(UUID.randomUUID());
    updatedModelStructureTypeDTO.setName("Updated Tree-based");

    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setId(updatedModelStructureTypeDTO.getId());
    modelStructureType.setName("Previous Tree-based");

    modelStructureTypeMapper.partialUpdate(modelStructureType, updatedModelStructureTypeDTO);

    assertEquals(updatedModelStructureTypeDTO.getName(), modelStructureType.getName());
  }
}
