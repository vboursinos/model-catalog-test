package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class FloatParameterMapperTest {

  @Autowired private FloatParameterMapper floatParameterMapper;

  @Test
  public void testToEntity() {
    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.randomUUID());
    floatParameterDTO.setDefaultValue(10.0);

    List<FloatParameterRangeDTO> rangeDTOs = new ArrayList<>();
    FloatParameterRangeDTO rangeDTO1 = new FloatParameterRangeDTO();
    rangeDTO1.setId(UUID.randomUUID());
    rangeDTO1.setLower(5.0);
    rangeDTO1.setUpper(15.0);
    rangeDTOs.add(rangeDTO1);
    floatParameterDTO.setFloatParameterRanges(rangeDTOs);

    FloatParameter floatParameter = floatParameterMapper.from(floatParameterDTO);

    assertEquals(floatParameterDTO.getId(), floatParameter.getId());
    assertEquals(floatParameterDTO.getDefaultValue(), floatParameter.getDefaultValue());
    assertEquals(1, floatParameter.getFloatParameterRanges().size());
  }

  @Test
  public void testToDTO() {
    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(UUID.randomUUID());
    floatParameter.setDefaultValue(10.0);

    Set<FloatParameterRange> ranges = new HashSet<>();
    FloatParameterRange range1 = new FloatParameterRange();
    range1.setId(UUID.randomUUID());
    range1.setLower(5.0);
    range1.setUpper(15.0);
    ranges.add(range1);
    floatParameter.setFloatParameterRanges(ranges);

    FloatParameterDTO floatParameterDTO = floatParameterMapper.to(floatParameter);

    assertEquals(floatParameter.getId(), floatParameterDTO.getId());
    assertEquals(floatParameter.getDefaultValue(), floatParameterDTO.getDefaultValue());
    assertEquals(1, floatParameterDTO.getFloatParameterRanges().size());
  }

  @Test
  public void testPartialUpdate() {
    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.randomUUID());
    floatParameterDTO.setDefaultValue(20.0);

    FloatParameter floatParameter = new FloatParameter();
    floatParameter.setId(floatParameterDTO.getId());
    floatParameter.setDefaultValue(10.0);

    List<FloatParameterRangeDTO> rangeDTOs = new ArrayList<>();
    FloatParameterRangeDTO rangeDTO1 = new FloatParameterRangeDTO();
    rangeDTO1.setId(UUID.randomUUID());
    rangeDTO1.setLower(5.0);
    rangeDTO1.setUpper(15.0);
    rangeDTOs.add(rangeDTO1);
    floatParameterDTO.setFloatParameterRanges(rangeDTOs);

    floatParameterMapper.partialUpdate(floatParameter, floatParameterDTO);

    assertEquals(floatParameterDTO.getDefaultValue(), floatParameter.getDefaultValue());
    assertEquals(1, floatParameter.getFloatParameterRanges().size());
  }
}
