package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.*;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class IntegerParameterValueFacadeTest extends BasicFacadeTest {
  @Autowired private IntegerParameterValueFacade integerParameterValueFacade;

  private IntegerParameterValueDTO getIntegerParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    //    integerParameterValueDTO.setIntegerParameter(integerParameter);
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
    integerParameterValueDTO.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    //    integerParameterValueDTO.setIntegerParameter(integerParameter);
    integerParameterValueDTO.setLower(1);
    integerParameterValueDTO.setUpper(10);
    return integerParameterValueDTO;
  }

  @Test
  void testFindAllIntegerParameterValueFacade() {
    Flux<IntegerParameterValueDTO> integerParameterValues = integerParameterValueFacade.findAll();
    integerParameterValues
        .collectList()
        .blockOptional()
        .ifPresent(
            integerParameterValueDTOS -> {
              assertEquals(
                  4,
                  integerParameterValueDTOS.size(),
                  "Returned integer parameter values do not match expected size");
            });
  }

  @Test
  void testFindByIdIntegerParameterValueFacade() {
    Mono<IntegerParameterValueDTO> integerParameterValueMonoDTO =
        integerParameterValueFacade.findOne(
            UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    integerParameterValueMonoDTO.subscribe(
        integerParameterValueDTO -> {
          Assert.assertTrue(integerParameterValueDTO.getLower() == 25);
        });
  }

  @Test
  @Transactional
  void testDeleteIntegerParameterValueFacade() {
    // Save an integer parameter value first
    Mono<IntegerParameterValueDTO> savedIntegerParameterValue =
        integerParameterValueFacade.save(getIntegerParameterValueDTO());

    // Subscribe and delete the saved integer parameter value
    savedIntegerParameterValue.subscribe(
        integerParameterValueDTO -> {
          Mono<Void> deleteResult =
              integerParameterValueFacade.delete(integerParameterValueDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted integer parameter value by ID
                Mono<IntegerParameterValueDTO> findResult =
                    integerParameterValueFacade.findOne(integerParameterValueDTO.getId());
                findResult.subscribe(
                    notFoundIntegerParameterValueDTO ->
                        Assert.assertNull(notFoundIntegerParameterValueDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingIntegerParameterValueFacade() {
    // Try to find an integer parameter value by a non-existing ID
    Mono<IntegerParameterValueDTO> integerParameterValue =
        integerParameterValueFacade.findOne(UUID.randomUUID());

    StepVerifier.create(integerParameterValue).expectError(NoSuchElementException.class).verify();
  }
}
