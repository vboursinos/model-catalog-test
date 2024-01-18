package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
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
public class MlTaskTypeFacadeTest extends BasicFacadeTest {
  @Autowired private MlTaskTypeFacade mlTaskTypeFacade;

  private MlTaskTypeDTO getMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setName("test_name");
    return mlTaskTypeDTO;
  }

  private MlTaskTypeDTO getUpdatedMlTaskTypeDTO() {
    MlTaskTypeDTO mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    mlTaskTypeDTO.setName("test_updated_mltasktype");
    return mlTaskTypeDTO;
  }

  @Test
  void testFindAllMlTaskTypeFacade() {
    Flux<MlTaskTypeDTO> mlTaskTypes = mlTaskTypeFacade.findAll();

    mlTaskTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            mlTaskTypeDTOS -> {
              assertEquals(
                  4, mlTaskTypeDTOS.size(), "Returned mltask types do not match expected size");
            });
  }

  @Test
  void testFindByIdMlTaksFacade() {
    Mono<MlTaskTypeDTO> mlTaskTypeDTOMono =
        mlTaskTypeFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    mlTaskTypeDTOMono.subscribe(
        mlTaskTypeDTO -> {
          Assert.assertEquals("mltask1", mlTaskTypeDTO.getName());
        });
  }

  @Test
  void testSaveMlTaskTypeFacade() {
    Mono<MlTaskTypeDTO> savedMlTaskTypeDTO = mlTaskTypeFacade.save(getMlTaskTypeDTO());
    savedMlTaskTypeDTO.subscribe(
        mlTaskTypeDTO -> {
          Assert.assertEquals(getMlTaskTypeDTO().getName(), mlTaskTypeDTO.getName());
          mlTaskTypeFacade.delete(mlTaskTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateMlTaskTypeFacade() {
    Mono<MlTaskTypeDTO> updatedMlTaskTypeDTO = mlTaskTypeFacade.save(getUpdatedMlTaskTypeDTO());
    updatedMlTaskTypeDTO.subscribe(
        mlTaskTypeDTO -> {
          Assert.assertEquals(getUpdatedMlTaskTypeDTO().getName(), mlTaskTypeDTO.getName());
        });
  }
}
