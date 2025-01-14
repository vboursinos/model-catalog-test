package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelFamilyTypeDTOTest {

  private ModelFamilyTypeDTO modelFamilyTypeDTO;

  @BeforeAll
  public void setUp() {
    modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(UUID.randomUUID());
    modelFamilyTypeDTO.setName("TestFamilyType");
  }

  @Test
  public void testEquals() {
    ModelFamilyTypeDTO sameIdModelFamilyTypeDTO = new ModelFamilyTypeDTO();
    sameIdModelFamilyTypeDTO.setId(modelFamilyTypeDTO.getId());

    ModelFamilyTypeDTO differentIdModelFamilyTypeDTO = new ModelFamilyTypeDTO();
    differentIdModelFamilyTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelFamilyTypeDTO, sameIdModelFamilyTypeDTO);
    assertNotEquals(modelFamilyTypeDTO, differentIdModelFamilyTypeDTO);
    assertNotEquals(modelFamilyTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ModelFamilyTypeDTO sameIdModelFamilyTypeDTO = new ModelFamilyTypeDTO();
    sameIdModelFamilyTypeDTO.setId(modelFamilyTypeDTO.getId());

    ModelFamilyTypeDTO differentIdModelFamilyTypeDTO = new ModelFamilyTypeDTO();
    differentIdModelFamilyTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelFamilyTypeDTO.hashCode(), sameIdModelFamilyTypeDTO.hashCode());
    assertNotEquals(modelFamilyTypeDTO.hashCode(), differentIdModelFamilyTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelFamilyTypeDTO{id='" + modelFamilyTypeDTO.getId() + "', name='TestFamilyType'}";
    assertEquals(expectedToString, modelFamilyTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    ModelFamilyTypeDTO newModelFamilyTypeDTO = new ModelFamilyTypeDTO();
    newModelFamilyTypeDTO.setName("NewTestFamilyType");

    assertEquals("NewTestFamilyType", newModelFamilyTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelFamilyTypeDTO.equals(differentObject));
  }
}
