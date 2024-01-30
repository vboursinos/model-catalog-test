package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.to.ModelEnsembleTypeTO;
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
public class ModelEnsembleTypeMapperTest {

  @Autowired private ModelEnsembleTypeMapper modelEnsembleTypeMapper;

  @Test
  public void testToTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.randomUUID());
    modelEnsembleTypeDTO.setName("Random Forest");

    ModelEnsembleTypeTO modelEnsembleType = modelEnsembleTypeMapper.to(modelEnsembleTypeDTO);

    assertEquals(modelEnsembleTypeDTO.getId(), modelEnsembleType.getId());
    assertEquals(modelEnsembleTypeDTO.getName(), modelEnsembleType.getName());
  }

  @Test
  public void testToDTO() {
    ModelEnsembleTypeTO modelEnsembleTypeTO = new ModelEnsembleTypeTO();
    modelEnsembleTypeTO.setId(UUID.randomUUID());
    modelEnsembleTypeTO.setName("Gradient Boosting");

    ModelEnsembleTypeDTO modelEnsembleTypeDTO = modelEnsembleTypeMapper.from(modelEnsembleTypeTO);

    assertEquals(modelEnsembleTypeTO.getId(), modelEnsembleTypeDTO.getId());
    assertEquals(modelEnsembleTypeTO.getName(), modelEnsembleTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.randomUUID());
    modelEnsembleTypeDTO.setName("Ensemble A");

    ModelEnsembleTypeTO modelEnsembleTypeTO = new ModelEnsembleTypeTO();
    modelEnsembleTypeTO.setId(modelEnsembleTypeDTO.getId());
    modelEnsembleTypeTO.setName("Ensemble B");

    modelEnsembleTypeMapper.partialUpdate(modelEnsembleTypeDTO, modelEnsembleTypeTO);

    assertEquals(modelEnsembleTypeDTO.getName(), modelEnsembleTypeTO.getName());
  }
}
