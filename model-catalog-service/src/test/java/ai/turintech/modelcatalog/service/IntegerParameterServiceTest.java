package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
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
public class IntegerParameterServiceTest extends BasicServiceTest {
  @Autowired private IntegerParameterService integerParameterService;

  private IntegerParameterDTO getIntegerParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    IntegerParameterDTO integerParameterDTO = new IntegerParameterDTO();
    integerParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    integerParameterDTO.setDefaultValue(1);
    return integerParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllIntegerParameterService() {
    Mono<List<IntegerParameterDTO>> integerParametersMono = integerParameterService.findAll();
    List<IntegerParameterDTO> integerParameterDTOS = integerParametersMono.block();
    Assert.assertNotNull(integerParameterDTOS);
    Assert.assertEquals(2, integerParameterDTOS.size());
  }

  @Test
  @Transactional
  void testFindByIdIntegerParameterService() {
    Mono<IntegerParameterDTO> integerParameterDTOMono =
        integerParameterService.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    integerParameterDTOMono.subscribe(
        integerParameterDTO -> {
          Assert.assertTrue(integerParameterDTO.getDefaultValue() == 10);
        });
  }
}
