package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.to.ParameterTO;
import ai.turintech.modelcatalog.to.ParameterTypeDefinitionTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;

/**
 * Mapper for the entity {@link Parameter} and its DTO {@link ParameterDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ParameterMapperTOImpl")
public interface ParameterMapper extends MapperInterface<ParameterTO, ParameterDTO> {

    List<ParameterDTO> toDto(List<ParameterTO> s);

    List<ParameterTO> toTO(List<ParameterDTO> s);

    ParameterTO toTo(Optional<ParameterDTO> parameterDTO);
}
