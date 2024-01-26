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
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterTypeDefinitionServiceTest extends BasicServiceTest {

  @Autowired private ParameterTypeDefinitionService parameterTypeDefinitionService;

  // UUID strings
  private static final String EXISTING_PARAMETER_TYPE_DEFINITION_ID =
      "323e4567-e89b-12d3-a456-426614174001";
  private static final String NON_EXISTING_PARAMETER_TYPE_DEFINITION_ID =
      "a12b34cd-5678-90ef-1234-567890abcdef";

  private static final String PARAMETER_DISTRIBUTION_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String PARAMETER_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private ParameterTypeDefinitionDTO getParameterTypeDefinitionDTO() {

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString(PARAMETER_DISTRIBUTION_TYPE_ID));
    parameterDistributionTypeDTO.setName("parameterdistributiontype1");

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString(PARAMETER_TYPE_ID));
    parameterTypeDTO.setName("parametertype1");

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setOrdering(10);

    return parameterTypeDefinitionDTO;
  }

  private ParameterTypeDefinitionDTO getUpdatedParameterTypeDefinitionDTO() {

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString(PARAMETER_DISTRIBUTION_TYPE_ID));
    parameterDistributionTypeDTO.setName("parameterdistributiontype1");

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString(PARAMETER_TYPE_ID));
    parameterTypeDTO.setName("parametertype1");

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString(EXISTING_PARAMETER_TYPE_DEFINITION_ID));
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
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_TYPE_DEFINITION_ID);
    Mono<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOMono =
        parameterTypeDefinitionService.findOne(existingId);

    StepVerifier.create(parameterTypeDefinitionDTOMono)
        .expectNextMatches(
            parameterTypeDefinitionDTO -> {
              System.out.println(
                  "Found ParameterTypeDefinition by ID: " + parameterTypeDefinitionDTO);
              Assert.assertTrue(1 == parameterTypeDefinitionDTO.getOrdering());
              return true;
            })
        .verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdParameterTypeDefinitionServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_TYPE_DEFINITION_ID);
    Mono<Boolean> exists = parameterTypeDefinitionService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdParameterTypeDefinitionServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_PARAMETER_TYPE_DEFINITION_ID);
    Mono<Boolean> exists = parameterTypeDefinitionService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
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
  void testUpdateParameterTypeDefinitionService() {
    Mono<ParameterTypeDefinitionDTO> updateParameterTypeDefinitionDTO =
        parameterTypeDefinitionService.save(getUpdatedParameterTypeDefinitionDTO());

    StepVerifier.create(updateParameterTypeDefinitionDTO)
        .expectNextMatches(
            parameterTypeDefinitionDTO -> {
              System.out.println("Updated ParameterTypeDefinition: " + parameterTypeDefinitionDTO);
              Assert.assertEquals(
                  getUpdatedParameterTypeDefinitionDTO().getOrdering(),
                  parameterTypeDefinitionDTO.getOrdering());
              return true;
            })
        .verifyComplete();
  }
}
