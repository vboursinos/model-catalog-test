package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.to.CategoricalParameterValueTO;
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
public class CategoricalParameterValueMapperTest {

  @Autowired private CategoricalParameterValueMapper categoricalParameterValueMapper;

  @Test
  public void testToTO() {
    CategoricalParameterValueDTO dto = new CategoricalParameterValueDTO();
    dto.setId(UUID.randomUUID());
    dto.setValue("TestValue");

    CategoricalParameterValueTO entity = categoricalParameterValueMapper.to(dto);

    assertEquals(dto.getId(), entity.getId());
    assertEquals(dto.getValue(), entity.getValue());
  }

  @Test
  public void testToDTO() {
    CategoricalParameterValueTO categoricalParameterValueTO = new CategoricalParameterValueTO();
    categoricalParameterValueTO.setId(UUID.randomUUID());
    categoricalParameterValueTO.setValue("TestValue");

    CategoricalParameterValueDTO dto =
        categoricalParameterValueMapper.from(categoricalParameterValueTO);

    assertEquals(categoricalParameterValueTO.getId(), dto.getId());
    assertEquals(categoricalParameterValueTO.getValue(), dto.getValue());
  }

  @Test
  public void testPartialUpdate() {
    CategoricalParameterValueDTO categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO.setValue("UpdatedValue");

    CategoricalParameterValueTO categoricalParameterValueTO = new CategoricalParameterValueTO();
    categoricalParameterValueTO.setId(UUID.randomUUID());
    categoricalParameterValueTO.setValue("OriginalValue");

    categoricalParameterValueMapper.partialUpdate(
        categoricalParameterValueDTO, categoricalParameterValueTO);

    assertEquals(categoricalParameterValueDTO.getValue(), categoricalParameterValueTO.getValue());
  }
}
