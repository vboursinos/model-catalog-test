package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
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
public class ParameterTypeDefinitionFacadeTest extends BasicFacadeTest {
  @Autowired private ParameterTypeDefinitionFacade parameterTypeDefinitionFacade;

  private ParameterTypeDefinitionDTO getParameterTypeDefinitionDTO() {

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterDistributionTypeDTO.setName("parameterdistributiontype1");

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterTypeDTO.setName("parametertype1");

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setOrdering(10);

    return parameterTypeDefinitionDTO;
  }

  private ParameterTypeDefinitionDTO getUpdatedParameterTypeDefinitionDTO() {

    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterDistributionTypeDTO.setName("parameterdistributiontype1");

    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));
    parameterTypeDTO.setName("parametertype1");

    ParameterTypeDefinitionDTO parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
    parameterTypeDefinitionDTO.setDistribution(parameterDistributionTypeDTO);
    parameterTypeDefinitionDTO.setType(parameterTypeDTO);
    parameterTypeDefinitionDTO.setOrdering(12);

    return parameterTypeDefinitionDTO;
  }

  @Test
  @Transactional
  void testFindAllParameterTypeDefinitionFacade() {
    Flux<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOsMono =
        parameterTypeDefinitionFacade.findAll();

    parameterTypeDefinitionDTOsMono
        .collectList()
        .blockOptional()
        .ifPresent(
            parameterTypeDefinitionDTOS -> {
              assertEquals(
                  3,
                  parameterTypeDefinitionDTOS.size(),
                  "Returned parameter type definitions do not match expected size");
            });
  }

  @Test
  @Transactional
  void testFindByIdParameterTypeDefinitionFacade() {
    Mono<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOMono =
        parameterTypeDefinitionFacade.findOne(
            UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));

    parameterTypeDefinitionDTOMono.subscribe(
        parameterTypeDefinitionDTO -> {
          Assert.assertTrue(1 == parameterTypeDefinitionDTO.getOrdering());
        });
  }

  @Test
  @Transactional
  void testSaveParameterTypeDefinitionFacade() {
    Mono<ParameterTypeDefinitionDTO> savedParameterTypeDefinitionDTO =
        parameterTypeDefinitionFacade.save(getParameterTypeDefinitionDTO());
    savedParameterTypeDefinitionDTO.subscribe(
        parameterTypeDefinitionDTO -> {
          Assert.assertEquals(
              getParameterTypeDefinitionDTO().getOrdering(),
              parameterTypeDefinitionDTO.getOrdering());
          parameterTypeDefinitionFacade.delete(parameterTypeDefinitionDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testUpdateParameterTypeDefinitionFacade() {
    Mono<ParameterTypeDefinitionDTO> updateParameterTypeDefinitionDTO =
        parameterTypeDefinitionFacade.save(getUpdatedParameterTypeDefinitionDTO());
    updateParameterTypeDefinitionDTO.subscribe(
        parameterTypeDefinitionDTO -> {
          Assert.assertEquals(
              getUpdatedParameterTypeDefinitionDTO().getOrdering(),
              parameterTypeDefinitionDTO.getOrdering());
        });
  }

  @Test
  @Transactional
  void testDeleteParameterTypeDefinitionFacade() {
    // Save a parameter type definition first
    Mono<ParameterTypeDefinitionDTO> savedParameterTypeDefinition =
        parameterTypeDefinitionFacade.save(getParameterTypeDefinitionDTO());

    // Subscribe and delete the saved parameter type definition
    savedParameterTypeDefinition.subscribe(
        parameterTypeDefinitionDTO -> {
          Mono<Void> deleteResult =
              parameterTypeDefinitionFacade.delete(parameterTypeDefinitionDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted parameter type definition by ID
                Mono<ParameterTypeDefinitionDTO> findResult =
                    parameterTypeDefinitionFacade.findOne(parameterTypeDefinitionDTO.getId());
                findResult.subscribe(
                    notFoundParameterTypeDefinitionDTO ->
                        Assert.assertNull(notFoundParameterTypeDefinitionDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  @Transactional
  void testFindByIdNonExistingParameterTypeDefinitionFacade() {
    // Try to find a parameter type definition by a non-existing ID
    Mono<ParameterTypeDefinitionDTO> parameterTypeDefinition =
        parameterTypeDefinitionFacade.findOne(UUID.randomUUID());

    StepVerifier.create(parameterTypeDefinition).expectError(NoSuchElementException.class).verify();
  }

  @Test
  @Transactional
  void testSaveAndUpdateParameterTypeDefinitionFacade() {
    // Save a parameter type definition first
    Mono<ParameterTypeDefinitionDTO> savedParameterTypeDefinition =
        parameterTypeDefinitionFacade.save(getParameterTypeDefinitionDTO());

    savedParameterTypeDefinition.subscribe(
        parameterTypeDefinitionDTO -> {
          // Update the saved parameter type definition
          ParameterTypeDefinitionDTO updatedParameterTypeDefinitionDTO =
              getUpdatedParameterTypeDefinitionDTO();
          updatedParameterTypeDefinitionDTO.setId(parameterTypeDefinitionDTO.getId());
          Mono<ParameterTypeDefinitionDTO> updatedParameterTypeDefinitionMono =
              parameterTypeDefinitionFacade.save(updatedParameterTypeDefinitionDTO);

          updatedParameterTypeDefinitionMono.subscribe(
              updatedParameterTypeDefinition -> {
                Assert.assertEquals(
                    updatedParameterTypeDefinition.getOrdering(),
                    updatedParameterTypeDefinitionDTO.getOrdering());
                // Clean up: Delete the updated parameter type definition
                parameterTypeDefinitionFacade
                    .delete(updatedParameterTypeDefinition.getId())
                    .block();
              });
        });
  }
}
