package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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
public class FloatParameterServiceTest extends BasicServiceTest {

  private final String EXISTING_FLOAT_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String NON_EXISTING_FLOAT_PARAMETER_ID = UUID.randomUUID().toString();

  @Autowired private FloatParameterService floatParameterService;

  private FloatParameterDTO getFloatParameterDTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.fromString(EXISTING_FLOAT_PARAMETER_ID));
    floatParameterDTO.setDefaultValue(1.1);
    return floatParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllFloatParameterService() {
    Mono<List<FloatParameterDTO>> floatParametersMono = floatParameterService.findAll();
    List<FloatParameterDTO> floatParameterDTOS = floatParametersMono.block();
    Assert.assertNotNull(floatParameterDTOS);
    Assert.assertEquals(2, floatParameterDTOS.size());
  }

  @Test
  @Transactional
  void testFindByIdFloatParameterService() {
    UUID existingId = UUID.fromString(EXISTING_FLOAT_PARAMETER_ID);
    Mono<FloatParameterDTO> floatParameterDTOMono = floatParameterService.findOne(existingId);

    StepVerifier.create(floatParameterDTOMono)
        .expectNextMatches(
            floatParameterDTO -> {
              System.out.println("Found FloatParameter by ID: " + floatParameterDTO);
              Assertions.assertTrue(floatParameterDTO.getDefaultValue() == 10.1);
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdFloatParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_FLOAT_PARAMETER_ID);
    Mono<Boolean> exists = floatParameterService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdFloatParameterServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_FLOAT_PARAMETER_ID);
    Mono<Boolean> exists = floatParameterService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
