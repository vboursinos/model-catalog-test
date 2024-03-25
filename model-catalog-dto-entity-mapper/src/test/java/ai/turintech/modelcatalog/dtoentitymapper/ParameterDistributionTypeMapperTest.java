package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class ParameterDistributionTypeMapperTest {

  @Autowired private ParameterDistributionTypeMapper parameterDistributionTypeMapper;

  @Test
  public void testToEntity() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.randomUUID());
    parameterDistributionTypeDTO.setName("Normal Distribution");

    ParameterDistributionType parameterDistributionType =
        parameterDistributionTypeMapper.from(parameterDistributionTypeDTO);

    assertEquals(parameterDistributionTypeDTO.getId(), parameterDistributionType.getId());
    assertEquals(parameterDistributionTypeDTO.getName(), parameterDistributionType.getName());
  }

  @Test
  public void testToDTO() {
    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(UUID.randomUUID());
    parameterDistributionType.setName("Uniform Distribution");

    ParameterDistributionTypeDTO parameterDistributionTypeDTO =
        parameterDistributionTypeMapper.to(parameterDistributionType);

    assertEquals(parameterDistributionType.getId(), parameterDistributionTypeDTO.getId());
    assertEquals(parameterDistributionType.getName(), parameterDistributionTypeDTO.getName());
  }

  @Test
  public void testPartialUpdate() {
    ParameterDistributionTypeDTO updatedParameterDistributionTypeDTO =
        new ParameterDistributionTypeDTO();
    updatedParameterDistributionTypeDTO.setId(UUID.randomUUID());
    updatedParameterDistributionTypeDTO.setName("Updated Gaussian Distribution");

    ParameterDistributionType parameterDistributionType = new ParameterDistributionType();
    parameterDistributionType.setId(updatedParameterDistributionTypeDTO.getId());
    parameterDistributionType.setName("Previous Gaussian Distribution");

    parameterDistributionTypeMapper.partialUpdate(
        parameterDistributionType, updatedParameterDistributionTypeDTO);

    assertEquals(
        updatedParameterDistributionTypeDTO.getName(), parameterDistributionType.getName());
  }
}
