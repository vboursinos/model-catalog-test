package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class CategoricalParameterMapperTest {

  @Autowired private CategoricalParameterMapper categoricalParameterMapper;
  @Autowired private CategoricalParameterValueMapper categoricalParameterValueMapper;

  @Test
  public void testToEntity() {

    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.randomUUID());
    categoricalParameterDTO.setDefaultValue("Default");

    CategoricalParameter result = categoricalParameterMapper.from(categoricalParameterDTO);

    assertEquals(categoricalParameterDTO.getId(), result.getId());
    assertEquals(categoricalParameterDTO.getDefaultValue(), result.getDefaultValue());
  }

  @Test
  public void testToDTO() {
    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(UUID.randomUUID());
    categoricalParameter.setDefaultValue("Default");

    Set<CategoricalParameterValue> values = new HashSet<>();
    CategoricalParameterValue value1 = new CategoricalParameterValue();
    value1.setId(UUID.randomUUID());
    value1.setValue("Value1");
    values.add(value1);
    categoricalParameter.setCategoricalParameterValues(values);

    CategoricalParameterDTO result = categoricalParameterMapper.to(categoricalParameter);

    assertEquals(categoricalParameter.getId(), result.getId());
    assertEquals(categoricalParameter.getDefaultValue(), result.getDefaultValue());
    assertEquals(1, result.getCategoricalParameterValues().size());
  }

  @Test
  public void testPartialUpdate() {
    CategoricalParameterDTO categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(UUID.randomUUID());
    categoricalParameterDTO.setDefaultValue("Default");

    CategoricalParameter categoricalParameter = new CategoricalParameter();
    categoricalParameter.setId(categoricalParameterDTO.getId());
    categoricalParameter.setDefaultValue("OldDefault");

    Set<CategoricalParameterValueDTO> valueDTOs = new HashSet<>();
    CategoricalParameterValueDTO valueDTO1 = new CategoricalParameterValueDTO();
    valueDTO1.setId(UUID.randomUUID());
    valueDTO1.setValue("Value1");
    valueDTOs.add(valueDTO1);
    categoricalParameterDTO.setCategoricalParameterValues(valueDTOs);

    categoricalParameterMapper.partialUpdate(categoricalParameter, categoricalParameterDTO);

    assertEquals(categoricalParameterDTO.getDefaultValue(), categoricalParameter.getDefaultValue());
    assertEquals(1, categoricalParameter.getCategoricalParameterValues().size());
  }
}
