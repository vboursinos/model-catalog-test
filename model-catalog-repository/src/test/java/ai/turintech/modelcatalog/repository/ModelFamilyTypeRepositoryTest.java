package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelFamilyType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ModelFamilyTypeRepositoryTest extends BasicRepositoryTest {
  @Autowired private ModelFamilyTypeRepository modelFamilyTypeRepository;

  private static final String MODEL_FAMILY_TYPE_ID = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private static final String NEW_MODEL_FAMILY_TYPE_ID = "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";

  private ModelFamilyType getModelFamilyType() {
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setName("test_modelFamilyType");
    return modelFamilyType;
  }

  private ModelFamilyType getUpdatedModelFamilyType() {
    ModelFamilyType modelFamilyType = new ModelFamilyType();
    modelFamilyType.setId(UUID.fromString(NEW_MODEL_FAMILY_TYPE_ID));
    modelFamilyType.setName("test_updated_modelFamilyType");
    return modelFamilyType;
  }

  @Test
  void testFindAllModelFamilyTypeRepository() {
    List<ModelFamilyType> modelFamilyTypes = modelFamilyTypeRepository.findAll();
    Assertions.assertEquals(4, modelFamilyTypes.size());
  }

  @Test
  void testFindByIdModelFamilyTypeRepository() {
    ModelFamilyType modelFamilyType =
        modelFamilyTypeRepository.findById(UUID.fromString(MODEL_FAMILY_TYPE_ID)).get();
    Assertions.assertEquals("modelfamilytype3", modelFamilyType.getName());
  }

  @Test
  void testSaveModelFamilyTypeRepository() {
    ModelFamilyType savedModelFamilyType = modelFamilyTypeRepository.save(getModelFamilyType());
    Assertions.assertEquals(getModelFamilyType().getName(), savedModelFamilyType.getName());
    modelFamilyTypeRepository.delete(savedModelFamilyType);
  }

  @Test
  void testUpdateModelFamilyTypeRepository() {
    ModelFamilyType updatedModelFamilyType =
        modelFamilyTypeRepository.save(getUpdatedModelFamilyType());
    Assertions.assertEquals(
        getUpdatedModelFamilyType().getName(), updatedModelFamilyType.getName());
  }
}
