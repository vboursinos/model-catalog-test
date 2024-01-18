package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
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
}
