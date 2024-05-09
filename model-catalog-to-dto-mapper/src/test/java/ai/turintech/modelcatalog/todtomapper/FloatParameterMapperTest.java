package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.to.FloatParameterRangeTO;
import ai.turintech.modelcatalog.to.FloatParameterTO;
import java.util.*;
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
public class FloatParameterMapperTest {

  @Autowired private FloatParameterMapper floatParameterMapper;

  @Test
  public void testToTO() {
    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.randomUUID());
    floatParameterDTO.setDefaultValue(10.0);

    Set<FloatParameterRangeDTO> rangeDTOs = new HashSet<>();
    FloatParameterRangeDTO rangeDTO1 = new FloatParameterRangeDTO();
    rangeDTO1.setId(UUID.randomUUID());
    rangeDTO1.setLower(5.0);
    rangeDTO1.setUpper(15.0);
    rangeDTOs.add(rangeDTO1);
    floatParameterDTO.setFloatParameterRanges(rangeDTOs);

    FloatParameterTO floatParameterTO = floatParameterMapper.to(floatParameterDTO);

    assertEquals(floatParameterDTO.getId(), floatParameterTO.getId());
    assertEquals(floatParameterDTO.getDefaultValue(), floatParameterTO.getDefaultValue());
    assertEquals(1, floatParameterTO.getFloatParameterRanges().size());
  }

  @Test
  public void testToDTO() {
    FloatParameterTO floatParameterTO = new FloatParameterTO();
    floatParameterTO.setId(UUID.randomUUID());
    floatParameterTO.setDefaultValue(10.0);

    List<FloatParameterRangeTO> ranges = new ArrayList<>();
    FloatParameterRangeTO range1 = new FloatParameterRangeTO();
    range1.setId(UUID.randomUUID());
    range1.setLower(5.0);
    range1.setUpper(15.0);
    ranges.add(range1);
    floatParameterTO.setFloatParameterRanges(ranges);

    FloatParameterDTO floatParameterDTO = floatParameterMapper.from(floatParameterTO);

    assertEquals(floatParameterTO.getId(), floatParameterDTO.getId());
    assertEquals(floatParameterTO.getDefaultValue(), floatParameterDTO.getDefaultValue());
    assertEquals(1, floatParameterDTO.getFloatParameterRanges().size());
  }

  @Test
  public void testPartialUpdate() {
    FloatParameterDTO floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(UUID.randomUUID());
    floatParameterDTO.setDefaultValue(20.0);

    FloatParameterTO floatParameterTO = new FloatParameterTO();
    floatParameterTO.setId(floatParameterDTO.getId());
    floatParameterTO.setDefaultValue(10.0);

    Set<FloatParameterRangeDTO> rangeDTOs = new HashSet<>();
    FloatParameterRangeDTO rangeDTO1 = new FloatParameterRangeDTO();
    rangeDTO1.setId(UUID.randomUUID());
    rangeDTO1.setLower(5.0);
    rangeDTO1.setUpper(15.0);
    rangeDTOs.add(rangeDTO1);
    floatParameterDTO.setFloatParameterRanges(rangeDTOs);

    floatParameterMapper.partialUpdate(floatParameterDTO, floatParameterTO);

    assertEquals(floatParameterDTO.getDefaultValue(), floatParameterTO.getDefaultValue());
    assertEquals(
        floatParameterDTO.getFloatParameterRanges().size(),
        floatParameterTO.getFloatParameterRanges().size());
  }
}
