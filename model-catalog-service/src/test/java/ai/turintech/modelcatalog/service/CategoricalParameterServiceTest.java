package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class CategoricalParameterServiceTest extends BasicServiceTest {

  private final String EXISTING_CATEGORICAL_PARAMETER_ID = "323e4567-e89b-12d3-a456-426614174001";
  private final String EXISTING_CATEGORICAL_PARAMETER_TYPE_DEFINITION_ID =
      "323e4567-e89b-12d3-a456-426614174003";
  private final String NON_EXISTING_CATEGORICAL_PARAMETER_ID = UUID.randomUUID().toString();

  @Autowired private CategoricalParameterService categoricalParameterService;

  private CategoricalParameterDTO getCategoricalParameterDTO() {
    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(
        UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_TYPE_DEFINITION_ID));
    parameterTypeDefinitionDTO.setOrdering(10);

    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_ID));
    categoricalParameterDTO.setDefaultValue("test_default_value");
    return categoricalParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllCategoricalParameterService() {
    Mono<List<CategoricalParameterDTO>> categoricalParametersMono =
        categoricalParameterService.findAll();
    List<CategoricalParameterDTO> categoricalParameterDTOS = categoricalParametersMono.block();
    Assert.assertNotNull(categoricalParameterDTOS);
    Assert.assertEquals(2, categoricalParameterDTOS.size());
  }

  @Test
  @Transactional
  void testFindByIdCategoricalParameterService() {
    UUID existingId = UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_ID);
    Mono<CategoricalParameterDTO> categoricalParameterDTOMono =
        categoricalParameterService.findOne(existingId);

    StepVerifier.create(categoricalParameterDTOMono)
        .expectNextMatches(
            categoricalParameterDTO -> {
              System.out.println("Found CategoricalParameter by ID: " + categoricalParameterDTO);
              Assert.assertEquals("value1", categoricalParameterDTO.getDefaultValue());
              return true;
            })
        .verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdCategoricalParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_CATEGORICAL_PARAMETER_ID);
    Mono<Boolean> exists = categoricalParameterService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdCategoricalParameterServiceForNonExistingId() {
    Mono<Boolean> exists =
        categoricalParameterService.existsById(
            UUID.fromString(NON_EXISTING_CATEGORICAL_PARAMETER_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }
}
