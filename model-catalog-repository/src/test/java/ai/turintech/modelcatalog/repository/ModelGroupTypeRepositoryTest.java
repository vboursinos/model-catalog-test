package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelGroupType;
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
public class ModelGroupTypeRepositoryTest extends BasicRepositoryTest {
  @Autowired private ModelGroupTypeRepository modelGroupTypeRepository;

  private ModelGroupType getModelGroupType() {
    ModelGroupType modelGroupType = new ModelGroupType();
    modelGroupType.setName("test_modelGroupType");
    return modelGroupType;
  }

  private ModelGroupType getUpdatedModelGroupType() {
    ModelGroupType modelGroupType = new ModelGroupType();
    modelGroupType.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28"));
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
            .findById(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29"))
            .get();
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
