package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
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
public class BooleanParameterFacadeTest extends BasicFacadeTest {
  @Autowired private BooleanParameterFacade booleanParameterFacade;

  private final String BOOLEAN_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String NEW_BOOLEAN_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174003";
  private final String PARAMETER_TYPE_DEFINITION_ID = "323e4567-e89b-12d3-a456-426614174003";

  private BooleanParameterDTO getBooleanParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString(PARAMETER_TYPE_DEFINITION_ID));
    parameterTypeDefinitionDTO.setOrdering(10);

    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.fromString(NEW_BOOLEAN_PARAMETER_ID));
    booleanParameterDTO.setDefaultValue(false);
    return booleanParameterDTO;
  }

  @Test
  void testFindAllBooleanParameterFacade() {
    Flux<BooleanParameterDTO> booleanParametersMono = booleanParameterFacade.findAll();
    booleanParametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            booleanParameterDTOS -> {
              assertEquals(
                  2,
                  booleanParameterDTOS.size(),
                  "Returned boolean parameters do not match expected size");
            });
  }

  @Test
  void testFindByIdBooleanParameterFacade() {
    Mono<BooleanParameterDTO> booleanParameterDTOMono =
        booleanParameterFacade.findOne(UUID.fromString(BOOLEAN_PARAMETER_ID));
    booleanParameterDTOMono.subscribe(
        booleanParameterDTO -> {
          Assert.assertEquals(true, booleanParameterDTO.getDefaultValue());
        });
  }

  @Test
  void testExistsByIdBooleanParameterFacade() {
    // Assume you have a known ID for an existing boolean parameter
    UUID existingBooleanParameterId = UUID.fromString(BOOLEAN_PARAMETER_ID);

    Mono<Boolean> exists = booleanParameterFacade.existsById(existingBooleanParameterId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingBooleanParameterFacade() {
    // Use a non-existing ID
    UUID nonExistingBooleanParameterId = UUID.randomUUID();

    Mono<Boolean> exists = booleanParameterFacade.existsById(nonExistingBooleanParameterId);
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  @Transactional
  void testDeleteBooleanParameterFacade() {
    Mono<BooleanParameterDTO> savedBooleanParameter =
        booleanParameterFacade.save(getBooleanParameterDTO());

    savedBooleanParameter.subscribe(
        booleanParameterDTO -> {
          Mono<Void> deleteResult = booleanParameterFacade.delete(booleanParameterDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
                Mono<BooleanParameterDTO> findResult =
                    booleanParameterFacade.findOne(booleanParameterDTO.getId());
                findResult.subscribe(
                    notFoundBooleanParameterDTO -> Assert.assertNull(notFoundBooleanParameterDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingBooleanParameterFacade() {
    // Try to find a boolean parameter by a non-existing ID
    Mono<BooleanParameterDTO> booleanParameter = booleanParameterFacade.findOne(UUID.randomUUID());

    StepVerifier.create(booleanParameter).expectError(NoSuchElementException.class).verify();
  }
}
