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
        modelEnsembleTypeFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelEnsembleTypeDTOMono.subscribe(
        modelEnsembleTypeDTO -> {
          Assert.assertEquals("modelensembletype1", modelEnsembleTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelEnsembleTypeFacade() {
    Mono<Boolean> exists =
        modelEnsembleTypeFacade.existsById(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingModelEnsembleTypeFacade() {
    // Use a non-existing ID
    UUID nonExistingModelEnsembleTypeId = UUID.randomUUID();

    Mono<Boolean> exists = modelEnsembleTypeFacade.existsById(nonExistingModelEnsembleTypeId);

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
    // Save a metric first
    Mono<ModelEnsembleTypeDTO> savedMetric =
        modelEnsembleTypeFacade.save(getModelEnsembleTypeDTO());

    // Subscribe and delete the saved metric
    savedMetric.subscribe(
        modelEnsembleTypeDTO -> {
          Mono<Void> deleteResult = modelEnsembleTypeFacade.delete(modelEnsembleTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted metric by ID
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
    // Try to find a metric by a non-existing ID
    Mono<ModelEnsembleTypeDTO> metric = modelEnsembleTypeFacade.findOne(UUID.randomUUID());

    StepVerifier.create(metric).expectError(NoSuchElementException.class).verify();
  }
}
