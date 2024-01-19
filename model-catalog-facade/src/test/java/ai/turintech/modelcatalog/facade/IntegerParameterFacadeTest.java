package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
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
public class IntegerParameterFacadeTest extends BasicFacadeTest {
  @Autowired private IntegerParameterFacade integerParameterFacade;

  private IntegerParameterDTO getIntegerParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    IntegerParameterDTO integerParameterDTO = new IntegerParameterDTO();
    integerParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    integerParameterDTO.setDefaultValue(1);
    return integerParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllIntegerParameterFacade() {
    Flux<IntegerParameterDTO> integerParametersMono = integerParameterFacade.findAll();
    integerParametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            integerParameterDTOS -> {
              assertEquals(
                  2,
                  integerParameterDTOS.size(),
                  "Returned integer parameters do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdIntegerParameterFacade() {
    Mono<IntegerParameterDTO> integerParameterDTOMono =
        integerParameterFacade.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameterDTOMono.subscribe(
        integerParameterDTO -> {
          Assert.assertTrue(integerParameterDTO.getDefaultValue() == 10);
        });
  }

  @Test
  void testExistsByIdIntegerParameterFacade() {
    // Save an integer parameter first
    UUID existingIntegerParameterId = UUID.fromString("323e4567-e89b-12d3-a456-426614174001");

    Mono<Boolean> exists = integerParameterFacade.existsById(existingIntegerParameterId);
    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingIntegerParameterFacade() {
    // Use a non-existing ID
    UUID nonExistingIntegerParameterId = UUID.randomUUID();

    Mono<Boolean> exists = integerParameterFacade.existsById(nonExistingIntegerParameterId);
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  @Transactional
  void testDeleteIntegerParameterFacade() {
    // Save an integer parameter first
    Mono<IntegerParameterDTO> savedIntegerParameter =
        integerParameterFacade.save(getIntegerParameterDTO());

    // Subscribe and delete the saved integer parameter
    savedIntegerParameter.subscribe(
        integerParameterDTO -> {
          Mono<Void> deleteResult = integerParameterFacade.delete(integerParameterDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted integer parameter by ID
                Mono<IntegerParameterDTO> findResult =
                    integerParameterFacade.findOne(integerParameterDTO.getId());
                findResult.subscribe(
                    notFoundIntegerParameterDTO -> Assert.assertNull(notFoundIntegerParameterDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingIntegerParameterFacade() {
    // Try to find an integer parameter by a non-existing ID
    Mono<IntegerParameterDTO> integerParameter = integerParameterFacade.findOne(UUID.randomUUID());

    StepVerifier.create(integerParameter).expectError(NoSuchElementException.class).verify();
  }
}
