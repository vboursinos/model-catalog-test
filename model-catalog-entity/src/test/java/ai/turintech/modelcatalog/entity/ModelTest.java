package ai.turintech.modelcatalog.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

class ModelTest {

  @Test
  public void testName() {
    Model model = new Model();
    model.setName("Test Model");
    assertEquals("Test Model", model.getName());
  }

  @Test
  public void testDisplayName() {
    Model model = new Model();
    model.setDisplayName("Display Test Model");
    assertEquals("Display Test Model", model.getDisplayName());
  }

  @Test
  public void testDescription() {
    Model model = new Model();
    model.setDescription("This is a test model.");
    assertEquals("This is a test model.", model.getDescription());
  }

  @Test
  public void testAdvantages() {
    Model model = new Model();
    String[] advantages = {"advantage1", "advantage2"};
    model.setAdvantages(advantages);
    assertArrayEquals(advantages, model.getAdvantages());
  }

  @Test
  public void testDisadvantages() {
    Model model = new Model();
    String[] disadvantages = {"disadvantage1", "disadvantage2"};
    model.setDisadvantages(disadvantages);
    assertArrayEquals(disadvantages, model.getDisadvantages());
  }

  @Test
  public void testEnabled() {
    Model model = new Model();
    model.setEnabled(true);
    assertTrue(model.getEnabled());
  }

  @Test
  public void testDecisionTree() {
    Model model = new Model();
    model.setDecisionTree(true);
    assertTrue(model.getDecisionTree());
  }

  @Test
  public void testHashCode() {
    Model model1 = new Model();
    model1.setName("Test Model");
    Model model2 = new Model();
    model2.setName("Test Model");
    assertEquals(model1.hashCode(), model2.hashCode());
  }

  @Test
  public void testToString() {
    Model model = new Model();
    model.setName("Test Model");
    assertTrue(model.toString().contains("Test Model"));
  }
}
