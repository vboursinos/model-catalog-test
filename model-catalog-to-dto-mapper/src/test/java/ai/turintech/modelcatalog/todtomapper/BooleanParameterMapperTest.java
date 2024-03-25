package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.to.BooleanParameterTO;
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
public class BooleanParameterMapperTest {
  @Autowired private BooleanParameterMapper booleanParameterMapper;

  @Test
  public void testToTO() {
    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.randomUUID());
    booleanParameterDTO.setDefaultValue(true);

    BooleanParameterTO result = booleanParameterMapper.to(booleanParameterDTO);

    assertEquals(booleanParameterDTO.getId(), result.getId());
    assertEquals(booleanParameterDTO.getDefaultValue(), result.getDefaultValue());
  }

  @Test
  public void testToDTO() {
    BooleanParameterTO booleanParameterTO = new BooleanParameterTO();
    booleanParameterTO.setId(UUID.randomUUID());
    booleanParameterTO.setDefaultValue(true);

    BooleanParameterDTO result = booleanParameterMapper.from(booleanParameterTO);

    assertEquals(booleanParameterTO.getId(), result.getId());
    assertEquals(booleanParameterTO.getDefaultValue(), result.getDefaultValue());
  }

  @Test
  public void testPartialUpdate() {
    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.randomUUID());
    booleanParameterDTO.setDefaultValue(true);

    BooleanParameterTO booleanParameterTO = new BooleanParameterTO();
    booleanParameterTO.setId(booleanParameterDTO.getId());
    booleanParameterTO.setDefaultValue(false);

    booleanParameterMapper.partialUpdate(booleanParameterDTO, booleanParameterTO);

    assertEquals(booleanParameterDTO.getDefaultValue(), booleanParameterTO.getDefaultValue());
  }
}
