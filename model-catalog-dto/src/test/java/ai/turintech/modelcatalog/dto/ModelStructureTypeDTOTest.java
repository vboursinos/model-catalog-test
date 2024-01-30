package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelStructureTypeDTOTest {

  private ModelStructureTypeDTO modelStructureTypeDTO;

  @BeforeEach
  public void setUp() {
    modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(UUID.randomUUID());
    modelStructureTypeDTO.setName("TestStructureType");
  }

  @Test
  public void testEquals() {
    ModelStructureTypeDTO sameIdModelStructureTypeDTO = new ModelStructureTypeDTO();
    sameIdModelStructureTypeDTO.setId(modelStructureTypeDTO.getId());

    ModelStructureTypeDTO differentIdModelStructureTypeDTO = new ModelStructureTypeDTO();
    differentIdModelStructureTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelStructureTypeDTO, sameIdModelStructureTypeDTO);
    assertNotEquals(modelStructureTypeDTO, differentIdModelStructureTypeDTO);
    assertNotEquals(modelStructureTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ModelStructureTypeDTO sameIdModelStructureTypeDTO = new ModelStructureTypeDTO();
    sameIdModelStructureTypeDTO.setId(modelStructureTypeDTO.getId());

    ModelStructureTypeDTO differentIdModelStructureTypeDTO = new ModelStructureTypeDTO();
    differentIdModelStructureTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelStructureTypeDTO.hashCode(), sameIdModelStructureTypeDTO.hashCode());
    assertNotEquals(modelStructureTypeDTO.hashCode(), differentIdModelStructureTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelStructureTypeDTO{id='"
            + modelStructureTypeDTO.getId()
            + "', name='TestStructureType'}";
    assertEquals(expectedToString, modelStructureTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    ModelStructureTypeDTO newModelStructureTypeDTO = new ModelStructureTypeDTO();
    newModelStructureTypeDTO.setName("NewTestStructureType");

    assertEquals("NewTestStructureType", newModelStructureTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelStructureTypeDTO.equals(differentObject));
  }
}
