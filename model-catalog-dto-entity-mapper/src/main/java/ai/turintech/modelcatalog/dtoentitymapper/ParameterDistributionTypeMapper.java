package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterDistributionType} and its DTO {@link ParameterDistributionTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterDistributionTypeMapper extends EntityMapper<ParameterDistributionTypeDTO, ParameterDistributionType> {
    @Mapping(target = "parameter", source = "parameter", qualifiedByName = "parameterId")
    ParameterDistributionTypeDTO toDto(ParameterDistributionType s);

    @Named("parameterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterDTO toDtoParameterId(Parameter parameter);
}
