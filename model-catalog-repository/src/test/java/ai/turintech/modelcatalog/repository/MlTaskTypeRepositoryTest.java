package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.MlTaskType;
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
public class MlTaskTypeRepositoryTest extends BasicRepositoryTest {
  @Autowired private MlTaskTypeRepository mlTaskTypeRepository;

  private final String mlTaskTypeId = "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";
  private final String newMlTaskTypeId = "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26";

  private MlTaskType getMlTaskType() {
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setName("test_mlTaskType");
    return mlTaskType;
  }

  private MlTaskType getUpdatedMlTaskType() {
    MlTaskType mlTaskType = new MlTaskType();
    mlTaskType.setId(UUID.fromString(newMlTaskTypeId));
    mlTaskType.setName("test_updated_mlTaskType");
    return mlTaskType;
  }

  @Test
  void testFindAllMlTaskRepository() {
    List<MlTaskType> mlTaskTypes = mlTaskTypeRepository.findAll();
    Assertions.assertEquals(4, mlTaskTypes.size());
  }

  @Test
  void testFindByIdMlTaskRepository() {
    MlTaskType mlTaskType = mlTaskTypeRepository.findById(UUID.fromString(mlTaskTypeId)).get();
    Assertions.assertEquals("mltask2", mlTaskType.getName());
  }

  @Test
  void testSaveMlTaskRepository() {
    MlTaskType savedMlTask = mlTaskTypeRepository.save(getMlTaskType());
    Assertions.assertEquals(getMlTaskType().getName(), savedMlTask.getName());
    mlTaskTypeRepository.delete(savedMlTask);
  }

  @Test
  void testUpdateMlTaskRepository() {
    MlTaskType updatedMlTask = mlTaskTypeRepository.save(getUpdatedMlTaskType());
    Assertions.assertEquals(getUpdatedMlTaskType().getName(), updatedMlTask.getName());
  }
}
