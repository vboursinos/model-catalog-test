package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.entity.*;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterServiceTest extends BasicServiceTest {
  @Autowired private ParameterService parameterService;

  private ParameterDTO getParameterDTO() {

    ParameterDTO parameter = new ParameterDTO();
    parameter.setName("test_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);

    return parameter;
  }

  private ParameterDTO getUpdatedParameterDTO() {
    ParameterDTO parameterDTO = new ParameterDTO();
    parameterDTO.setName("update_parameter");
    parameterDTO.setDescription("test_description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setLabel("test_label");
    parameterDTO.setOrdering(1);

    return parameterDTO;
  }

  @Test
  @Transactional
  void testFindAllParameterService() {
    Mono<List<ParameterDTO>> parametersMono = parameterService.findAll();
    List<ParameterDTO> parameterDTOs = parametersMono.block();

    Assert.assertNotNull(parameterDTOs);
    Assert.assertEquals(1, parameterDTOs.size());
    Assert.assertEquals("parameter_name", parameterDTOs.get(0).getName());
  }

  @Test
  @Transactional
  void testFindByIdParameterService() {
    Mono<ParameterDTO> parameterDTOMono =
        parameterService.findOne(UUID.fromString("523e4567-e89b-12d3-a456-426614174001"));

    parameterDTOMono.subscribe(
        parameterDTO -> {
          Assert.assertEquals("parameter_name", parameterDTO.getName());
        });
  }

  @Test
  @Transactional
  void testSaveParameterService() {
    Mono<ParameterDTO> savedParameter = parameterService.save(getParameterDTO());
    savedParameter.subscribe(
        parameterDTO -> {
          Assert.assertEquals(getParameterDTO().getName(), parameterDTO.getName());
          parameterService.delete(parameterDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testUpdateParameterServce() {
    Mono<ParameterDTO> updateParameterDTO = parameterService.save(getUpdatedParameterDTO());
    updateParameterDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getUpdatedParameterDTO().getName(), parameterDistributionTypeDTO.getName());
        });
  }
}
