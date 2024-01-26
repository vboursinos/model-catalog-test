package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterDistributionTypeServiceTest extends BasicServiceTest {

  private static final String EXISTING_PARAMETER_DISTRIBUTION_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private static final String NON_EXISTING_PARAMETER_DISTRIBUTION_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  @Autowired private ParameterDistributionTypeService parameterDistributionTypeService;

  private ParameterDistributionTypeDTO getParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("test_name");
    return parameterDistributionTypeDTO;
  }

  private ParameterDistributionTypeDTO getUpdatedParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    parameterDistributionTypeDTO.setName("test_updated_parametertype");
    return parameterDistributionTypeDTO;
  }

  @Test
  void testFindAllParameterDistributionTypeService() {
    Mono<List<ParameterDistributionTypeDTO>> parameterDistributionTypes =
        parameterDistributionTypeService.findAll();

    List<ParameterDistributionTypeDTO> parameterDistributionTypeDTOS =
        parameterDistributionTypes.block();

    Assert.assertNotNull(parameterDistributionTypeDTOS);
    Assert.assertEquals(4, parameterDistributionTypeDTOS.size());
    Assert.assertEquals(
        "parameterdistributiontype1", parameterDistributionTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdParameterDistributionTypeService() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_ID);

    StepVerifier.create(parameterDistributionTypeService.findOne(existingId))
        .expectNextMatches(
            parameterDistributionTypeDTO -> {
              System.out.println(
                  "Found ParameterDistributionType by ID: " + parameterDistributionTypeDTO);
              Assert.assertEquals(existingId, parameterDistributionTypeDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdParameterDistributionTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_ID);

    StepVerifier.create(parameterDistributionTypeService.existsById(existingId))
        .expectNext(true)
        .verifyComplete();
  }

  @Test
  void testExistsByIdParameterDistributionTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID();

    StepVerifier.create(parameterDistributionTypeService.existsById(nonExistingId))
        .expectNext(false)
        .verifyComplete();
  }

  @Test
  void testSaveParameterDistributionTypeService() {
    Mono<ParameterDistributionTypeDTO> savedParameterDistributionTypeDTO =
        parameterDistributionTypeService.save(getParameterDistributionTypeDTO());
    savedParameterDistributionTypeDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getParameterDistributionTypeDTO().getName(), parameterDistributionTypeDTO.getName());
          parameterDistributionTypeService.delete(parameterDistributionTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateParameterDistributionTypeService() {
    Mono<ParameterDistributionTypeDTO> updatedParameterDistributionTypeDTO =
        parameterDistributionTypeService.save(getUpdatedParameterDistributionTypeDTO());

    StepVerifier.create(updatedParameterDistributionTypeDTO)
        .expectNextMatches(
            parameterDistributionTypeDTO -> {
              System.out.println(
                  "Updated ParameterDistributionType: " + parameterDistributionTypeDTO);
              Assert.assertEquals(
                  getUpdatedParameterDistributionTypeDTO().getName(),
                  parameterDistributionTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
