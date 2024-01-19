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

  private BooleanParameterDTO getBooleanParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
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
        booleanParameterFacade.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    booleanParameterDTOMono.subscribe(
        booleanParameterDTO -> {
          Assert.assertEquals(true, booleanParameterDTO.getDefaultValue());
        });
  }

  @Test
  @Transactional
  void testDeleteBooleanParameterFacade() {
    // Save a boolean parameter first
    Mono<BooleanParameterDTO> savedBooleanParameter =
        booleanParameterFacade.save(getBooleanParameterDTO());

    // Subscribe and delete the saved boolean parameter
    savedBooleanParameter.subscribe(
        booleanParameterDTO -> {
          Mono<Void> deleteResult = booleanParameterFacade.delete(booleanParameterDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted boolean parameter by ID
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
