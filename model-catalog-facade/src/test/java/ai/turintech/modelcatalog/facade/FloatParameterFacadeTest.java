package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class FloatParameterFacadeTest extends BasicFacadeTest {
  @Autowired private FloatParameterFacade floatParameterFacade;

  private FloatParameterDTO getFloatParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    floatParameterDTO.setDefaultValue(1.1);
    return floatParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllFloatParameterFacade() {
    Flux<FloatParameterDTO> floatParametersMono = floatParameterFacade.findAll();
    floatParametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            floatParameterDTOS -> {
              assertEquals(
                  2,
                  floatParameterDTOS.size(),
                  "Returned float parameters do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdFloatParameterFacade() {
    Mono<FloatParameterDTO> floatParameterDTOMono =
        floatParameterFacade.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    floatParameterDTOMono.subscribe(
        floatParameterDTO -> {
          Assert.assertTrue(floatParameterDTO.getDefaultValue() == 10.1);
        });
  }

  @Test
  void testExistsByIdFloatParameterFacade() {
    // Assume you have a known ID for an existing categorical parameter
    UUID existingFloatParameterId = UUID.fromString("323e4567-e89b-12d3-a456-426614174001");

    Mono<Boolean> exists = floatParameterFacade.existsById(existingFloatParameterId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingFloatParameterFacade() {
    // Use a non-existing ID
    UUID nonExistingFloatParameterId = UUID.randomUUID();

    Mono<Boolean> exists = floatParameterFacade.existsById(nonExistingFloatParameterId);
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  @Transactional
  void testDeleteFloatParameterFacade() {
    // Save a float parameter first
    Mono<FloatParameterDTO> savedFloatParameter = floatParameterFacade.save(getFloatParameterDTO());

    // Subscribe and delete the saved float parameter
    savedFloatParameter.subscribe(
        floatParameterDTO -> {
          Mono<Void> deleteResult = floatParameterFacade.delete(floatParameterDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted float parameter by ID
                Mono<FloatParameterDTO> findResult =
                    floatParameterFacade.findOne(floatParameterDTO.getId());
                findResult.subscribe(
                    notFoundFloatParameterDTO -> Assert.assertNull(notFoundFloatParameterDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingFloatParameterFacade() {
    // Try to find a float parameter by a non-existing ID
    Mono<FloatParameterDTO> floatParameter = floatParameterFacade.findOne(UUID.randomUUID());

    StepVerifier.create(floatParameter).expectError(NoSuchElementException.class).verify();
  }
}
