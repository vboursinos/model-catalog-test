package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.to.MlTaskTypeTO;
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
  public void testToTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.randomUUID());
    mlTaskTypeDTO.setName("Classification");

    MlTaskTypeTO mlTaskTypeTO = mlTaskTypeMapper.to(mlTaskTypeDTO);

    assertEquals(mlTaskTypeDTO.getId(), mlTaskTypeTO.getId());
    assertEquals(mlTaskTypeDTO.getName(), mlTaskTypeTO.getName());
  }

  @Test
  public void testToDTO() {
    MlTaskTypeTO mlTaskTypeTO = new MlTaskTypeTO();
    mlTaskTypeTO.setId(UUID.randomUUID());
    mlTaskTypeTO.setName("Regression");

    MlTaskTypeDTO mlTaskTypeDTO = mlTaskTypeMapper.from(mlTaskTypeTO);

    assertEquals(mlTaskTypeTO.getId(), mlTaskTypeDTO.getId());
    assertEquals(mlTaskTypeTO.getName(), mlTaskTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.randomUUID());
    mlTaskTypeDTO.setName("Clustering");

    MlTaskTypeTO mlTaskTypeTO = new MlTaskTypeTO();
    mlTaskTypeTO.setId(mlTaskTypeDTO.getId());
    mlTaskTypeTO.setName("Association");

    mlTaskTypeMapper.partialUpdate(mlTaskTypeDTO, mlTaskTypeTO);
    assertEquals(mlTaskTypeDTO.getName(), mlTaskTypeTO.getName());
  }
}
