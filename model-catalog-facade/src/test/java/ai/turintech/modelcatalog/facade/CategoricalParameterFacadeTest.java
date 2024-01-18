package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
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
public class CategoricalParameterFacadeTest extends BasicFacadeTest {
  @Autowired private CategoricalParameterFacade categoricalParameterFacade;

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
  void testFindAllCategoricalParameterFacade() {
    Flux<CategoricalParameterDTO> categoricalParametersMono = categoricalParameterFacade.findAll();
    categoricalParametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            categoricalParameterDTOS -> {
              assertEquals(
                  2,
                  categoricalParameterDTOS.size(),
                  "Returned categorical parameters do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdCategoricalParameterFacade() {
    Mono<CategoricalParameterDTO> categoricalParameterDTOMono =
        categoricalParameterFacade.findOne(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameterDTOMono.subscribe(
        categoricalParameterDTO -> {
          Assert.assertEquals("value1", categoricalParameterDTO.getDefaultValue());
        });
  }
}
