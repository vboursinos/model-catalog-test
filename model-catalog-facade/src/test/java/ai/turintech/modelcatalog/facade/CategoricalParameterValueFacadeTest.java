package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.*;
import java.util.UUID;
import org.junit.Assert;
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
public class CategoricalParameterValueFacadeTest extends BasicFacadeTest {
  @Autowired private CategoricalParameterValueFacade categoricalParameterValueFacade;

  private CategoricalParameterValueDTO getCategoricalParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    //    categoricalParameterValueDTO.SE(categoricalParameter);
    categoricalParameterValueDTO.setValue("test_value");
    return categoricalParameterValueDTO;
  }

  private CategoricalParameterValueDTO getUpdatedCategoricalParameterValueDTO() {
    ParameterTypeDefinition parameterTypeDefinition = new ParameterTypeDefinition();
    parameterTypeDefinition.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinition.setOrdering(10);

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    categoricalParameter.setDefaultValue("test_defaultValue");
    categoricalParameter.setParameterTypeDefinition(parameterTypeDefinition);

    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
    //    categoricalParameterValueDTO.setCategoricalParameter(categoricalParameter);
    categoricalParameterValueDTO.setValue("test_value");
    return categoricalParameterValueDTO;
  }

  @Test
  void testFindAllCategoricalParameterValueFacade() {
    Flux<CategoricalParameterValueDTO> categoricalParameterValues =
        categoricalParameterValueFacade.findAll();
    categoricalParameterValues
        .collectList()
        .blockOptional()
        .ifPresent(
            categoricalParameterValueDTOS -> {
              assertEquals(
                  4,
                  categoricalParameterValueDTOS.size(),
                  "Returned categorical parameter values do not match expected size");
            });
  }

  @Test
  void testFindByIdCategoricalParameterValueFacade() {
    Mono<CategoricalParameterValueDTO> categoricalParameterValueDTOMono =
        categoricalParameterValueFacade.findOne(
            UUID.fromString("423e4567-e89b-12d3-a456-426614174003"));

    categoricalParameterValueDTOMono.subscribe(
        categoricalParameterValueDTO -> {
          Assert.assertTrue(categoricalParameterValueDTO.getValue().equals("Category3"));
        });
  }
}
