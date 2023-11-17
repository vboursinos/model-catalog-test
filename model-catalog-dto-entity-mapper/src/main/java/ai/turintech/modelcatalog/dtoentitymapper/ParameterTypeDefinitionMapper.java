package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterTypeDefinition} and its DTO {@link
 * ParameterTypeDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterTypeDefinitionMapper
    extends AbstractMapper<ParameterTypeDefinitionDTO,ParameterTypeDefinition> {}
