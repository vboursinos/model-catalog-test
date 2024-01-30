package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
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
public class FloatParameterRangeMapperTest {

  @Autowired private FloatParameterRangeMapper floatParameterRangeMapper;

  @Test
  public void testToTO() {
    FloatParameterRangeDTO rangeDTO = new FloatParameterRangeDTO();
    rangeDTO.setId(UUID.randomUUID());
    rangeDTO.setIsLeftOpen(true);
    rangeDTO.setIsRightOpen(false);
    rangeDTO.setLower(5.0);
    rangeDTO.setUpper(15.0);

    FloatParameterRangeTO floatParameterRangeTO = floatParameterRangeMapper.to(rangeDTO);

    assertEquals(rangeDTO.getId(), floatParameterRangeTO.getId());
    assertEquals(rangeDTO.getIsLeftOpen(), floatParameterRangeTO.getIsLeftOpen());
    assertEquals(rangeDTO.getIsRightOpen(), floatParameterRangeTO.getIsRightOpen());
    assertEquals(rangeDTO.getLower(), floatParameterRangeTO.getLower());
    assertEquals(rangeDTO.getUpper(), floatParameterRangeTO.getUpper());
  }

  @Test
  public void testToDTO() {
    FloatParameterRangeTO floatParameterRangeTO = new FloatParameterRangeTO();
    floatParameterRangeTO.setId(UUID.randomUUID());
    floatParameterRangeTO.setIsLeftOpen(true);
    floatParameterRangeTO.setIsRightOpen(false);
    floatParameterRangeTO.setLower(5.0);
    floatParameterRangeTO.setUpper(15.0);

    FloatParameterRangeDTO rangeDTO = floatParameterRangeMapper.from(floatParameterRangeTO);

    assertEquals(floatParameterRangeTO.getId(), rangeDTO.getId());
    assertEquals(floatParameterRangeTO.getIsLeftOpen(), rangeDTO.getIsLeftOpen());
    assertEquals(floatParameterRangeTO.getIsRightOpen(), rangeDTO.getIsRightOpen());
    assertEquals(floatParameterRangeTO.getLower(), rangeDTO.getLower());
    assertEquals(floatParameterRangeTO.getUpper(), rangeDTO.getUpper());
  }

  @Test
  public void testPartialUpdate() {
    FloatParameterRangeDTO rangeDTO = new FloatParameterRangeDTO();
    rangeDTO.setId(UUID.randomUUID());
    rangeDTO.setIsLeftOpen(false);
    rangeDTO.setIsRightOpen(true);
    rangeDTO.setLower(10.0);
    rangeDTO.setUpper(20.0);

    FloatParameterRangeTO floatParameterRangeTO = new FloatParameterRangeTO();
    floatParameterRangeTO.setId(rangeDTO.getId());
    floatParameterRangeTO.setIsLeftOpen(true);
    floatParameterRangeTO.setIsRightOpen(false);
    floatParameterRangeTO.setLower(5.0);
    floatParameterRangeTO.setUpper(15.0);

    floatParameterRangeMapper.partialUpdate(rangeDTO, floatParameterRangeTO);

    assertEquals(rangeDTO.getIsLeftOpen(), floatParameterRangeTO.getIsLeftOpen());
    assertEquals(rangeDTO.getIsRightOpen(), floatParameterRangeTO.getIsRightOpen());
    assertEquals(rangeDTO.getLower(), floatParameterRangeTO.getLower());
    assertEquals(rangeDTO.getUpper(), floatParameterRangeTO.getUpper());
  }
}
