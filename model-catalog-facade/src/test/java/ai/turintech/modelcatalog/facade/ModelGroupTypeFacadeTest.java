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
  private final String EXISTING_MODEL_GROUP_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private final String EXISTING_MODEL_GROUP_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private final String NON_EXISTING_MODEL_GROUP_TYPE_ID = UUID.randomUUID().toString();

  @Autowired private ModelGroupTypeFacade modelGroupTypeFacade;

  private ModelGroupTypeDTO getModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setName("test_name");
    return modelGroupTypeDTO;
  }

  private ModelGroupTypeDTO getUpdatedModelGroupTypeDTO() {
    ModelGroupTypeDTO modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID_FOR_UPDATE));
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
        modelGroupTypeFacade.findOne(UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID));

    modelGroupTypeDTOMono.subscribe(
        modelGroupTypeDTO -> {
          Assert.assertEquals("modelgroup1", modelGroupTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelGroupTypeFacade() {
    Mono<Boolean> exists =
        modelGroupTypeFacade.existsById(UUID.fromString(EXISTING_MODEL_GROUP_TYPE_ID));

    exists.subscribe(
        modelGroupType -> {
          Assert.assertEquals(true, exists.block());
        });
  }

  @Test
  void testExistsByIdNonExistingModelGroupTypeFacade() {
    Mono<Boolean> exists =
        modelGroupTypeFacade.existsById(UUID.fromString(NON_EXISTING_MODEL_GROUP_TYPE_ID));

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
        modelGroupTypeFacade.update(getUpdatedModelGroupTypeDTO());
    updatedModelGroupTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelGroupTypeDTO().getName(), modelTypeDTO.getName());
        });
  }

  @Test
  void testPartialUpdateModelGroupTypeFacade() {
    Mono<ModelGroupTypeDTO> updatedModelGroupTypeDTO =
        modelGroupTypeFacade.partialUpdate(getUpdatedModelGroupTypeDTO());
    updatedModelGroupTypeDTO.subscribe(
        modelTypeDTO -> {
          Assert.assertEquals(getUpdatedModelGroupTypeDTO().getName(), modelTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelGroupTypeFacade() {
    Mono<ModelGroupTypeDTO> savedMetric = modelGroupTypeFacade.save(getModelGroupTypeDTO());

    savedMetric.subscribe(
        modelGroupTypeDTO -> {
          Mono<Void> deleteResult = modelGroupTypeFacade.delete(modelGroupTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
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
    Mono<ModelGroupTypeDTO> metric =
        modelGroupTypeFacade.findOne(UUID.fromString(NON_EXISTING_MODEL_GROUP_TYPE_ID));

    StepVerifier.create(metric).expectError(NoSuchElementException.class).verify();
  }
}
