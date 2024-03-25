package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.entity.ModelType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class ModelTypeMapperTest {

  @Autowired private ModelTypeMapper modelTypeMapper;

  @Test
  public void testToEntity() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(UUID.randomUUID());
    modelTypeDTO.setName("Learning");

    ModelType modelType = modelTypeMapper.from(modelTypeDTO);

    assertEquals(modelTypeDTO.getId(), modelType.getId());
    assertEquals(modelTypeDTO.getName(), modelType.getName());
  }

  @Test
  public void testToDTO() {
    ModelType modelType = new ModelType();
    modelType.setId(UUID.randomUUID());
    modelType.setName("Learning");

    ModelTypeDTO modelTypeDTO = modelTypeMapper.to(modelType);

    assertEquals(modelType.getId(), modelTypeDTO.getId());
    assertEquals(modelType.getName(), modelTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelTypeDTO updatedModelTypeDTO = new ModelTypeDTO();
    updatedModelTypeDTO.setId(UUID.randomUUID());
    updatedModelTypeDTO.setName("Updated Learning");

    ModelType modelType = new ModelType();
    modelType.setId(updatedModelTypeDTO.getId());
    modelType.setName("Learning");

    modelTypeMapper.partialUpdate(modelType, updatedModelTypeDTO);

    assertEquals(updatedModelTypeDTO.getName(), modelType.getName());
  }
}
