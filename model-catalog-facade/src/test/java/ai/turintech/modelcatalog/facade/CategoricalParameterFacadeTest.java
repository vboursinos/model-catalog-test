package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
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
public class CategoricalParameterFacadeTest extends BasicFacadeTest {
  @Autowired private CategoricalParameterFacade categoricalParameterFacade;

  private CategoricalParameterDTO getCategoricalParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    categoricalParameterDTO.setDefaultValue("test_default_value");
    return categoricalParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllCategoricalParameterFacade() {
    Flux<CategoricalParameterDTO> categoricalParametersMono = categoricalParameterFacade.findAll();
    categoricalParametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            categoricalParameterDTOS -> {
              assertEquals(
                  2,
                  categoricalParameterDTOS.size(),
                  "Returned categorical parameters do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdCategoricalParameterFacade() {
    Mono<CategoricalParameterDTO> categoricalParameterDTOMono =
        categoricalParameterFacade.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameterDTOMono.subscribe(
        categoricalParameterDTO -> {
          Assert.assertEquals("value1", categoricalParameterDTO.getDefaultValue());
        });
  }

  @Test
  @Transactional
  void testDeleteCategoricalParameterFacade() {
    // Save a categorical parameter first
    Mono<CategoricalParameterDTO> savedCategoricalParameter =
        categoricalParameterFacade.save(getCategoricalParameterDTO());

    // Subscribe and delete the saved categorical parameter
    savedCategoricalParameter.subscribe(
        categoricalParameterDTO -> {
          Mono<Void> deleteResult =
              categoricalParameterFacade.delete(categoricalParameterDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted categorical parameter by ID
                Mono<CategoricalParameterDTO> findResult =
                    categoricalParameterFacade.findOne(categoricalParameterDTO.getId());
                findResult.subscribe(
                    notFoundCategoricalParameterDTO ->
                        Assert.assertNull(notFoundCategoricalParameterDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingCategoricalParameterFacade() {
    // Try to find a categorical parameter by a non-existing ID
    Mono<CategoricalParameterDTO> categoricalParameter =
        categoricalParameterFacade.findOne(UUID.randomUUID());

    StepVerifier.create(categoricalParameter).expectError(NoSuchElementException.class).verify();
  }
}
