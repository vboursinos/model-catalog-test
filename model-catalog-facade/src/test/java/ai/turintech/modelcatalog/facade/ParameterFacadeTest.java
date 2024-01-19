package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterFacadeTest extends BasicFacadeTest {
  @Autowired private ParameterFacade parameterFacade;

  private ParameterDTO getParameterDTO() {

    ParameterDTO parameter = new ParameterDTO();
    parameter.setName("test_parameter");
    parameter.setDescription("test_description");
    parameter.setEnabled(true);
    parameter.setFixedValue(true);
    parameter.setLabel("test_label");
    parameter.setOrdering(1);

    return parameter;
  }

  private ParameterDTO getUpdatedParameterDTO() {
    ParameterDTO parameterDTO = new ParameterDTO();
    parameterDTO.setName("update_parameter");
    parameterDTO.setDescription("test_description");
    parameterDTO.setEnabled(true);
    parameterDTO.setFixedValue(true);
    parameterDTO.setLabel("test_label");
    parameterDTO.setOrdering(1);

    return parameterDTO;
  }

  @Test
  @Transactional
  void testFindAllParameterFacade() {
    Flux<ParameterDTO> parametersMono = parameterFacade.findAll();
    parametersMono
        .collectList()
        .blockOptional()
        .ifPresent(
            parameterDTOS -> {
              assertEquals(
                  1, parameterDTOS.size(), "Returned parameters do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdParameterFacade() {
    Mono<ParameterDTO> parameterDTOMono =
        parameterFacade.findOne(UUID.fromString("523e4567-e89b-12d3-a456-426614174001"));

    parameterDTOMono.subscribe(
        parameterDTO -> {
          Assert.assertEquals("parameter_name", parameterDTO.getName());
        });
  }

  @Test
  @Transactional
  void testExistsByIdParameterFacade() {
    // Assume you have a known ID for an existing parameter
    UUID existingParameterId = UUID.fromString("523e4567-e89b-12d3-a456-426614174001");

    Mono<Boolean> exists = parameterFacade.existsById(existingParameterId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdNonExistingParameterFacade() {
    // Use a non-existing ID
    UUID nonExistingParameterId = UUID.randomUUID();

    Mono<Boolean> exists = parameterFacade.existsById(nonExistingParameterId);
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  @Transactional
  void testSaveParameterFacade() {
    Mono<ParameterDTO> savedParameter = parameterFacade.save(getParameterDTO());
    savedParameter.subscribe(
        parameterDTO -> {
          Assert.assertEquals(getParameterDTO().getName(), parameterDTO.getName());
          parameterFacade.delete(parameterDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testUpdateParameterFacade() {
    Mono<ParameterDTO> updateParameterDTO = parameterFacade.save(getUpdatedParameterDTO());
    updateParameterDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getUpdatedParameterDTO().getName(), parameterDistributionTypeDTO.getName());
        });
  }

  @Test
  @Transactional
  void testDeleteParameterFacade() {
    // Save a parameter first
    Mono<ParameterDTO> savedParameter = parameterFacade.save(getParameterDTO());

    // Subscribe and delete the saved parameter
    savedParameter.subscribe(
        parameterDTO -> {
          Mono<Void> deleteResult = parameterFacade.delete(parameterDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted parameter by ID
                Mono<ParameterDTO> findResult = parameterFacade.findOne(parameterDTO.getId());
                findResult.subscribe(
                    notFoundParameterDTO -> Assert.assertNull(notFoundParameterDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingParameterFacade() {
    // Try to find a parameter by a non-existing ID
    Mono<ParameterDTO> parameter = parameterFacade.findOne(UUID.randomUUID());

    StepVerifier.create(parameter).expectError(NoSuchElementException.class).verify();
  }

  @Test
  @Transactional
  void testSaveAndUpdateParameterFacade() {
    // Save a parameter first
    Mono<ParameterDTO> savedParameter = parameterFacade.save(getParameterDTO());

    savedParameter.subscribe(
        parameterDTO -> {
          // Update the saved parameter
          ParameterDTO updatedParameterDTO = getUpdatedParameterDTO();
          updatedParameterDTO.setId(parameterDTO.getId());
          Mono<ParameterDTO> updatedParameterMono = parameterFacade.save(updatedParameterDTO);

          updatedParameterMono.subscribe(
              updatedParameter -> {
                Assert.assertEquals(updatedParameter.getName(), updatedParameterDTO.getName());
                // Clean up: Delete the updated parameter
                parameterFacade.delete(updatedParameter.getId()).block();
              });
        });
  }
}
