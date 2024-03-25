package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ParameterDistributionTypeFacadeTest extends BasicFacadeTest {
  private final String EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID =
      "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d27";

  private final String EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d21";
  private final String NON_EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID = UUID.randomUUID().toString();

  @Autowired private ParameterDistributionTypeFacade parameterDistributionTypeFacade;

  private ParameterDistributionTypeDTO getParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setName("test_name");
    return parameterDistributionTypeDTO;
  }

  private ParameterDistributionTypeDTO getUpdatedParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(
        UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID_FOR_UPDATE));
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
            UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID));

    parameterDistributionTypeDTOMono.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals("parameterdistributiontype1", parameterDistributionTypeDTO.getName());
        });
  }

  @Test
  void testExistsByIdParameterDistributionTypeFacade() {
    UUID existingParameterDistributionTypeId =
        UUID.fromString(EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID);

    Mono<Boolean> exists =
        parameterDistributionTypeFacade.existsById(existingParameterDistributionTypeId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingParameterDistributionTypeFacade() {

    Mono<Boolean> exists =
        parameterDistributionTypeFacade.existsById(
            UUID.fromString(NON_EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID));
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
        parameterDistributionTypeFacade.update(getUpdatedParameterDistributionTypeDTO());
    updatedParameterDistributionTypeDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getUpdatedParameterDistributionTypeDTO().getName(),
              parameterDistributionTypeDTO.getName());
        });
  }

  @Test
  void testPartialUpdateParameterDistributionTypeDTOFacade() {
    Mono<ParameterDistributionTypeDTO> updatedParameterDistributionTypeDTO =
        parameterDistributionTypeFacade.partialUpdate(getUpdatedParameterDistributionTypeDTO());
    updatedParameterDistributionTypeDTO.subscribe(
        parameterDistributionTypeDTO -> {
          Assert.assertEquals(
              getUpdatedParameterDistributionTypeDTO().getName(),
              parameterDistributionTypeDTO.getName());
        });
  }

  @Test
  void testDeleteParameterDistributionTypeFacade() {
    Mono<ParameterDistributionTypeDTO> savedParameterDistributionType =
        parameterDistributionTypeFacade.save(getParameterDistributionTypeDTO());

    savedParameterDistributionType.subscribe(
        parameterDistributionTypeDTO -> {
          Mono<Void> deleteResult =
              parameterDistributionTypeFacade.delete(parameterDistributionTypeDTO.getId());
          deleteResult.subscribe(
              result -> {
                Assert.assertNull(result);
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
        parameterDistributionTypeFacade.findOne(
            UUID.fromString(NON_EXISTING_PARAMETER_DISTRIBUTION_TYPE_ID));

    StepVerifier.create(parameterDistributionType)
        .expectError(NoSuchElementException.class)
        .verify();
  }

  @Test
  void testSaveAndUpdateParameterDistributionTypeFacade() {
    Mono<ParameterDistributionTypeDTO> savedParameterDistributionType =
        parameterDistributionTypeFacade.save(getParameterDistributionTypeDTO());

    savedParameterDistributionType.subscribe(
        parameterDistributionTypeDTO -> {
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
                parameterDistributionTypeFacade
                    .delete(updatedParameterDistributionType.getId())
                    .block();
              });
        });
  }
}
