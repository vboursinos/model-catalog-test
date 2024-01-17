package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelType;
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
public class ModelTypeRepositoryTest extends BasicRepositoryTest {
  @Autowired private ModelTypeRepository modelTypeRepository;

  private ModelType getModelType() {
    ModelType modelType = new ModelType();
    modelType.setName("test_modelType");
    return modelType;
  }

  private ModelType getUpdatedModelType() {
    ModelType modelType = new ModelType();
    modelType.setId(UUID.fromString("2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28"));
    modelType.setName("test_updated_modelType");
    return modelType;
  }

  @Test
  void testFindAllModelTypeRepository() {
    List<ModelType> modelTypes = modelTypeRepository.findAll();
    Assertions.assertEquals(4, modelTypes.size());
  }

  @Test
  void testFindByIdModelTypeRepository() {
    ModelType modelType =
        modelTypeRepository.findById(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29")).get();
    Assertions.assertEquals("modeltype3", modelType.getName());
  }

  @Test
  void testSaveModelTypeRepository() {
    ModelType savedModelType = modelTypeRepository.save(getModelType());
    Assertions.assertEquals(getModelType().getName(), savedModelType.getName());
    modelTypeRepository.delete(savedModelType);
  }

  @Test
  void testUpdateModelTypeRepository() {
    ModelType updatedModelType = modelTypeRepository.save(getUpdatedModelType());
    Assertions.assertEquals(getUpdatedModelType().getName(), updatedModelType.getName());
  }
}
