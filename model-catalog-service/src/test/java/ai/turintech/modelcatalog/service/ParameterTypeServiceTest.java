package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
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
public class ParameterTypeServiceTest extends BasicServiceTest {

  private static final String EXISTING_PARAMETER_TYPE_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String NON_EXISTING_PARAMETER_TYPE_ID =
      "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";
  private static final String EXISTING_PARAMETER_TYPE_UPDATED_ID =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";

  @Autowired private ParameterTypeService parameterTypeService;

  private ParameterTypeDTO getParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("test_name");
    return parameterTypeDTO;
  }

  private ParameterTypeDTO getUpdatedParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString(EXISTING_PARAMETER_TYPE_UPDATED_ID));
    parameterTypeDTO.setName("test_updated_parametertype");
    return parameterTypeDTO;
  }

  @Test
  void testFindAllParameterTypeService() {
    Mono<List<ParameterTypeDTO>> parameterTypes = parameterTypeService.findAll();

    List<ParameterTypeDTO> parameterTypeDTOS = parameterTypes.block();

    Assert.assertNotNull(parameterTypeDTOS);
    Assert.assertEquals(4, parameterTypeDTOS.size());
    Assert.assertEquals("parametertype1", parameterTypeDTOS.get(0).getName());
  }

  @Test
  void testFindByIdParameterTypeService() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_TYPE_ID);
    Mono<ParameterTypeDTO> parameterTypeDTOMono = parameterTypeService.findOne(existingId);

    StepVerifier.create(parameterTypeDTOMono)
        .expectNextMatches(
            parameterTypeDTO -> {
              Assert.assertEquals(existingId, parameterTypeDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testExistsByIdParameterTypeServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_PARAMETER_TYPE_ID);
    Mono<Boolean> exists = parameterTypeService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdParameterTypeServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_PARAMETER_TYPE_ID);
    Mono<Boolean> exists = parameterTypeService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveParameterTypeService() {
    Mono<ParameterTypeDTO> savedParameterTypeDTO = parameterTypeService.save(getParameterTypeDTO());
    savedParameterTypeDTO.subscribe(
        parameterTypeDTO -> {
          Assert.assertEquals(getParameterTypeDTO().getName(), parameterTypeDTO.getName());
          parameterTypeService.delete(parameterTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateParameterTypeService() {
    Mono<ParameterTypeDTO> updatedParameterTypeDTO =
        parameterTypeService.update(getUpdatedParameterTypeDTO());

    StepVerifier.create(updatedParameterTypeDTO)
        .expectNextMatches(
            parameterTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedParameterTypeDTO().getName(), parameterTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateParameterTypeService() {
    Mono<ParameterTypeDTO> updatedParameterTypeDTO =
        parameterTypeService.partialUpdate(getUpdatedParameterTypeDTO());

    StepVerifier.create(updatedParameterTypeDTO)
        .expectNextMatches(
            parameterTypeDTO -> {
              Assert.assertEquals(
                  getUpdatedParameterTypeDTO().getName(), parameterTypeDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
