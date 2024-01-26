package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.IntegerParameter;
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
public class IntegerParameterValueServiceTest extends BasicServiceTest {

  private final String EXISTING_INTEGER_PARAMETER_VALUE_ID = "423e4567-e89b-12d3-a456-426614174004";
  private final String NON_EXISTING_INTEGER_PARAMETER_VALUE_ID =
      "123e4567-e89b-12d3-a456-426614174099";

  @Autowired private IntegerParameterValueService integerParameterValueService;

  private IntegerParameterValueDTO getIntegerParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    integerParameterValueDTO.setLower(1);
    integerParameterValueDTO.setUpper(10);
    return integerParameterValueDTO;
  }

  private IntegerParameterValueDTO getUpdatedIntegerParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    integerParameterValueDTO.setId(UUID.fromString(EXISTING_INTEGER_PARAMETER_VALUE_ID));
    //    integerParameterValueDTO.setIntegerParameter(integerParameter);
    integerParameterValueDTO.setLower(1);
    integerParameterValueDTO.setUpper(10);
    return integerParameterValueDTO;
  }

  @Test
  void testFindAllIntegerParameterValueService() {
    Mono<List<IntegerParameterValueDTO>> integerParameterValues =
        integerParameterValueService.findAll();
    List<IntegerParameterValueDTO> integerParameterValueDTOS = integerParameterValues.block();
    Assertions.assertNotNull(integerParameterValueDTOS);
    Assertions.assertEquals(4, integerParameterValueDTOS.size());
  }

  @Test
  void testFindByIdIntegerParameterValueService() {
    UUID existingId = UUID.fromString(EXISTING_INTEGER_PARAMETER_VALUE_ID);
    Mono<IntegerParameterValueDTO> integerParameterValueMonoDTO =
        integerParameterValueService.findOne(existingId);

    StepVerifier.create(integerParameterValueMonoDTO)
        .expectNextMatches(
            integerParameterValueDTO -> {
              System.out.println("Found IntegerParameterValue by ID: " + integerParameterValueDTO);
              Assertions.assertTrue(integerParameterValueDTO.getLower() == 25);
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdWithExistingId() {
    UUID existingId = UUID.fromString(EXISTING_INTEGER_PARAMETER_VALUE_ID);
    Mono<Boolean> existsMono = integerParameterValueService.existsById(existingId);

    StepVerifier.create(existsMono).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdWithNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_INTEGER_PARAMETER_VALUE_ID);
    Mono<Boolean> existsMono = integerParameterValueService.existsById(nonExistingId);

    StepVerifier.create(existsMono).expectNext(false).verifyComplete();
  }
}
