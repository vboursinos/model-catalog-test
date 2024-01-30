package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
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
public class ModelFamilyTypeMapperTest {

  @Autowired private ModelFamilyTypeMapper modelFamilyTypeMapper;

  @Test
  public void testToEntity() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.randomUUID());
    modelFamilyTypeDTO.setName("Supervised Learning");

    ModelFamilyType modelFamilyType = modelFamilyTypeMapper.from(modelFamilyTypeDTO);

    assertEquals(modelFamilyTypeDTO.getId(), modelFamilyType.getId());
    assertEquals(modelFamilyTypeDTO.getName(), modelFamilyType.getName());
  }

  @Test
  public void testToDTO() {
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setId(UUID.randomUUID());
    modelFamilyType.setName("Unsupervised Learning");

    ModelFamilyTypeDTO modelFamilyTypeDTO = modelFamilyTypeMapper.to(modelFamilyType);

    assertEquals(modelFamilyType.getId(), modelFamilyTypeDTO.getId());
    assertEquals(modelFamilyType.getName(), modelFamilyTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.randomUUID());
    modelFamilyTypeDTO.setName("Reinforcement Learning");

    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setId(modelFamilyTypeDTO.getId());
    modelFamilyType.setName("Deep Learning");

    modelFamilyTypeMapper.partialUpdate(modelFamilyType, modelFamilyTypeDTO);

    assertEquals(modelFamilyTypeDTO.getName(), modelFamilyType.getName());
  }
}
