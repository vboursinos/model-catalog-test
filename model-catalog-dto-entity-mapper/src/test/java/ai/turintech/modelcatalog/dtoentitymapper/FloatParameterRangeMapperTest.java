package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class FloatParameterRangeMapperTest {

  @Autowired private FloatParameterRangeMapper floatParameterRangeMapper;

  @Test
  public void testToEntity() {
    FloatParameterRangeDTO rangeDTO = new FloatParameterRangeDTO();
    rangeDTO.setId(UUID.randomUUID());
    rangeDTO.setIsLeftOpen(true);
    rangeDTO.setIsRightOpen(false);
    rangeDTO.setLower(5.0);
    rangeDTO.setUpper(15.0);

    FloatParameterRange floatParameterRange = floatParameterRangeMapper.from(rangeDTO);

    assertEquals(rangeDTO.getId(), floatParameterRange.getId());
    assertEquals(rangeDTO.getIsLeftOpen(), floatParameterRange.getIsLeftOpen());
    assertEquals(rangeDTO.getIsRightOpen(), floatParameterRange.getIsRightOpen());
    assertEquals(rangeDTO.getLower(), floatParameterRange.getLower());
    assertEquals(rangeDTO.getUpper(), floatParameterRange.getUpper());
  }

  @Test
  public void testToDTO() {
    FloatParameterRange floatParameterRange = new FloatParameterRange();
    floatParameterRange.setId(UUID.randomUUID());
    floatParameterRange.setIsLeftOpen(true);
    floatParameterRange.setIsRightOpen(false);
    floatParameterRange.setLower(5.0);
    floatParameterRange.setUpper(15.0);

    FloatParameterRangeDTO rangeDTO = floatParameterRangeMapper.to(floatParameterRange);

    assertEquals(floatParameterRange.getId(), rangeDTO.getId());
    assertEquals(floatParameterRange.getIsLeftOpen(), rangeDTO.getIsLeftOpen());
    assertEquals(floatParameterRange.getIsRightOpen(), rangeDTO.getIsRightOpen());
    assertEquals(floatParameterRange.getLower(), rangeDTO.getLower());
    assertEquals(floatParameterRange.getUpper(), rangeDTO.getUpper());
  }

  @Test
  public void testPartialUpdate() {
    FloatParameterRangeDTO rangeDTO = new FloatParameterRangeDTO();
    rangeDTO.setId(UUID.randomUUID());
    rangeDTO.setIsLeftOpen(false);
    rangeDTO.setIsRightOpen(true);
    rangeDTO.setLower(10.0);
    rangeDTO.setUpper(20.0);

    FloatParameterRange floatParameterRange = new FloatParameterRange();
    floatParameterRange.setId(rangeDTO.getId());
    floatParameterRange.setIsLeftOpen(true);
    floatParameterRange.setIsRightOpen(false);
    floatParameterRange.setLower(5.0);
    floatParameterRange.setUpper(15.0);

    floatParameterRangeMapper.partialUpdate(floatParameterRange, rangeDTO);

    assertEquals(rangeDTO.getIsLeftOpen(), floatParameterRange.getIsLeftOpen());
    assertEquals(rangeDTO.getIsRightOpen(), floatParameterRange.getIsRightOpen());
    assertEquals(rangeDTO.getLower(), floatParameterRange.getLower());
    assertEquals(rangeDTO.getUpper(), floatParameterRange.getUpper());
  }
}
