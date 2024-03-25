package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelGroupType;
import java.util.List;
import java.util.NoSuchElementException;
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
public class ModelGroupTypeRepositoryTest {
  @Autowired private ModelGroupTypeRepository modelGroupTypeRepository;

  private static final String MODEL_GROUP_TYPE_ID = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private static final String NEW_MODEL_GROUP_TYPE_ID = "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";

  private ModelGroupType getModelGroupType() {
    ModelGroupType modelGroupType = new ModelGroupType();
    modelGroupType.setName("test_modelGroupType");
    return modelGroupType;
  }

  private ModelGroupType getUpdatedModelGroupType() {
    ModelGroupType modelGroupType = new ModelGroupType();
    modelGroupType.setId(UUID.fromString(NEW_MODEL_GROUP_TYPE_ID));
    modelGroupType.setName("test_updated_modelGroupType");
    return modelGroupType;
  }

  @Test
  void testFindAllModelGroupTypeRepository() {
    List<ModelGroupType> modelGroupTypes = modelGroupTypeRepository.findAll();
    Assertions.assertEquals(4, modelGroupTypes.size());
  }

  @Test
  void testFindByIdModelGroupTypeRepository() {
    ModelGroupType modelGroupType =
        modelGroupTypeRepository
            .findById(UUID.fromString(MODEL_GROUP_TYPE_ID))
            .orElseThrow(() -> new NoSuchElementException("Model group type not found"));
    Assertions.assertEquals("modelgroup3", modelGroupType.getName());
  }

  @Test
  void testSaveModelGroupTypeRepository() {
    ModelGroupType savedModelGroupType = modelGroupTypeRepository.save(getModelGroupType());
    Assertions.assertEquals(getModelGroupType().getName(), savedModelGroupType.getName());
    modelGroupTypeRepository.delete(savedModelGroupType);
  }

  @Test
  void testUpdateModelGroupTypeRepository() {
    ModelGroupType updatedModelGroupType =
        modelGroupTypeRepository.save(getUpdatedModelGroupType());
    Assertions.assertEquals(getUpdatedModelGroupType().getName(), updatedModelGroupType.getName());
  }
}
