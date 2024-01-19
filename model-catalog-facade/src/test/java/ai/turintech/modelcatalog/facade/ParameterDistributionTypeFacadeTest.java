package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterDistributionTypeFacadeTest extends BasicFacadeTest {
  @Autowired private ParameterDistributionTypeFacade parameterDistributionTypeFacade;

  private ParameterDistributionTypeDTO getParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("test_name");
    return parameterDistributionTypeDTO;
  }

  private ParameterDistributionTypeDTO getUpdatedParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    parameterDistributionTypeDTO.setName("test_updated_parametertype");
    return parameterDistributionTypeDTO;
  }

  @Test
  void testFindAllParameterDistributionTypeFacade() {
    Flux<ParameterDistributionTypeDTO> parameterDistributionTypes =
        parameterDistributionTypeFacade.findAll();
    parameterDistributionTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            parameterDistributionTypeDTOS -> {
              assertEquals(
                  4,
                  parameterDistributionTypeDTOS.size(),
                  "Returned parameter distribution type do not match expected size");
            });
  }

  @Test
  void testFindByIdParameterDistributionTypeFacade() {
    Mono<ParameterDistributionTypeDTO> parameterDistributionTypeDTOMono =
        parameterDistributionTypeFacade.findOne(
            UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    parameterDistributionTypeDTOMono.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals("parameterdistributiontype1", parameterDistributionTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdParameterDistributionTypeFacade() {
    // Assume you have a known ID for an existing distribution type
    UUID existingParameterDistributionTypeId =
        UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27");

    Mono<Boolean> exists =
        parameterDistributionTypeFacade.existsById(existingParameterDistributionTypeId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingParameterDistributionTypeFacade() {
    // Use a non-existing ID
    UUID nonExistingParameterDistributionTypeId = UUID.randomUUID();

    Mono<Boolean> exists =
        parameterDistributionTypeFacade.existsById(nonExistingParameterDistributionTypeId);
    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveParameterDistributionTypeFacade() {
    Mono<ParameterDistributionTypeDTO> savedParameterDistributionTypeDTO =
        parameterDistributionTypeFacade.save(getParameterDistributionTypeDTO());
    savedParameterDistributionTypeDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getParameterDistributionTypeDTO().getName(), parameterDistributionTypeDTO.getName());
          parameterDistributionTypeFacade.delete(parameterDistributionTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateParameterDistributionTypeDTOFacade() {
    Mono<ParameterDistributionTypeDTO> updatedParameterDistributionTypeDTO =
        parameterDistributionTypeFacade.save(getUpdatedParameterDistributionTypeDTO());
    updatedParameterDistributionTypeDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getUpdatedParameterDistributionTypeDTO().getName(),
              parameterDistributionTypeDTO.getName());
        });
  }

  @Test
  void testDeleteParameterDistributionTypeFacade() {
    // Save a parameter distribution type first
    Mono<ParameterDistributionTypeDTO> savedParameterDistributionType =
        parameterDistributionTypeFacade.save(getParameterDistributionTypeDTO());

    // Subscribe and delete the saved parameter distribution type
    savedParameterDistributionType.subscribe(
        parameterDistributionTypeDTO -> {
          Mono<Void> deleteResult =
              parameterDistributionTypeFacade.delete(parameterDistributionTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted parameter distribution type by ID
                Mono<ParameterDistributionTypeDTO> findResult =
                    parameterDistributionTypeFacade.findOne(parameterDistributionTypeDTO.getId());
                findResult.subscribe(
                    notFoundParameterDistributionTypeDTO ->
                        Assert.assertNull(notFoundParameterDistributionTypeDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingParameterDistributionTypeFacade() {
    // Try to find a parameter distribution type by a non-existing ID
    Mono<ParameterDistributionTypeDTO> parameterDistributionType =
        parameterDistributionTypeFacade.findOne(UUID.randomUUID());

    StepVerifier.create(parameterDistributionType)
        .expectError(NoSuchElementException.class)
        .verify();
  }

  @Test
  void testSaveAndUpdateParameterDistributionTypeFacade() {
    // Save a parameter distribution type first
    Mono<ParameterDistributionTypeDTO> savedParameterDistributionType =
        parameterDistributionTypeFacade.save(getParameterDistributionTypeDTO());

    savedParameterDistributionType.subscribe(
        parameterDistributionTypeDTO -> {
          // Update the saved parameter distribution type
          ParameterDistributionTypeDTO updatedParameterDistributionTypeDTO =
              getUpdatedParameterDistributionTypeDTO();
          updatedParameterDistributionTypeDTO.setId(parameterDistributionTypeDTO.getId());
          Mono<ParameterDistributionTypeDTO> updatedParameterDistributionTypeMono =
              parameterDistributionTypeFacade.save(updatedParameterDistributionTypeDTO);

          updatedParameterDistributionTypeMono.subscribe(
              updatedParameterDistributionType -> {
                Assert.assertEquals(
                    updatedParameterDistributionType.getName(),
                    updatedParameterDistributionTypeDTO.getName());
                // Clean up: Delete the updated parameter distribution type
                parameterDistributionTypeFacade
                    .delete(updatedParameterDistributionType.getId())
                    .block();
              });
        });
  }
}
