package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
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
public class CategoricalParameterValueFacadeTest extends BasicFacadeTest {
  @Autowired private CategoricalParameterValueFacade categoricalParameterValueFacade;

  private CategoricalParameterValueDTO getCategoricalParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    //    categoricalParameterValueDTO.SE(categoricalParameter);
    categoricalParameterValueDTO.setValue("test_value");
    return categoricalParameterValueDTO;
  }

  private CategoricalParameterValueDTO getUpdatedCategoricalParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    //    categoricalParameterValueDTO.setCategoricalParameter(categoricalParameter);
    categoricalParameterValueDTO.setValue("test_value");
    return categoricalParameterValueDTO;
  }

  @Test
  void testFindAllCategoricalParameterValueFacade() {
    Flux<CategoricalParameterValueDTO> categoricalParameterValues =
        categoricalParameterValueFacade.findAll();
    categoricalParameterValues
        .collectList()
        .blockOptional()
        .ifPresent(
            categoricalParameterValueDTOS -> {
              assertEquals(
                  4,
                  categoricalParameterValueDTOS.size(),
                  "Returned categorical parameter values do not match expected size");
            });
  }

  @Test
  void testFindByIdCategoricalParameterValueFacade() {
    Mono<CategoricalParameterValueDTO> categoricalParameterValueDTOMono =
        categoricalParameterValueFacade.findOne(
            UUID.fromString("423e4567-e89b-12d3-a456-426614174003"));

    categoricalParameterValueDTOMono.subscribe(
        categoricalParameterValueDTO -> {
          Assert.assertTrue(categoricalParameterValueDTO.getValue().equals("Category3"));
        });
  }

  @Test
  @Transactional
  void testDeleteCategoricalParameterValueFacade() {
    // Save a categorical parameter value first
    Mono<CategoricalParameterValueDTO> savedCategoricalParameterValue =
        categoricalParameterValueFacade.save(getCategoricalParameterValueDTO());

    // Subscribe and delete the saved categorical parameter value
    savedCategoricalParameterValue.subscribe(
        categoricalParameterValueDTO -> {
          Mono<Void> deleteResult =
              categoricalParameterValueFacade.delete(categoricalParameterValueDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted categorical parameter value by ID
                Mono<CategoricalParameterValueDTO> findResult =
                    categoricalParameterValueFacade.findOne(categoricalParameterValueDTO.getId());
                findResult.subscribe(
                    notFoundCategoricalParameterValueDTO ->
                        Assert.assertNull(notFoundCategoricalParameterValueDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingCategoricalParameterValueFacade() {
    // Try to find a categorical parameter value by a non-existing ID
    Mono<CategoricalParameterValueDTO> categoricalParameterValue =
        categoricalParameterValueFacade.findOne(UUID.randomUUID());

    StepVerifier.create(categoricalParameterValue)
        .expectError(NoSuchElementException.class)
        .verify();
  }
}
