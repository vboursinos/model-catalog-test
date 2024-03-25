package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class CategoricalParameterValueMapperTest {

  @Autowired private CategoricalParameterValueMapper categoricalParameterValueMapper;

  @Test
  public void testToEntity() {
    CategoricalParameterValueDTO dto = new CategoricalParameterValueDTO();
    dto.setId(UUID.randomUUID());
    dto.setValue("TestValue");

    CategoricalParameterValue entity = categoricalParameterValueMapper.from(dto);

    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getValue(), entity.getValue());
  }

  @Test
  public void testToDTO() {
    CategoricalParameterValue entity = new CategoricalParameterValue();
    entity.setId(UUID.randomUUID());
    entity.setValue("TestValue");

    CategoricalParameterValueDTO dto = categoricalParameterValueMapper.to(entity);

    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getValue(), dto.getValue());
  }

  @Test
  public void testPartialUpdate() {
    CategoricalParameterValueDTO dto = new CategoricalParameterValueDTO();
    dto.setValue("UpdatedValue");

    CategoricalParameterValue entity = new CategoricalParameterValue();
    entity.setId(UUID.randomUUID());
    entity.setValue("OriginalValue");

    categoricalParameterValueMapper.partialUpdate(entity, dto);

    assertEquals("UpdatedValue", entity.getValue());
  }
}
