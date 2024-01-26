package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
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
public class ModelEnsembleTypeFacadeTest extends BasicFacadeTest {

  private final String EXISTING_MODEL_ENSEMBLE_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private final String NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID = UUID.randomUUID().toString();

  @Autowired private ModelEnsembleTypeFacade modelEnsembleTypeFacade;

  private ModelEnsembleTypeDTO getModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setName("test_name");
    return modelEnsembleTypeDTO;
  }

  private ModelEnsembleTypeDTO getUpdatedModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    modelEnsembleTypeDTO.setName("test_updated_modelEnsembletype");
    return modelEnsembleTypeDTO;
  }

  @Test
  void testFindAllModelEnsembleTypeFacade() {
    Flux<ModelEnsembleTypeDTO> modelEnsembleTypes = modelEnsembleTypeFacade.findAll();
    modelEnsembleTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            modelEnsembleTypeDTOS -> {
              assertEquals(
                  4,
                  modelEnsembleTypeDTOS.size(),
                  "Returned ensemble types do not match expected size");
            });
  }

  @Test
  void testFindByIdModelEnsembleTypeFacade() {
    Mono<ModelEnsembleTypeDTO> modelEnsembleTypeDTOMono =
        modelEnsembleTypeFacade.findOne(UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID));

    modelEnsembleTypeDTOMono.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals("modelensembletype1", modelEnsembleTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelEnsembleTypeFacade() {
    Mono<Boolean> exists =
        modelEnsembleTypeFacade.existsById(UUID.fromString(EXISTING_MODEL_ENSEMBLE_TYPE_ID));

    exists.subscribe(
        modelEnsembleType -> {
          Assert.assertEquals(true, exists.block());
        });
  }

  @Test
  void testExistsByIdNonExistingModelEnsembleTypeFacade() {

    Mono<Boolean> exists =
        modelEnsembleTypeFacade.existsById(UUID.fromString(NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelEnsembleTypeFacade() {
    Mono<ModelEnsembleTypeDTO> savedModelEnsembleTypeDTO =
        modelEnsembleTypeFacade.save(getModelEnsembleTypeDTO());
    savedModelEnsembleTypeDTO.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals(getModelEnsembleTypeDTO().getName(), modelEnsembleTypeDTO.getName());
          modelEnsembleTypeFacade.delete(modelEnsembleTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelEnsembleTypeFacade() {
    Mono<ModelEnsembleTypeDTO> updatedModelEnsembleTypeDTO =
        modelEnsembleTypeFacade.save(getUpdatedModelEnsembleTypeDTO());
    updatedModelEnsembleTypeDTO.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals(
              getUpdatedModelEnsembleTypeDTO().getName(), modelEnsembleTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelEnsembleTypeFacade() {
    Mono<ModelEnsembleTypeDTO> savedMetric =
        modelEnsembleTypeFacade.save(getModelEnsembleTypeDTO());

    savedMetric.subscribe(
        modelEnsembleTypeDTO -> {
          Mono<Void> deleteResult = modelEnsembleTypeFacade.delete(modelEnsembleTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
                Mono<ModelEnsembleTypeDTO> findResult =
                    modelEnsembleTypeFacade.findOne(modelEnsembleTypeDTO.getId());
                findResult.subscribe(
                    notFoundMetricDTO -> Assert.assertNull(notFoundMetricDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingModelEnsembleTypeFacade() {
    Mono<ModelEnsembleTypeDTO> metric =
        modelEnsembleTypeFacade.findOne(UUID.fromString(NON_EXISTING_MODEL_ENSEMBLE_TYPE_ID));

    StepVerifier.create(metric).expectError(NoSuchElementException.class).verify();
  }
}
