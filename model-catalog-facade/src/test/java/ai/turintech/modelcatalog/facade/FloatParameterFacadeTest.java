package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class FloatParameterFacadeTest extends BasicFacadeTest {
  @Autowired private FloatParameterFacade floatParameterFacade;

  private FloatParameterDTO getFloatParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    floatParameterDTO.setDefaultValue(1.1);
    return floatParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllFloatParameterFacade() {
    Flux<FloatParameterDTO> floatParametersMono = floatParameterFacade.findAll();
    floatParametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            floatParameterDTOS -> {
              assertEquals(
                  2,
                  floatParameterDTOS.size(),
                  "Returned float parameters do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdFloatParameterFacade() {
    Mono<FloatParameterDTO> floatParameterDTOMono =
        floatParameterFacade.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    floatParameterDTOMono.subscribe(
        floatParameterDTO -> {
          Assert.assertTrue(floatParameterDTO.getDefaultValue() == 10.1);
        });
  }
}
