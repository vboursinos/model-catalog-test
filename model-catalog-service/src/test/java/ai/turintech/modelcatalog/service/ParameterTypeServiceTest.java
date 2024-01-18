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

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterTypeServiceTest extends BasicServiceTest {
  @Autowired private ParameterTypeService parameterTypeService;

  private ParameterTypeDTO getParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("test_name");
    return parameterTypeDTO;
  }

  private ParameterTypeDTO getUpdatedParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
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
    Mono<ParameterTypeDTO> parameterTypeDTOMono =
        parameterTypeService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    parameterTypeDTOMono.subscribe(
        parameterTypeDTO -> {
          Assert.assertEquals("parametertype1", parameterTypeDTO.getName());
        });
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
  void testUpdateParameterTypeDTOService() {
    Mono<ParameterTypeDTO> updatedParameterTypeDTO =
        parameterTypeService.save(getUpdatedParameterTypeDTO());
    updatedParameterTypeDTO.subscribe(
        parameterTypeDTO -> {
          Assert.assertEquals(getUpdatedParameterTypeDTO().getName(), parameterTypeDTO.getName());
        });
  }
}
