package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
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
public class ParameterDistributionTypeMapperTest {

  @Autowired private ParameterDistributionTypeMapper parameterDistributionTypeMapper;

  @Test
  public void testToTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.randomUUID());
    parameterDistributionTypeDTO.setName("Normal Distribution");

    ParameterDistributionTypeTO parameterDistributionTypeTO =
        parameterDistributionTypeMapper.to(parameterDistributionTypeDTO);

    assertEquals(parameterDistributionTypeDTO.getId(), parameterDistributionTypeTO.getId());
    assertEquals(parameterDistributionTypeDTO.getName(), parameterDistributionTypeTO.getName());
  }

  @Test
  public void testToDTO() {
    ParameterDistributionTypeTO parameterDistributionTypeTO = new ParameterDistributionTypeTO();
    parameterDistributionTypeTO.setId(UUID.randomUUID());
    parameterDistributionTypeTO.setName("Uniform Distribution");

    ParameterDistributionTypeDTO parameterDistributionTypeDTO =
        parameterDistributionTypeMapper.from(parameterDistributionTypeTO);

    assertEquals(parameterDistributionTypeTO.getId(), parameterDistributionTypeDTO.getId());
    assertEquals(parameterDistributionTypeTO.getName(), parameterDistributionTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ParameterDistributionTypeDTO updatedParameterDistributionTypeDTO =
        new ParameterDistributionTypeDTO();
    updatedParameterDistributionTypeDTO.setId(UUID.randomUUID());
    updatedParameterDistributionTypeDTO.setName("Updated Gaussian Distribution");

    ParameterDistributionTypeTO parameterDistributionTypeTO = new ParameterDistributionTypeTO();
    parameterDistributionTypeTO.setId(updatedParameterDistributionTypeDTO.getId());
    parameterDistributionTypeTO.setName("Previous Gaussian Distribution");

    parameterDistributionTypeMapper.partialUpdate(
        updatedParameterDistributionTypeDTO, parameterDistributionTypeTO);

    assertEquals(
        updatedParameterDistributionTypeDTO.getName(), parameterDistributionTypeTO.getName());
  }
}
