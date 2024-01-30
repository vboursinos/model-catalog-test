package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
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
public class ModelGroupTypeMapperTest {

  @Autowired private ModelGroupTypeMapper modelGroupTypeMapper;

  @Test
  public void testToTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.randomUUID());
    modelGroupTypeDTO.setName("Ensemble Models");

    ModelGroupTypeTO modelGroupType = modelGroupTypeMapper.to(modelGroupTypeDTO);

    assertEquals(modelGroupTypeDTO.getId(), modelGroupType.getId());
    assertEquals(modelGroupTypeDTO.getName(), modelGroupType.getName());
  }

  @Test
  public void testToDTO() {
    ModelGroupTypeTO modelGroupTypeTO = new ModelGroupTypeTO();
    modelGroupTypeTO.setId(UUID.randomUUID());
    modelGroupTypeTO.setName("Individual Models");

    ModelGroupTypeDTO modelGroupTypeDTO = modelGroupTypeMapper.from(modelGroupTypeTO);

    assertEquals(modelGroupTypeTO.getId(), modelGroupTypeDTO.getId());
    assertEquals(modelGroupTypeTO.getName(), modelGroupTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.randomUUID());
    modelGroupTypeDTO.setName("Updated Ensemble Models");

    ModelGroupTypeTO modelGroupTypeTO = new ModelGroupTypeTO();
    modelGroupTypeTO.setId(modelGroupTypeDTO.getId());
    modelGroupTypeTO.setName("Previous Ensemble Models");

    modelGroupTypeMapper.partialUpdate(modelGroupTypeDTO, modelGroupTypeTO);

    assertEquals(modelGroupTypeDTO.getName(), modelGroupTypeTO.getName());
  }
}
