package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTypeDTOTest {

  private ModelTypeDTO modelTypeDTO;

  @BeforeEach
  public void setUp() {
    modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(UUID.randomUUID());
    modelTypeDTO.setName("TestModelType");
  }

  @Test
  public void testEquals() {
    ModelTypeDTO sameIdModelTypeDTO = new ModelTypeDTO();
    sameIdModelTypeDTO.setId(modelTypeDTO.getId());

    ModelTypeDTO differentIdModelTypeDTO = new ModelTypeDTO();
    differentIdModelTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelTypeDTO, sameIdModelTypeDTO);
    assertNotEquals(modelTypeDTO, differentIdModelTypeDTO);
    assertNotEquals(modelTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ModelTypeDTO sameIdModelTypeDTO = new ModelTypeDTO();
    sameIdModelTypeDTO.setId(modelTypeDTO.getId());

    ModelTypeDTO differentIdModelTypeDTO = new ModelTypeDTO();
    differentIdModelTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelTypeDTO.hashCode(), sameIdModelTypeDTO.hashCode());
    assertNotEquals(modelTypeDTO.hashCode(), differentIdModelTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelTypeDTO{id='" + modelTypeDTO.getId() + "', name='TestModelType'}";
    assertEquals(expectedToString, modelTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    ModelTypeDTO newModelTypeDTO = new ModelTypeDTO();
    newModelTypeDTO.setName("NewTestModelType");

    assertEquals("NewTestModelType", newModelTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelTypeDTO.equals(differentObject));
  }
}
