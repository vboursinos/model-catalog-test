package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterTypeDefinition} and its DTO {@link
 * ParameterTypeDefinitionDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ParameterTypeDefinitionMapperTOImpl")
public interface ParameterTypeDefinitionMapper
    extends EntityMapper<ParameterTypeDefinitionTO, ParameterTypeDefinitionDTO> {}
