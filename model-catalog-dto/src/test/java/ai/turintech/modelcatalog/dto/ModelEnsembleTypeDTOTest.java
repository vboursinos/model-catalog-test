package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelEnsembleTypeDTOTest {

  private ModelEnsembleTypeDTO modelEnsembleTypeDTO;

  @BeforeEach
  public void setUp() {
    modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(UUID.randomUUID());
    modelEnsembleTypeDTO.setName("TestEnsembleType");
  }

  @Test
  public void testEquals() {
    ModelEnsembleTypeDTO sameIdModelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    sameIdModelEnsembleTypeDTO.setId(modelEnsembleTypeDTO.getId());

    ModelEnsembleTypeDTO differentIdModelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    differentIdModelEnsembleTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelEnsembleTypeDTO, sameIdModelEnsembleTypeDTO);
    assertNotEquals(modelEnsembleTypeDTO, differentIdModelEnsembleTypeDTO);
    assertNotEquals(modelEnsembleTypeDTO, null);
  }

  @Test
  public void testHashCode() {
    ModelEnsembleTypeDTO sameIdModelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    sameIdModelEnsembleTypeDTO.setId(modelEnsembleTypeDTO.getId());

    ModelEnsembleTypeDTO differentIdModelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    differentIdModelEnsembleTypeDTO.setId(UUID.randomUUID());

    assertEquals(modelEnsembleTypeDTO.hashCode(), sameIdModelEnsembleTypeDTO.hashCode());
    assertNotEquals(modelEnsembleTypeDTO.hashCode(), differentIdModelEnsembleTypeDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ModelEnsembleTypeDTO{id='" + modelEnsembleTypeDTO.getId() + "', name='TestEnsembleType'}";
    assertEquals(expectedToString, modelEnsembleTypeDTO.toString());
  }

  @Test
  public void testGetSetName() {
    ModelEnsembleTypeDTO newModelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    newModelEnsembleTypeDTO.setName("NewTestEnsembleType");

    assertEquals("NewTestEnsembleType", newModelEnsembleTypeDTO.getName());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(modelEnsembleTypeDTO.equals(differentObject));
  }
}
