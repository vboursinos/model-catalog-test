package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.entity.*;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class IntegerParameterValueServiceTest extends BasicServiceTest {
  @Autowired private IntegerParameterValueService integerParameterValueService;

  private IntegerParameterValueDTO getIntegerParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    //    integerParameterValueDTO.setIntegerParameter(integerParameter);
    integerParameterValueDTO.setLower(1);
    integerParameterValueDTO.setUpper(10);
    return integerParameterValueDTO;
  }

  private IntegerParameterValueDTO getUpdatedIntegerParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    IntegerParameter integerParameter = new IntegerParameter();
    integerParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameter.setDefaultValue(1);
    integerParameter.setParameterTypeDefinition(parameterTypeDefinition);

    IntegerParameterValueDTO integerParameterValueDTO = new IntegerParameterValueDTO();
    integerParameterValueDTO.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    //    integerParameterValueDTO.setIntegerParameter(integerParameter);
    integerParameterValueDTO.setLower(1);
    integerParameterValueDTO.setUpper(10);
    return integerParameterValueDTO;
  }

  @Test
  void testFindAllIntegerParameterValueService() {
    Mono<List<IntegerParameterValueDTO>> integerParameterValues =
        integerParameterValueService.findAll();
    List<IntegerParameterValueDTO> integerParameterValueDTOS = integerParameterValues.block();
    Assertions.assertNotNull(integerParameterValueDTOS);
    Assertions.assertEquals(4, integerParameterValueDTOS.size());
  }

  @Test
  void testFindByIdIntegerParameterValueRepository() {
    Mono<IntegerParameterValueDTO> integerParameterValueMonoDTO =
        integerParameterValueService.findOne(
            UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    integerParameterValueMonoDTO.subscribe(
        integerParameterValueDTO -> {
          Assert.assertTrue(integerParameterValueDTO.getLower() == 25);
        });
  }
}
