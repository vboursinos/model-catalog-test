package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.entity.Model;
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
  public void testToEntity() {
    ModelDTO modelDTO = new ModelDTO();
    modelDTO.setId(UUID.randomUUID());
    modelDTO.setName("Random Forest");
    modelDTO.setDisplayName("Random Forest Classifier");
    modelDTO.setDescription("A machine learning model based on random forests.");

    Model model = modelMapper.from(modelDTO);

    assertEquals(modelDTO.getId(), model.getId());
    assertEquals(modelDTO.getName(), model.getName());
    assertEquals(modelDTO.getDisplayName(), model.getDisplayName());
    assertEquals(modelDTO.getDescription(), model.getDescription());
  }

  @Test
  public void testToDTO() {
    Model model = new Model();
    model.setId(UUID.randomUUID());
    model.setName("Decision Tree");
    model.setDisplayName("Decision Tree Classifier");
    model.setDescription("A machine learning model based on decision trees.");

    ModelDTO modelDTO = modelMapper.to(model);

    assertEquals(model.getId(), modelDTO.getId());
    assertEquals(model.getName(), modelDTO.getName());
    assertEquals(model.getDisplayName(), modelDTO.getDisplayName());
    assertEquals(model.getDescription(), modelDTO.getDescription());
  }

  @Test
  public void testPartialUpdate() {
    ModelDTO updatedModelDTO = new ModelDTO();
    updatedModelDTO.setId(UUID.randomUUID());
    updatedModelDTO.setName("Updated Random Forest");
    updatedModelDTO.setDisplayName("Updated Random Forest Classifier");
    updatedModelDTO.setDescription("Updated description.");

    Model model = new Model();
    model.setId(updatedModelDTO.getId());
    model.setName("Previous Random Forest");
    model.setDisplayName("Previous Random Forest Classifier");
    model.setDescription("Previous description.");

    modelMapper.partialUpdate(model, updatedModelDTO);

    assertEquals(updatedModelDTO.getName(), model.getName());
    assertEquals(updatedModelDTO.getDisplayName(), model.getDisplayName());
    assertEquals(updatedModelDTO.getDescription(), model.getDescription());
  }
}
