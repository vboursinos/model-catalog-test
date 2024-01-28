package ai.turintech.modelcatalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.*;
import jakarta.transaction.Transactional;
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
public class CategoricalParameterValueServiceTest extends BasicServiceTest {

  private final String EXISTING_CATEGORICAL_PARAMETER_VALUE_ID =
      "423e4567-e89b-12d3-a456-426614174003";
  private final String NON_EXISTING_CATEGORICAL_PARAMETER_VALUE_ID = UUID.randomUUID().toString();

  @Autowired private CategoricalParameterValueService categoricalParameterValueService;

  private CategoricalParameterValueDTO getCategoricalParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
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
    categoricalParameterValueDTO.setValue("test_value");
    return categoricalParameterValueDTO;
  }

  @Test
  void testFindAllCategoricalParameterValueService() {
    Mono<List<CategoricalParameterValueDTO>> categoricalParameterValues =
        categoricalParameterValueService.findAll();
    List<CategoricalParameterValueDTO> categoricalParameterValueDTOS =
        categoricalParameterValues.block();
    Assertions.assertNotNull(categoricalParameterValueDTOS);
    Assertions.assertEquals(4, categoricalParameterValueDTOS.size());
  }

  @Test
  void testFindByIdCategoricalParameterValueRepository() {
    UUID existingId = UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_VALUE_ID);
    Mono<CategoricalParameterValueDTO> categoricalParameterValueDTOMono =
        categoricalParameterValueService.findOne(existingId);

    StepVerifier.create(categoricalParameterValueDTOMono)
        .expectNextMatches(
            categoricalParameterValueDTO -> {
              System.out.println(
                  "Found CategoricalParameterValue by ID: " + categoricalParameterValueDTO);
              Assertions.assertTrue(categoricalParameterValueDTO.getValue().equals("Category3"));
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdCategoricalParameterValueServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_VALUE_ID);
    Mono<Boolean> exists = categoricalParameterValueService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdCategoricalParameterValueServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_CATEGORICAL_PARAMETER_VALUE_ID);
    Mono<Boolean> exists = categoricalParameterValueService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  @Transactional
  void testPartialUpdateCategoricalParameterValueService() {
    Mono<CategoricalParameterValueDTO> updatedCategoricalParameterValue =
        categoricalParameterValueService.partialUpdate(getUpdatedCategoricalParameterValueDTO());
    updatedCategoricalParameterValue.subscribe(
        categoricalParameterValueDTO -> {
          assertEquals(
              getUpdatedCategoricalParameterValueDTO().getValue(),
              categoricalParameterValueDTO.getValue());
          assertEquals(
              getUpdatedCategoricalParameterValueDTO().getId(),
              categoricalParameterValueDTO.getId());
        });
  }

  @Test
  @Transactional
  void testUpdateCategoricalParameterValueService() {
    Mono<CategoricalParameterValueDTO> updatedCategoricalParameterValue =
        categoricalParameterValueService.update(getUpdatedCategoricalParameterValueDTO());
    updatedCategoricalParameterValue.subscribe(
        categoricalParameterValueDTO -> {
          assertEquals(
              getUpdatedCategoricalParameterValueDTO().getValue(),
              categoricalParameterValueDTO.getValue());
          assertEquals(
              getUpdatedCategoricalParameterValueDTO().getId(),
              categoricalParameterValueDTO.getId());
        });
  }

  @Test
  @Transactional
  void testSaveCategoricalParameterValueService() {
    Mono<CategoricalParameterValueDTO> savedCategoricalParameterValue =
        categoricalParameterValueService.save(getCategoricalParameterValueDTO());
    savedCategoricalParameterValue.subscribe(
        categoricalParameterValueDTO -> {
          assertEquals(
              getCategoricalParameterValueDTO().getValue(),
              categoricalParameterValueDTO.getValue());
          categoricalParameterValueService.delete(categoricalParameterValueDTO.getId()).block();
        });
  }
}
