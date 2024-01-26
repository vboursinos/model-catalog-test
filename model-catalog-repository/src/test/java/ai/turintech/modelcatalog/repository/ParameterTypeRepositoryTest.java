package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterType;
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
public class ParameterTypeRepositoryTest extends BasicRepositoryTest {
  @Autowired private ParameterTypeRepository parameterTypeRepository;

  private final String parameterTypeId = "3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d29";
  private final String newParameterTypeId = "2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d28";

  private ParameterType getParameterType() {
    ParameterType parameterType = new ParameterType();
    parameterType.setName("test_parameterType");
    return parameterType;
  }

  private ParameterType getUpdatedParameterType() {
    ParameterType parameterType = new ParameterType();
    parameterType.setId(UUID.fromString(newParameterTypeId));
    parameterType.setName("test_updated_parameterType");
    return parameterType;
  }

  @Test
  void testFindAllParameterTypeRepository() {
    List<ParameterType> parameterTypes = parameterTypeRepository.findAll();
    Assertions.assertEquals(4, parameterTypes.size());
  }

  @Test
  void testFindByIdParameterTypeRepository() {
    ParameterType parameterType =
        parameterTypeRepository.findById(UUID.fromString(parameterTypeId)).get();
    Assertions.assertEquals("parametertype3", parameterType.getName());
  }

  @Test
  void testSaveParameterTypeRepository() {
    ParameterType savedParameterType = parameterTypeRepository.save(getParameterType());
    Assertions.assertEquals(getParameterType().getName(), savedParameterType.getName());
    parameterTypeRepository.delete(savedParameterType);
  }

  @Test
  void testUpdateParameterTypeRepository() {
    ParameterType updatedParameterType = parameterTypeRepository.save(getUpdatedParameterType());
    Assertions.assertEquals(getUpdatedParameterType().getName(), updatedParameterType.getName());
  }
}
