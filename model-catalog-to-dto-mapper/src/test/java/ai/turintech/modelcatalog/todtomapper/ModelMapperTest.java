package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.to.ModelTO;
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
public class ModelMapperTest {

  @Autowired private ModelMapper modelMapper;

  @Test
  public void testToTO() {
    ModelDTO modelDTO = new ModelDTO();
    modelDTO.setId(UUID.randomUUID());
    modelDTO.setName("Random Forest");
    modelDTO.setDisplayName("Random Forest Classifier");
    modelDTO.setDescription("A machine learning model based on random forests.");

    ModelTO model = modelMapper.to(modelDTO);

    assertEquals(modelDTO.getId(), model.getId());
    assertEquals(modelDTO.getName(), model.getName());
    assertEquals(modelDTO.getDisplayName(), model.getDisplayName());
    assertEquals(modelDTO.getDescription(), model.getDescription());
  }

  @Test
  public void testToDTO() {
    ModelTO modelTO = new ModelTO();
    modelTO.setId(UUID.randomUUID());
    modelTO.setName("Decision Tree");
    modelTO.setDisplayName("Decision Tree Classifier");
    modelTO.setDescription("A machine learning model based on decision trees.");

    ModelDTO modelDTO = modelMapper.to(modelTO);

    assertEquals(modelTO.getId(), modelDTO.getId());
    assertEquals(modelTO.getName(), modelDTO.getName());
    assertEquals(modelTO.getDisplayName(), modelDTO.getDisplayName());
    assertEquals(modelTO.getDescription(), modelDTO.getDescription());
  }

  @Test
  public void testPartialUpdate() {
    ModelDTO updatedModelDTO = new ModelDTO();
    updatedModelDTO.setId(UUID.randomUUID());
    updatedModelDTO.setName("Updated Random Forest");
    updatedModelDTO.setDisplayName("Updated Random Forest Classifier");
    updatedModelDTO.setDescription("Updated description.");

    ModelTO modelTO = new ModelTO();
    modelTO.setId(updatedModelDTO.getId());
    modelTO.setName("Previous Random Forest");
    modelTO.setDisplayName("Previous Random Forest Classifier");
    modelTO.setDescription("Previous description.");

    modelMapper.partialUpdate(updatedModelDTO, modelTO);

    assertEquals(updatedModelDTO.getName(), modelTO.getName());
    assertEquals(updatedModelDTO.getDisplayName(), modelTO.getDisplayName());
    assertEquals(updatedModelDTO.getDescription(), modelTO.getDescription());
  }
}
