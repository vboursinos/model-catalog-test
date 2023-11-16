package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link ParameterTypeDefinition} and its DTO {@link
 * ParameterTypeDefinitionDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ParameterTypeDefinitionMapperTOImpl")
public interface ParameterTypeDefinitionMapper
    extends MapperInterface<ParameterTypeDefinitionTO, ParameterTypeDefinitionDTO> {

    List<ParameterTypeDefinitionDTO> toDto(List<ParameterTypeDefinitionTO> s);

    List<ParameterTypeDefinitionTO> toTO(List<ParameterTypeDefinitionDTO> s);
}
