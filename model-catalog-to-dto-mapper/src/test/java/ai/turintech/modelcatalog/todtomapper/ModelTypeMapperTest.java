package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.to.ModelTypeTO;
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
public class ModelTypeMapperTest {

  @Autowired private ModelTypeMapper modelTypeMapper;

  @Test
  public void testToTO() {
    ModelTypeDTO modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(UUID.randomUUID());
    modelTypeDTO.setName("Learning");

    ModelTypeTO modelTypeTO = modelTypeMapper.to(modelTypeDTO);

    assertEquals(modelTypeDTO.getId(), modelTypeTO.getId());
    assertEquals(modelTypeDTO.getName(), modelTypeTO.getName());
  }

  @Test
  public void testToDTO() {
    ModelTypeTO modelTypeTO = new ModelTypeTO();
    modelTypeTO.setId(UUID.randomUUID());
    modelTypeTO.setName("Learning");

    ModelTypeDTO modelTypeDTO = modelTypeMapper.from(modelTypeTO);

    assertEquals(modelTypeTO.getId(), modelTypeDTO.getId());
    assertEquals(modelTypeTO.getName(), modelTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelTypeDTO updatedModelTypeDTO = new ModelTypeDTO();
    updatedModelTypeDTO.setId(UUID.randomUUID());
    updatedModelTypeDTO.setName("Updated Learning");

    ModelTypeTO modelTypeTO = new ModelTypeTO();
    modelTypeTO.setId(updatedModelTypeDTO.getId());
    modelTypeTO.setName("Learning");

    modelTypeMapper.partialUpdate(updatedModelTypeDTO, modelTypeTO);

    assertEquals(updatedModelTypeDTO.getName(), modelTypeTO.getName());
  }
}
