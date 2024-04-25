package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ParameterDistributionTypeDTO;
import database.entity.ParameterDistributionType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ParameterDistributionTypeMapper
    extends MapperInterface<ParameterDistributionTypeDTO, ParameterDistributionType> {}
