package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.to.CategoricalParameterTO;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class CategoricalParameterMapperTest {

  @Autowired private CategoricalParameterMapper categoricalParameterMapper;
  @Autowired private CategoricalParameterValueMapper categoricalParameterValueMapper;

  @Test
  public void testToTO() {

    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.randomUUID());
    categoricalParameterDTO.setDefaultValue("Default");

    CategoricalParameterTO result = categoricalParameterMapper.to(categoricalParameterDTO);

    assertEquals(categoricalParameterDTO.getId(), result.getId());
    assertEquals(categoricalParameterDTO.getDefaultValue(), result.getDefaultValue());
  }

  @Test
  public void testToDTO() {
    CategoricalParameterTO categoricalParameterTO = new CategoricalParameterTO();
    categoricalParameterTO.setId(UUID.randomUUID());
    categoricalParameterTO.setDefaultValue("Default");

    Set<CategoricalParameterValueTO> values = new HashSet<>();
    CategoricalParameterValueTO value1 = new CategoricalParameterValueTO();
    value1.setId(UUID.randomUUID());
    value1.setValue("Value1");
    values.add(value1);
    categoricalParameterTO.setCategoricalParameterValues(values);

    CategoricalParameterDTO result = categoricalParameterMapper.from(categoricalParameterTO);

    assertEquals(categoricalParameterTO.getId(), result.getId());
    assertEquals(categoricalParameterTO.getDefaultValue(), result.getDefaultValue());
    assertEquals(1, result.getCategoricalParameterValues().size());
  }

  @Test
  public void testPartialUpdate() {
    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.randomUUID());
    categoricalParameterDTO.setDefaultValue("Default");

    CategoricalParameterTO categoricalParameterTO = new CategoricalParameterTO();
    categoricalParameterTO.setId(categoricalParameterDTO.getId());
    categoricalParameterTO.setDefaultValue("OldDefault");

    Set<CategoricalParameterValueDTO> valueDTOs = new HashSet<>();
    CategoricalParameterValueDTO valueDTO1 = new CategoricalParameterValueDTO();
    valueDTO1.setId(UUID.randomUUID());
    valueDTO1.setValue("Value1");
    valueDTOs.add(valueDTO1);
    categoricalParameterDTO.setCategoricalParameterValues(valueDTOs);

    categoricalParameterMapper.partialUpdate(categoricalParameterDTO, categoricalParameterTO);

    assertEquals(
        categoricalParameterDTO.getDefaultValue(), categoricalParameterTO.getDefaultValue());
    assertEquals(
        categoricalParameterDTO.getCategoricalParameterValues().size(),
        categoricalParameterTO.getCategoricalParameterValues().size());
  }
}
