package ai.turintech.modelcatalog.facade;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.*;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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
public class FloatParameterValueFacadeTest extends BasicFacadeTest {
  @Autowired private FloatParameterRangeFacade floatParameterRangeFacade;

  private FloatParameterRangeDTO getFloatParameterRangeDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"));
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);

    FloatParameterRangeDTO floatParameterRangeDTO = new FloatParameterRangeDTO();
    //    floatParameterRange.setFloatParameter(floatParameter);
    floatParameterRangeDTO.setLower(1.0);
    floatParameterRangeDTO.setUpper(10.0);
    floatParameterRangeDTO.setIsLeftOpen(false);
    floatParameterRangeDTO.setIsRightOpen(false);
    return floatParameterRangeDTO;
  }

  private FloatParameterRangeDTO getUpdatedFloatParameterRangeDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174002"));
    floatParameter.setDefaultValue(1.0);
    floatParameter.setParameterTypeDefinition(parameterTypeDefinition);

    FloatParameterRangeDTO floatParameterRangeDTO = new FloatParameterRangeDTO();
    floatParameterRangeDTO.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174003"));
    //    floatParameterRange.setFloatParameter(floatParameter);
    floatParameterRangeDTO.setLower(1.0);
    floatParameterRangeDTO.setUpper(10.0);
    floatParameterRangeDTO.setIsLeftOpen(false);
    floatParameterRangeDTO.setIsRightOpen(false);
    return floatParameterRangeDTO;
  }

  @Test
  void testFindAllFloatParameterRangeFacade() {
    Flux<FloatParameterRangeDTO> floatParameterRangesDTOMono = floatParameterRangeFacade.findAll();
    floatParameterRangesDTOMono
        .collectList()
        .blockOptional()
        .ifPresent(
            floatParameterRangeDTOS -> {
              Assertions.assertEquals(
                  4,
                  floatParameterRangeDTOS.size(),
                  "Returned float parameter ranges do not match expected size");
            });
  }

  @Test
  void testFindByIdFloatParameterRangeFacade() {
    Mono<FloatParameterRangeDTO> floatParameterRangeDTOMono =
        floatParameterRangeFacade.findOne(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    floatParameterRangeDTOMono.subscribe(
        floatParameterRangeDTO -> {
          Assert.assertTrue(floatParameterRangeDTO.getLower() == 25.3);
        });
  }
}
