package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelGroupTypeDTOTest {

  private ModelGroupTypeDTO modelGroupTypeDTO;

  @BeforeEach
  public void setUp() {
    modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(UUID.randomUUID());
    modelGroupTypeDTO.setName("TestGroupType");
  }

  @Test
  public void testEquals() {
    ModelGroupTypeDTO sameIdModelGroupTypeDTO = new ModelGroupTypeDTO();
    sameIdModelGroupTypeDTO.setId(modelGroupTypeDTO.getId());

    ModelGroupTypeDTO differentIdModelGroupTypeDTO = new ModelGroupTypeDTO();
    differentIdModelGroupTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelGroupTypeDTO, sameIdModelGroupTypeDTO);
    assertNotEquals(modelGroupTypeDTO, differentIdModelGroupTypeDTO);
    assertNotEquals(modelGroupTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ModelGroupTypeDTO sameIdModelGroupTypeDTO = new ModelGroupTypeDTO();
    sameIdModelGroupTypeDTO.setId(modelGroupTypeDTO.getId());

    ModelGroupTypeDTO differentIdModelGroupTypeDTO = new ModelGroupTypeDTO();
    differentIdModelGroupTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelGroupTypeDTO.hashCode(), sameIdModelGroupTypeDTO.hashCode());
    assertNotEquals(modelGroupTypeDTO.hashCode(), differentIdModelGroupTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelGroupTypeDTO{id='" + modelGroupTypeDTO.getId() + "', name='TestGroupType'}";
    assertEquals(expectedToString, modelGroupTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    ModelGroupTypeDTO newModelGroupTypeDTO = new ModelGroupTypeDTO();
    newModelGroupTypeDTO.setName("NewTestGroupType");

    assertEquals("NewTestGroupType", newModelGroupTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelGroupTypeDTO.equals(differentObject));
  }
}
