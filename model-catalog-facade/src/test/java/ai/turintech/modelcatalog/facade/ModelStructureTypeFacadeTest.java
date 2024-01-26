package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
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
public class ModelStructureTypeFacadeTest extends BasicFacadeTest {
  private final String EXISTING_MODEL_STRUCTURE_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private final String EXISTING_MODEL_STRUCTURE_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private final String NON_EXISTING_MODEL_STRUCTURE_TYPE_ID = UUID.randomUUID().toString();

  @Autowired private ModelStructureTypeFacade modelStructureTypeFacade;

  private ModelStructureTypeDTO getModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setName("test_name");
    return modelStructureTypeDTO;
  }

  private ModelStructureTypeDTO getUpdatedModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID_FOR_UPDATE));
    modelStructureTypeDTO.setName("test_updated_modelstructuretype");
    return modelStructureTypeDTO;
  }

  @Test
  void testFindAllModelStructureTypeFacade() {
    Flux<ModelStructureTypeDTO> modelStructureTypes = modelStructureTypeFacade.findAll();
    modelStructureTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            modelStructureTypeDTOS -> {
              assertEquals(
                  4,
                  modelStructureTypeDTOS.size(),
                  "Returned group types do not match expected size");
            });
  }

  @Test
  void testFindByIdModelStructureTypeFacade() {
    Mono<ModelStructureTypeDTO> modelStructureTypeDTOMono =
        modelStructureTypeFacade.findOne(UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID));

    modelStructureTypeDTOMono.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals("modelstructuretype1", modelStructureTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdModelStructureTypeFacade() {
    // Assume you have a known ID for an existing structure type
    UUID existingStructureTypeId = UUID.fromString(EXISTING_MODEL_STRUCTURE_TYPE_ID);

    Mono<Boolean> exists = modelStructureTypeFacade.existsById(existingStructureTypeId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingModelStructureTypeFacade() {
    // Use a non-existing ID

    Mono<Boolean> exists =
        modelStructureTypeFacade.existsById(UUID.fromString(NON_EXISTING_MODEL_STRUCTURE_TYPE_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveModelStructureTypeFacade() {
    Mono<ModelStructureTypeDTO> savedModelStructureTypeDTO =
        modelStructureTypeFacade.save(getModelStructureTypeDTO());
    savedModelStructureTypeDTO.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals(
              getModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
          modelStructureTypeFacade.delete(modelStructureTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateModelStructureTypeFacade() {
    Mono<ModelStructureTypeDTO> updatedModelStructureTypeDTO =
        modelStructureTypeFacade.save(getUpdatedModelStructureTypeDTO());
    updatedModelStructureTypeDTO.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals(
              getUpdatedModelStructureTypeDTO().getName(), modelStructureTypeDTO.getName());
        });
  }

  @Test
  void testDeleteModelStructureTypeFacade() {
    Mono<ModelStructureTypeDTO> savedStructureType =
        modelStructureTypeFacade.save(getModelStructureTypeDTO());

    savedStructureType.subscribe(
        modelStructureTypeDTO -> {
          Mono<Void> deleteResult = modelStructureTypeFacade.delete(modelStructureTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
                Mono<ModelStructureTypeDTO> findResult =
                    modelStructureTypeFacade.findOne(modelStructureTypeDTO.getId());
                findResult.subscribe(
                    notFoundStructureTypeDTO -> Assert.assertNull(notFoundStructureTypeDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingModelStructureTypeFacade() {
    Mono<ModelStructureTypeDTO> structureType =
        modelStructureTypeFacade.findOne(UUID.fromString(NON_EXISTING_MODEL_STRUCTURE_TYPE_ID));

    StepVerifier.create(structureType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testSaveAndUpdateModelStructureTypeFacade() {
    Mono<ModelStructureTypeDTO> savedStructureType =
        modelStructureTypeFacade.save(getModelStructureTypeDTO());

    savedStructureType.subscribe(
        modelStructureTypeDTO -> {
          ModelStructureTypeDTO updatedStructureTypeDTO = getUpdatedModelStructureTypeDTO();
          updatedStructureTypeDTO.setId(modelStructureTypeDTO.getId());
          Mono<ModelStructureTypeDTO> updatedStructureTypeMono =
              modelStructureTypeFacade.save(updatedStructureTypeDTO);

          updatedStructureTypeMono.subscribe(
              updatedModelStructureTypeDTO -> {
                Assert.assertEquals(
                    updatedStructureTypeDTO.getName(), updatedModelStructureTypeDTO.getName());
                modelStructureTypeFacade.delete(updatedModelStructureTypeDTO.getId()).block();
              });
        });
  }
}
