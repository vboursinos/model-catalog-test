package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
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

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ModelStructureTypeFacadeTest extends BasicFacadeTest {
  @Autowired private ModelStructureTypeFacade modelStructureTypeFacade;

  private ModelStructureTypeDTO getModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setName("test_name");
    return modelStructureTypeDTO;
  }

  private ModelStructureTypeDTO getUpdatedModelStructureTypeDTO() {
    ModelStructureTypeDTO modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
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
        modelStructureTypeFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    modelStructureTypeDTOMono.subscribe(
        modelStructureTypeDTO -> {
          Assert.assertEquals("modelstructuretype1", modelStructureTypeDTO.getName());
        });
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
}
