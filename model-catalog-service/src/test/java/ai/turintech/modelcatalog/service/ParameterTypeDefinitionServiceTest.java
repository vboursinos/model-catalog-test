package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterTypeDefinitionServiceTest extends BasicServiceTest {
  @Autowired private ParameterTypeDefinitionService parameterTypeDefinitionService;

  private ParameterTypeDefinitionDTO getParameterTypeDefinitionDTO() {

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterDistributionTypeDTO.setName("parameterdistributiontype1");

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterTypeDTO.setName("parametertype1");

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setOrdering(10);

    return parameterTypeDefinitionDTO;
  }

  private ParameterTypeDefinitionDTO getUpdatedParameterTypeDefinitionDTO() {

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterDistributionTypeDTO.setName("parameterdistributiontype1");

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterTypeDTO.setName("parametertype1");

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setOrdering(12);

    return parameterTypeDefinitionDTO;
  }

  @Test
  @Transactional
  void testFindAllParameterTypeDefinitionService() {
    Mono<List<ParameterTypeDefinitionDTO>> parameterTypeDefinitionDTOsMono =
        parameterTypeDefinitionService.findAll();
    List<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOs =
        parameterTypeDefinitionDTOsMono.block();

    Assert.assertNotNull(parameterTypeDefinitionDTOs);
    Assert.assertEquals(3, parameterTypeDefinitionDTOs.size());
    Assert.assertTrue(1 == parameterTypeDefinitionDTOs.get(0).getOrdering());
  }

  @Test
  @Transactional
  void testFindByIdParameterTypeDefinitionService() {
    Mono<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOMono =
        parameterTypeDefinitionService.findOne(
            UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));

    parameterTypeDefinitionDTOMono.subscribe(
        parameterTypeDefinitionDTO -> {
          Assert.assertTrue(1 == parameterTypeDefinitionDTO.getOrdering());
        });
  }

  @Test
  @Transactional
  void testSaveParameterTypeDefinitionService() {
    Mono<ParameterTypeDefinitionDTO> savedParameterTypeDefinitionDTO =
        parameterTypeDefinitionService.save(getParameterTypeDefinitionDTO());
    savedParameterTypeDefinitionDTO.subscribe(
        parameterTypeDefinitionDTO -> {
          Assert.assertEquals(
              getParameterTypeDefinitionDTO().getOrdering(),
              parameterTypeDefinitionDTO.getOrdering());
          parameterTypeDefinitionService.delete(parameterTypeDefinitionDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testUpdateParameterTypeDefinitionServce() {
    Mono<ParameterTypeDefinitionDTO> updateParameterTypeDefinitionDTO =
        parameterTypeDefinitionService.save(getUpdatedParameterTypeDefinitionDTO());
    updateParameterTypeDefinitionDTO.subscribe(
        parameterTypeDefinitionDTO -> {
          Assert.assertEquals(
              getUpdatedParameterTypeDefinitionDTO().getOrdering(),
              parameterTypeDefinitionDTO.getOrdering());
        });
  }
}
