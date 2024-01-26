package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
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
public class ModelFamilyTypeFacadeTest extends BasicFacadeTest {
  private final String EXISTING_MODEL_FAMILY_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private final String EXISTING_MODEL_FAMILY_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private final String NON_EXISTING_MODEL_FAMILY_TYPE_ID = UUID.randomUUID().toString();

  @Autowired private ModelFamilyTypeFacade modelFamilyTypeFacade;

  private ModelFamilyTypeDTO getModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setName("test_name");
    return modelFamilyTypeDTO;
  }

  private ModelFamilyTypeDTO getUpdatedModelFamilyTypeDTO() {
    ModelFamilyTypeDTO modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID_FOR_UPDATE));
    modelFamilyTypeDTO.setName("test_updated_modelfamilytype");
    return modelFamilyTypeDTO;
  }

  @Test
  void testFindAllModelFamilyTypeFacade() {
    Flux<ModelFamilyTypeDTO> modelFamilyTypes = modelFamilyTypeFacade.findAll();

    modelFamilyTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            modelFamilyTypeDTOS -> {
              assertEquals(
                  4,
                  modelFamilyTypeDTOS.size(),
                  "Returned family types do not match expected size");
            });
  }

  @Test
  void testFindByIdModelFamilyTypeFacade() {
    Mono<ModelFamilyTypeDTO> modelFamilyTypeDTOMono =
        modelFamilyTypeFacade.findOne(UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID));

    modelFamilyTypeDTOMono.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals("modelfamilytype1", modelFamilyTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelFamilyTypeFacade() {
    Mono<Boolean> exists =
        modelFamilyTypeFacade.existsById(UUID.fromString(EXISTING_MODEL_FAMILY_TYPE_ID));

    exists.subscribe(
        modelFamilyType -> {
          Assert.assertEquals(true, exists.block());
        });
  }

  @Test
  void testExistsByIdNonExistingModelFamilyTypeFacade() {
    Mono<Boolean> exists =
        modelFamilyTypeFacade.existsById(UUID.fromString(NON_EXISTING_MODEL_FAMILY_TYPE_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelFamilyTypeFacade() {
    Mono<ModelFamilyTypeDTO> savedModelFamilyTypeDTO =
        modelFamilyTypeFacade.save(getModelFamilyTypeDTO());
    savedModelFamilyTypeDTO.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals(getModelFamilyTypeDTO().getName(), modelFamilyTypeDTO.getName());
          modelFamilyTypeFacade.delete(modelFamilyTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelFamilyTypeFacade() {
    Mono<ModelFamilyTypeDTO> updatedModelFamilyTypeDTO =
        modelFamilyTypeFacade.save(getUpdatedModelFamilyTypeDTO());
    updatedModelFamilyTypeDTO.subscribe(
        modelFamilyTypeDTO -> {
          Assert.assertEquals(
              getUpdatedModelFamilyTypeDTO().getName(), modelFamilyTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelFamilyTypeFacade() {
    Mono<ModelFamilyTypeDTO> savedMetric = modelFamilyTypeFacade.save(getModelFamilyTypeDTO());

    savedMetric.subscribe(
        modelFamilyTypeDTO -> {
          Mono<Void> deleteResult = modelFamilyTypeFacade.delete(modelFamilyTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
                Mono<ModelFamilyTypeDTO> findResult =
                    modelFamilyTypeFacade.findOne(modelFamilyTypeDTO.getId());
                findResult.subscribe(
                    notFoundMetricDTO -> Assert.assertNull(notFoundMetricDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingModelFamilyTypeFacade() {
    Mono<ModelFamilyTypeDTO> metric =
        modelFamilyTypeFacade.findOne(UUID.fromString(NON_EXISTING_MODEL_FAMILY_TYPE_ID));

    StepVerifier.create(metric).expectError(NoSuchElementException.class).verify();
  }
}
