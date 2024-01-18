package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
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
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class BooleanParameterServiceTest extends BasicServiceTest {
  @Autowired private BooleanParameterService booleanParameterService;

  private BooleanParameterDTO getBooleanParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    BooleanParameterDTO booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    booleanParameterDTO.setDefaultValue(false);
    return booleanParameterDTO;
  }

  @Test
  void testFindAllBooleanParameterService() {
    Mono<List<BooleanParameterDTO>> booleanParametersMono = booleanParameterService.findAll();
    List<BooleanParameterDTO> booleanParameters = booleanParametersMono.block();
    Assert.assertNotNull(booleanParameters);
    Assert.assertEquals(2, booleanParameters.size());
  }

  @Test
  void testFindByIdBooleanParameterService() {
    Mono<BooleanParameterDTO> booleanParameterDTOMono =
        booleanParameterService.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    booleanParameterDTOMono.subscribe(
        booleanParameterDTO -> {
          Assert.assertEquals(true, booleanParameterDTO.getDefaultValue());
        });
  }
}
