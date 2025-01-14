package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.to.ModelFamilyTypeTO;
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
  public void testToTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.randomUUID());
    modelFamilyTypeDTO.setName("Supervised Learning");

    ModelFamilyTypeTO modelFamilyTypeTO = modelFamilyTypeMapper.to(modelFamilyTypeDTO);

    assertEquals(modelFamilyTypeDTO.getId(), modelFamilyTypeTO.getId());
    assertEquals(modelFamilyTypeDTO.getName(), modelFamilyTypeTO.getName());
  }

  @Test
  public void testToDTO() {
    ModelFamilyTypeTO modelFamilyTypeTO = new ModelFamilyTypeTO();
    modelFamilyTypeTO.setId(UUID.randomUUID());
    modelFamilyTypeTO.setName("Unsupervised Learning");

    ModelFamilyTypeDTO modelFamilyTypeDTO = modelFamilyTypeMapper.from(modelFamilyTypeTO);

    assertEquals(modelFamilyTypeTO.getId(), modelFamilyTypeDTO.getId());
    assertEquals(modelFamilyTypeTO.getName(), modelFamilyTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.randomUUID());
    modelFamilyTypeDTO.setName("Supervised Learning");

    ModelFamilyTypeTO modelFamilyTypeTO = new ModelFamilyTypeTO();
    modelFamilyTypeTO.setId(modelFamilyTypeDTO.getId());
    modelFamilyTypeTO.setName("Deep Learning");

    modelFamilyTypeMapper.partialUpdate(modelFamilyTypeDTO, modelFamilyTypeTO);

    assertEquals(modelFamilyTypeDTO.getName(), modelFamilyTypeTO.getName());
  }
}
