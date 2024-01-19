package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
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
public class ParameterTypeFacadeTest extends BasicFacadeTest {
  @Autowired private ParameterTypeFacade parameterTypeFacade;

  private ParameterTypeDTO getParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setName("test_name");
    return parameterTypeDTO;
  }

  private ParameterTypeDTO getUpdatedParameterTypeDTO() {
    ParameterTypeDTO parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21"));
    parameterTypeDTO.setName("test_updated_parametertype");
    return parameterTypeDTO;
  }

  @Test
  void testFindAllParameterTypeFacade() {
    Flux<ParameterTypeDTO> parameterTypes = parameterTypeFacade.findAll();
    parameterTypes
        .collectList()
        .blockOptional()
        .ifPresent(
            parameterTypeDTOS -> {
              assertEquals(
                  4,
                  parameterTypeDTOS.size(),
                  "Returned parameter types do not match expected size");
            });
  }

  @Test
  void testFindByIdParameterTypeFacade() {
    Mono<ParameterTypeDTO> parameterTypeDTOMono =
        parameterTypeFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27"));

    parameterTypeDTOMono.subscribe(
        parameterTypeDTO -> {
          Assert.assertEquals("parametertype1", parameterTypeDTO.getName());
        });
  }

  @Test
  void testSaveParameterTypeFacade() {
    Mono<ParameterTypeDTO> savedParameterTypeDTO = parameterTypeFacade.save(getParameterTypeDTO());
    savedParameterTypeDTO.subscribe(
        parameterTypeDTO -> {
          Assert.assertEquals(getParameterTypeDTO().getName(), parameterTypeDTO.getName());
          parameterTypeFacade.delete(parameterTypeDTO.getId()).block();
        });
  }

  @Test
  void testUpdateParameterTypeDTOFacade() {
    Mono<ParameterTypeDTO> updatedParameterTypeDTO =
        parameterTypeFacade.save(getUpdatedParameterTypeDTO());
    updatedParameterTypeDTO.subscribe(
        parameterTypeDTO -> {
          Assert.assertEquals(getUpdatedParameterTypeDTO().getName(), parameterTypeDTO.getName());
        });
  }

  @Test
  void testDeleteParameterTypeFacade() {
    // Save a parameter type first
    Mono<ParameterTypeDTO> savedParameterType = parameterTypeFacade.save(getParameterTypeDTO());

    // Subscribe and delete the saved parameter type
    savedParameterType.subscribe(
        parameterTypeDTO -> {
          Mono<Void> deleteResult = parameterTypeFacade.delete(parameterTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result); // deletion should return null
                // Now, try to find the deleted parameter type by ID
                Mono<ParameterTypeDTO> findResult =
                    parameterTypeFacade.findOne(parameterTypeDTO.getId());
                findResult.subscribe(
                    notFoundParameterTypeDTO -> Assert.assertNull(notFoundParameterTypeDTO),
                    throwable -> Assert.assertTrue(throwable instanceof NoSuchElementException));
              });
        });
  }

  @Test
  void testFindByIdNonExistingParameterTypeFacade() {
    // Try to find a parameter type by a non-existing ID
    Mono<ParameterTypeDTO> parameterType = parameterTypeFacade.findOne(UUID.randomUUID());

    StepVerifier.create(parameterType).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testSaveAndUpdateParameterTypeFacade() {
    // Save a parameter type first
    Mono<ParameterTypeDTO> savedParameterType = parameterTypeFacade.save(getParameterTypeDTO());

    savedParameterType.subscribe(
        parameterTypeDTO -> {
          // Update the saved parameter type
          ParameterTypeDTO updatedParameterTypeDTO = getUpdatedParameterTypeDTO();
          updatedParameterTypeDTO.setId(parameterTypeDTO.getId());
          Mono<ParameterTypeDTO> updatedParameterTypeMono =
              parameterTypeFacade.save(updatedParameterTypeDTO);

          updatedParameterTypeMono.subscribe(
              updatedParameterType -> {
                Assert.assertEquals(
                    updatedParameterType.getName(), updatedParameterTypeDTO.getName());
                // Clean up: Delete the updated parameter type
                parameterTypeFacade.delete(updatedParameterType.getId()).block();
              });
        });
  }
}
