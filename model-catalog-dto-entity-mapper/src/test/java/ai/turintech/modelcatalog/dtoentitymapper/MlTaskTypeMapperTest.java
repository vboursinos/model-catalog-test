package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.entity.MlTaskType;
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
public class MlTaskTypeMapperTest {

  @Autowired private MlTaskTypeMapper mlTaskTypeMapper;

  @Test
  public void testToEntity() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.randomUUID());
    mlTaskTypeDTO.setName("Classification");

    MlTaskType mlTaskType = mlTaskTypeMapper.from(mlTaskTypeDTO);

    assertEquals(mlTaskTypeDTO.getId(), mlTaskType.getId());
    assertEquals(mlTaskTypeDTO.getName(), mlTaskType.getName());
  }

  @Test
  public void testToDTO() {
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setId(UUID.randomUUID());
    mlTaskType.setName("Regression");

    MlTaskTypeDTO mlTaskTypeDTO = mlTaskTypeMapper.to(mlTaskType);

    assertEquals(mlTaskType.getId(), mlTaskTypeDTO.getId());
    assertEquals(mlTaskType.getName(), mlTaskTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.randomUUID());
    mlTaskTypeDTO.setName("Clustering");

    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setId(mlTaskTypeDTO.getId());
    mlTaskType.setName("Association");

    mlTaskTypeMapper.partialUpdate(mlTaskType, mlTaskTypeDTO);
    assertEquals(mlTaskTypeDTO.getName(), mlTaskType.getName());
  }
}
