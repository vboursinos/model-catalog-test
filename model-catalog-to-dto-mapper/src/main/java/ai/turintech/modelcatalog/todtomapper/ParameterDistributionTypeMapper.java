package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
import ai.turintech.modelcatalog.to.ParameterTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterDistributionType} and its DTO {@link
 * ParameterDistributionTypeDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ParameterDistributionTypeMapperTOImpl")
public interface ParameterDistributionTypeMapper
    extends EntityMapper<ParameterDistributionTypeTO, ParameterDistributionTypeDTO> {
  ParameterDistributionTypeDTO toDto(ParameterDistributionTypeTO s);

  @Named("parameterId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  ParameterDTO toDtoParameterId(ParameterTO parameter);
}
