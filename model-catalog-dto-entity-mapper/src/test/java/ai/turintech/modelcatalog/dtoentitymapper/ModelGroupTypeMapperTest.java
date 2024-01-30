package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.entity.ModelGroupType;
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
  public void testToEntity() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.randomUUID());
    modelGroupTypeDTO.setName("Ensemble Models");

    ModelGroupType modelGroupType = modelGroupTypeMapper.from(modelGroupTypeDTO);

    assertEquals(modelGroupTypeDTO.getId(), modelGroupType.getId());
    assertEquals(modelGroupTypeDTO.getName(), modelGroupType.getName());
  }

  @Test
  public void testToDTO() {
    ModelGroupType modelGroupType = new ModelGroupType();
    modelGroupType.setId(UUID.randomUUID());
    modelGroupType.setName("Individual Models");

    ModelGroupTypeDTO modelGroupTypeDTO = modelGroupTypeMapper.to(modelGroupType);

    assertEquals(modelGroupType.getId(), modelGroupTypeDTO.getId());
    assertEquals(modelGroupType.getName(), modelGroupTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.randomUUID());
    modelGroupTypeDTO.setName("Updated Ensemble Models");

    ModelGroupType modelGroupType = new ModelGroupType();
    modelGroupType.setId(modelGroupTypeDTO.getId());
    modelGroupType.setName("Previous Ensemble Models");

    modelGroupTypeMapper.partialUpdate(modelGroupType, modelGroupTypeDTO);

    assertEquals(modelGroupTypeDTO.getName(), modelGroupType.getName());
  }
}
