package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.to.ModelStructureTypeTO;
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
  public void testToTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.randomUUID());
    modelStructureTypeDTO.setName("Tree-based");

    ModelStructureTypeTO modelStructureTypeTO = modelStructureTypeMapper.to(modelStructureTypeDTO);

    assertEquals(modelStructureTypeDTO.getId(), modelStructureTypeTO.getId());
    assertEquals(modelStructureTypeDTO.getName(), modelStructureTypeTO.getName());
  }

  @Test
  public void testToDTO() {
    ModelStructureTypeTO modelStructureTypeTO = new ModelStructureTypeTO();
    modelStructureTypeTO.setId(UUID.randomUUID());
    modelStructureTypeTO.setName("Neural Network");

    ModelStructureTypeDTO modelStructureTypeDTO =
        modelStructureTypeMapper.from(modelStructureTypeTO);

    assertEquals(modelStructureTypeTO.getId(), modelStructureTypeDTO.getId());
    assertEquals(modelStructureTypeTO.getName(), modelStructureTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelStructureTypeDTO updatedModelStructureTypeDTO = new ModelStructureTypeDTO();
    updatedModelStructureTypeDTO.setId(UUID.randomUUID());
    updatedModelStructureTypeDTO.setName("Updated Tree-based");

    ModelStructureTypeTO modelStructureTypeTO = new ModelStructureTypeTO();
    modelStructureTypeTO.setId(updatedModelStructureTypeDTO.getId());
    modelStructureTypeTO.setName("Previous Tree-based");

    modelStructureTypeMapper.partialUpdate(updatedModelStructureTypeDTO, modelStructureTypeTO);

    assertEquals(updatedModelStructureTypeDTO.getName(), modelStructureTypeTO.getName());
  }
}
