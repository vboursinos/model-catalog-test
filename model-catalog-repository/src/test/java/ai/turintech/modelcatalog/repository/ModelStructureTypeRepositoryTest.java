package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelStructureType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestRepositoryConfig.class)
public class ModelStructureTypeRepositoryTest {
  @Autowired private ModelStructureTypeRepository modelStructureTypeRepository;

  private static final String MODEL_STRUCTURE_TYPE_ID = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private static final String NEW_MODEL_STRUCTURE_TYPE_ID = "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";

  private ModelStructureType getModelStructureType() {
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setName("test_modelStructureType");
    return modelStructureType;
  }

  private ModelStructureType getUpdatedModelStructureType() {
    ModelStructureType modelStructureType = new ModelStructureType();
    modelStructureType.setId(UUID.fromString(NEW_MODEL_STRUCTURE_TYPE_ID));
    modelStructureType.setName("test_updated_modelStructureType");
    return modelStructureType;
  }

  @Test
  void testFindAllModelStructureTypeRepository() {
    List<ModelStructureType> modelStructureTypes = modelStructureTypeRepository.findAll();
    Assertions.assertEquals(4, modelStructureTypes.size());
  }

  @Test
  void testFindByIdModelStructureTypeRepository() {
    ModelStructureType modelStructureType =
        modelStructureTypeRepository.findById(UUID.fromString(MODEL_STRUCTURE_TYPE_ID)).get();
    Assertions.assertEquals("modelstructuretype3", modelStructureType.getName());
  }

  @Test
  void testSaveModelStructureTypeRepository() {
    ModelStructureType savedModelStructureType =
        modelStructureTypeRepository.save(getModelStructureType());
    Assertions.assertEquals(getModelStructureType().getName(), savedModelStructureType.getName());
    modelStructureTypeRepository.delete(savedModelStructureType);
  }

  @Test
  void testUpdateModelStructureTypeRepository() {
    ModelStructureType updatedModelStructureType =
        modelStructureTypeRepository.save(getUpdatedModelStructureType());
    Assertions.assertEquals(
        getUpdatedModelStructureType().getName(), updatedModelStructureType.getName());
  }
}
