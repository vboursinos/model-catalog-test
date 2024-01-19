package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
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
public class ModelGroupTypeFacadeTest extends BasicFacadeTest {
  @Autowired private ModelGroupTypeFacade modelGroupTypeFacade;

  private ModelGroupTypeDTO getModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setName("test_name");
    return modelGroupTypeDTO;
  }

  private ModelGroupTypeDTO getUpdatedModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    modelGroupTypeDTO.setName("test_updated_modelgrouptype");
    return modelGroupTypeDTO;
  }

  @Test
  void testFindAllModelGroupTypeFacade() {
    Flux<ModelGroupTypeDTO> modelGroupTypes = modelGroupTypeFacade.findAll();

    modelGroupTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            modelGroupTypeDTOS -> {
              assertEquals(
                  4, modelGroupTypeDTOS.size(), "Returned group types do not match expected size");
            });
  }

  @Test
  void testFindByIdModelGroupTypeFacade() {
    Mono<ModelGroupTypeDTO> modelGroupTypeDTOMono =
        modelGroupTypeFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelGroupTypeDTOMono.subscribe(
        modelGroupTypeDTO -> {
          Assert.assertEquals("modelgroup1", modelGroupTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelGroupTypeFacade() {
    Mono<Boolean> exists =
        modelGroupTypeFacade.existsById(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingModelGroupTypeFacade() {
    // Use a non-existing ID
    UUID nonExistingModelGroupTypeId = UUID.randomUUID();

    Mono<Boolean> exists = modelGroupTypeFacade.existsById(nonExistingModelGroupTypeId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelGroupTypeFacade() {
    Mono<ModelGroupTypeDTO> savedModelGroupTypeDTO =
        modelGroupTypeFacade.save(getModelGroupTypeDTO());
    savedModelGroupTypeDTO.subscribe(
        modelGroupTypeDTO -> {
          Assert.assertEquals(getModelGroupTypeDTO().getName(), modelGroupTypeDTO.getName());
          modelGroupTypeFacade.delete(modelGroupTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelGroupTypeFacade() {
    Mono<ModelGroupTypeDTO> updatedModelGroupTypeDTO =
        modelGroupTypeFacade.save(getUpdatedModelGroupTypeDTO());
    updatedModelGroupTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelGroupTypeDTO().getName(), modelTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelGroupTypeFacade() {
    // Save a metric first
    Mono<ModelGroupTypeDTO> savedMetric = modelGroupTypeFacade.save(getModelGroupTypeDTO());

    // Subscribe and delete the saved metric
    savedMetric.subscribe(
        modelGroupTypeDTO -> {
          Mono<Void> deleteResult = modelGroupTypeFacade.delete(modelGroupTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted metric by ID
                Mono<ModelGroupTypeDTO> findResult =
                    modelGroupTypeFacade.findOne(modelGroupTypeDTO.getId());
                findResult.subscribe(
                    notFoundMetricDTO -> Assert.assertNull(notFoundMetricDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingModelGroupTypeFacade() {
    // Try to find a metric by a non-existing ID
    Mono<ModelGroupTypeDTO> metric = modelGroupTypeFacade.findOne(UUID.randomUUID());

    StepVerifier.create(metric).expectError(NoSuchElementException.class).verify();
  }
}
