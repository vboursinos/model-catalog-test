package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterDistributionType} and its DTO {@link
 * ParameterDistributionTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterDistributionTypeMapper
    extends AbstractMapper<ParameterDistributionTypeDTO,ParameterDistributionType> {}
