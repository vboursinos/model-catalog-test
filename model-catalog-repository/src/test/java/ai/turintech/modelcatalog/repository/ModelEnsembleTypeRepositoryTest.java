package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelEnsembleType;
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
public class ModelEnsembleTypeRepositoryTest extends BasicRepositoryTest {
  @Autowired private ModelEnsembleTypeRepository modelEnsembleTypeRepository;

  private final String modelEnsembleTypeId = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private final String newModelEnsembleTypeId = "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";

  private ModelEnsembleType getModelEnsembleType() {
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setName("test_modelEnsembleType");
    return modelEnsembleType;
  }

  private ModelEnsembleType getUpdatedModelEnsembleType() {
    ModelEnsembleType modelEnsembleType = new ModelEnsembleType();
    modelEnsembleType.setId(UUID.fromString(newModelEnsembleTypeId));
    modelEnsembleType.setName("test_updated_modelEnsembleType");
    return modelEnsembleType;
  }

  @Test
  void testFindAllModelEnsembleTypeRepository() {
    List<ModelEnsembleType> modelEnsembleTypes = modelEnsembleTypeRepository.findAll();
    Assertions.assertEquals(4, modelEnsembleTypes.size());
  }

  @Test
  void testFindByIdModelEnsembleTypeRepository() {
    ModelEnsembleType modelEnsembleType =
        modelEnsembleTypeRepository.findById(UUID.fromString(modelEnsembleTypeId)).get();
    Assertions.assertEquals("modelensembletype3", modelEnsembleType.getName());
  }

  @Test
  void testSaveModelEnsembleTypeRepository() {
    ModelEnsembleType savedModelEnsembleType =
        modelEnsembleTypeRepository.save(getModelEnsembleType());
    Assertions.assertEquals(getModelEnsembleType().getName(), savedModelEnsembleType.getName());
    modelEnsembleTypeRepository.delete(savedModelEnsembleType);
  }

  @Test
  void testUpdateModelEnsembleTypeRepository() {
    ModelEnsembleType updatedModelEnsembleType =
        modelEnsembleTypeRepository.save(getUpdatedModelEnsembleType());
    Assertions.assertEquals(
        getUpdatedModelEnsembleType().getName(), updatedModelEnsembleType.getName());
  }
}
