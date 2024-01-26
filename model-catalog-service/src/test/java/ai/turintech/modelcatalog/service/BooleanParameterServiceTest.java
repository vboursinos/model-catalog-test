package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class BooleanParameterServiceTest extends BasicServiceTest {
  private final String EXISTING_BOOLEAN_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String EXISTING_BOOLEAN_PARAMETER_ID_FOR_UPDATE =
      "323e4567-e89b-12d3-a456-426614174003";
  private final String EXISTING_BOOLEAN_PARAMETER_TYPE_DEFINITION_ID =
      "323e4567-e89b-12d3-a456-426614174003";
  private final String NON_EXISTING_BOOLEAN_PARAMETER_ID = UUID.randomUUID().toString();

  @Autowired private BooleanParameterService booleanParameterService;

  private BooleanParameterDTO getBooleanParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(
        UUID.fromString(EXISTING_BOOLEAN_PARAMETER_TYPE_DEFINITION_ID));
    parameterTypeDefinitionDTO.setOrdering(10);

    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID_FOR_UPDATE));
    booleanParameterDTO.setDefaultValue(false);
    return booleanParameterDTO;
  }

  @Test
  void testFindAllBooleanParameterService() {
    Mono<List<BooleanParameterDTO>> booleanParametersMono = booleanParameterService.findAll();
    List<BooleanParameterDTO> booleanParameters = booleanParametersMono.block();
    Assert.assertNotNull(booleanParameters);
    Assert.assertEquals(2, booleanParameters.size());
  }

  @Test
  void testFindByIdBooleanParameterService() {
    UUID existingId = UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID);
    Mono<BooleanParameterDTO> booleanParameterDTOMono = booleanParameterService.findOne(existingId);

    StepVerifier.create(booleanParameterDTOMono)
        .expectNextMatches(
            booleanParameterDTO -> {
              System.out.println("Found BooleanParameter by ID: " + booleanParameterDTO);
              Assert.assertEquals(true, booleanParameterDTO.getDefaultValue());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdBooleanParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_BOOLEAN_PARAMETER_ID);
    Mono<Boolean> exists = booleanParameterService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdBooleanParameterServiceForNonExistingId() {
    Mono<Boolean> exists =
        booleanParameterService.existsById(UUID.fromString(NON_EXISTING_BOOLEAN_PARAMETER_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
