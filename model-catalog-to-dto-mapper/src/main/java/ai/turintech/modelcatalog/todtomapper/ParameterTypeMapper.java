package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.to.ParameterTypeTO;
import java.util.List;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link ParameterType} and its DTO {@link ParameterTypeDTO}. */
@Mapper(componentModel = "spring", implementationName = "ParameterTypeMapperTOImpl")
public interface ParameterTypeMapper extends MapperInterface<ParameterTypeTO, ParameterTypeDTO> {

  List<ParameterTypeDTO> toDto(List<ParameterTypeTO> s);

  List<ParameterTypeTO> toTO(List<ParameterTypeDTO> s);
}
