package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MlTaskTypeDTOTest {

  private MlTaskTypeDTO mlTaskTypeDTO;

  @BeforeAll
  public void setUp() {
    mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(UUID.randomUUID());
    mlTaskTypeDTO.setName("TestMlTaskType");
  }

  @Test
  public void testEquals() {
    MlTaskTypeDTO sameIdMlTaskTypeDTO = new MlTaskTypeDTO();
    sameIdMlTaskTypeDTO.setId(mlTaskTypeDTO.getId());

    MlTaskTypeDTO differentIdMlTaskTypeDTO = new MlTaskTypeDTO();
    differentIdMlTaskTypeDTO.setId(UUID.randomUUID());

    assertEquals(mlTaskTypeDTO, sameIdMlTaskTypeDTO);
    assertNotEquals(mlTaskTypeDTO, differentIdMlTaskTypeDTO);
    assertNotEquals(mlTaskTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    MlTaskTypeDTO sameIdMlTaskTypeDTO = new MlTaskTypeDTO();
    sameIdMlTaskTypeDTO.setId(mlTaskTypeDTO.getId());

    MlTaskTypeDTO differentIdMlTaskTypeDTO = new MlTaskTypeDTO();
    differentIdMlTaskTypeDTO.setId(UUID.randomUUID());

    assertEquals(mlTaskTypeDTO.hashCode(), sameIdMlTaskTypeDTO.hashCode());
    assertNotEquals(mlTaskTypeDTO.hashCode(), differentIdMlTaskTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "MlTaskTypeDTO{id='" + mlTaskTypeDTO.getId() + "', name='TestMlTaskType'}";
    assertEquals(expectedToString, mlTaskTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    MlTaskTypeDTO newMlTaskTypeDTO = new MlTaskTypeDTO();
    newMlTaskTypeDTO.setName("NewTestMlTaskType");

    assertEquals("NewTestMlTaskType", newMlTaskTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(mlTaskTypeDTO.equals(differentObject));
  }
}
