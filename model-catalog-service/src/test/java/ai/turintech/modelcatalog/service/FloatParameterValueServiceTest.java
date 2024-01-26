package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
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
public class FloatParameterValueServiceTest extends BasicServiceTest {

  private final String EXISTING_FLOAT_PARAMETER_RANGE_ID = "423e4567-e89b-12d3-a456-426614174004";
  private final String NON_EXISTING_FLOAT_PARAMETER_RANGE_ID = UUID.randomUUID().toString();

  @Autowired private FloatParameterRangeService floatParameterRangeService;

  private FloatParameterRangeDTO getFloatParameterRangeDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"));
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);

    FloatParameterRangeDTO floatParameterRangeDTO = new FloatParameterRangeDTO();
    floatParameterRangeDTO.setLower(1.0);
    floatParameterRangeDTO.setUpper(10.0);
    floatParameterRangeDTO.setIsLeftOpen(false);
    floatParameterRangeDTO.setIsRightOpen(false);
    return floatParameterRangeDTO;
  }

  private FloatParameterRangeDTO getUpdatedFloatParameterRangeDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"));
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);

    FloatParameterRangeDTO floatParameterRangeDTO = new FloatParameterRangeDTO();
    floatParameterRangeDTO.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174003"));
    floatParameterRangeDTO.setLower(1.0);
    floatParameterRangeDTO.setUpper(10.0);
    floatParameterRangeDTO.setIsLeftOpen(false);
    floatParameterRangeDTO.setIsRightOpen(false);
    return floatParameterRangeDTO;
  }

  @Test
  void testFindAllFloatParameterRangeService() {
    Mono<List<FloatParameterRangeDTO>> floatParameterRangesDTOMono =
        floatParameterRangeService.findAll();
    List<FloatParameterRangeDTO> floatParameterRanges = floatParameterRangesDTOMono.block();
    Assertions.assertNotNull(floatParameterRanges);
    Assertions.assertEquals(4, floatParameterRanges.size());
  }

  @Test
  void testFindByIdFloatParameterRangeService() {
    UUID existingId = UUID.fromString(EXISTING_FLOAT_PARAMETER_RANGE_ID);
    Mono<FloatParameterRangeDTO> floatParameterRangeDTOMono =
        floatParameterRangeService.findOne(existingId);

    StepVerifier.create(floatParameterRangeDTOMono)
        .expectNextMatches(
            floatParameterRangeDTO -> {
              System.out.println("Found FloatParameterRange by ID: " + floatParameterRangeDTO);
              Assertions.assertTrue(floatParameterRangeDTO.getLower() == 25.3);
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdFloatParameterRangeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_FLOAT_PARAMETER_RANGE_ID);
    Mono<Boolean> exists = floatParameterRangeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdFloatParameterRangeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_FLOAT_PARAMETER_RANGE_ID);
    Mono<Boolean> exists = floatParameterRangeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
