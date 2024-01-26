package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class ParameterServiceTest extends BasicServiceTest {

  @Autowired private ParameterService parameterService;

  // UUID strings
  private static final String EXISTING_ID_STRING = "523e4567-e89b-12d3-a456-426614174001";
  private static final String NON_EXISTING_ID_STRING = "a12b34cd-5678-90ef-1234-567890abcdef";

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
    parameterDTO.setId(UUID.fromString(EXISTING_ID_STRING));
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
  void testFindAllParameterService() {
    Mono<List<ParameterDTO>> parametersMono = parameterService.findAll();
    List<ParameterDTO> parameterDTOs = parametersMono.block();

    Assert.assertNotNull(parameterDTOs);
    Assert.assertEquals(1, parameterDTOs.size());
    Assert.assertEquals("parameter_name", parameterDTOs.get(0).getName());
  }

  @Test
  @Transactional
  void testFindByIdParameterService() {
    UUID existingId = UUID.fromString(EXISTING_ID_STRING);
    Mono<ParameterDTO> parameterDTOMono = parameterService.findOne(existingId);

    StepVerifier.create(parameterDTOMono)
        .expectNextMatches(
            parameterDTO -> {
              System.out.println("Found Parameter by ID: " + parameterDTO);
              Assert.assertEquals(existingId, parameterDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdParameterServiceForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_ID_STRING);
    Mono<Boolean> exists = parameterService.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  @Transactional
  void testExistsByIdParameterServiceForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_ID_STRING);
    Mono<Boolean> exists = parameterService.existsById(nonExistingId);

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  @Transactional
  void testSaveParameterService() {
    Mono<ParameterDTO> savedParameter = parameterService.save(getParameterDTO());
    savedParameter.subscribe(
        parameterDTO -> {
          Assert.assertEquals(getParameterDTO().getName(), parameterDTO.getName());
          parameterService.delete(parameterDTO.getId()).block();
        });
  }

  @Test
  @Transactional
  void testUpdateParameterService() {
    Mono<ParameterDTO> updateParameterDTO = parameterService.save(getUpdatedParameterDTO());

    StepVerifier.create(updateParameterDTO)
        .expectNextMatches(
            parameterDTO -> {
              System.out.println("Updated Parameter: " + parameterDTO);
              Assert.assertEquals(getUpdatedParameterDTO().getName(), parameterDTO.getName());
              return true;
            })
        .verifyComplete();
  }
}
