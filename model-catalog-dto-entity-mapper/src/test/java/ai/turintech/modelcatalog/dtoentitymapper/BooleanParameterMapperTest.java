package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
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
  public void testToEntity() {    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.randomUUID());
    booleanParameterDTO.setDefaultValue(true);

    BooleanParameter result = booleanParameterMapper.from(booleanParameterDTO);

    assertEquals(booleanParameterDTO.getId(), result.getId());
    assertEquals(booleanParameterDTO.getDefaultValue(), result.getDefaultValue());
  }

  @Test
  public void testToDTO() {
    BooleanParameter booleanParameter = new BooleanParameter();
    booleanParameter.setId(UUID.randomUUID());
    booleanParameter.setDefaultValue(true);

    BooleanParameterDTO result = booleanParameterMapper.to(booleanParameter);

    assertEquals(booleanParameter.getId(), result.getId());
    assertEquals(booleanParameter.getDefaultValue(), result.getDefaultValue());
  }

  @Test
  public void testPartialUpdate() {
    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.randomUUID());
    booleanParameterDTO.setDefaultValue(true);

    BooleanParameter booleanParameter = new BooleanParameter();
    booleanParameter.setId(booleanParameterDTO.getId());
    booleanParameter.setDefaultValue(false);

    booleanParameterMapper.partialUpdate(booleanParameter, booleanParameterDTO);

    assertEquals(booleanParameterDTO.getDefaultValue(), booleanParameter.getDefaultValue());
  }
}
