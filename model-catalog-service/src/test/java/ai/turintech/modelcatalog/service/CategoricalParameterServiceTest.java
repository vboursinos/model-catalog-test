package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
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
public class CategoricalParameterServiceTest extends BasicServiceTest {
  @Autowired private CategoricalParameterService categoricalParameterService;

  private CategoricalParameterDTO getCategoricalParameterDTO() {

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    parameterTypeDefinitionDTO.setOrdering(10);

    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174003"));
    categoricalParameterDTO.setDefaultValue("test_default_value");
    return categoricalParameterDTO;
  }

  @Test
  @Transactional
  void testFindAllCategoricalParameterService() {
    Mono<List<CategoricalParameterDTO>> categoricalParametersMono =
        categoricalParameterService.findAll();
    List<CategoricalParameterDTO> categoricalParameterDTOS = categoricalParametersMono.block();
    Assert.assertNotNull(categoricalParameterDTOS);
    Assert.assertEquals(2, categoricalParameterDTOS.size());
  }

  @Test
  @Transactional
  void testFindByIdCategoricalParameterService() {
    Mono<CategoricalParameterDTO> categoricalParameterDTOMono =
        categoricalParameterService.findOne(
            UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameterDTOMono.subscribe(
        categoricalParameterDTO -> {
          Assert.assertEquals("value1", categoricalParameterDTO.getDefaultValue());
        });
  }
}
