package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class ModelEnsembleTypeMapperTest {

  @Autowired private ModelEnsembleTypeMapper modelEnsembleTypeMapper;

  @Test
  public void testToEntity() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.randomUUID());
    modelEnsembleTypeDTO.setName("Random Forest");

    ModelEnsembleType modelEnsembleType = modelEnsembleTypeMapper.from(modelEnsembleTypeDTO);

    assertEquals(modelEnsembleTypeDTO.getId(), modelEnsembleType.getId());
    assertEquals(modelEnsembleTypeDTO.getName(), modelEnsembleType.getName());
  }

  @Test
  public void testToDTO() {
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setId(UUID.randomUUID());
    modelEnsembleType.setName("Gradient Boosting");

    ModelEnsembleTypeDTO modelEnsembleTypeDTO = modelEnsembleTypeMapper.to(modelEnsembleType);

    assertEquals(modelEnsembleType.getId(), modelEnsembleTypeDTO.getId());
    assertEquals(modelEnsembleType.getName(), modelEnsembleTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.randomUUID());
    modelEnsembleTypeDTO.setName("Ensemble A");

    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setId(modelEnsembleTypeDTO.getId());
    modelEnsembleType.setName("Ensemble B");

    modelEnsembleTypeMapper.partialUpdate(modelEnsembleType, modelEnsembleTypeDTO);

    assertEquals(modelEnsembleTypeDTO.getName(), modelEnsembleType.getName());
  }
}
