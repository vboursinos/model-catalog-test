package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.to.ParameterTO;
import java.util.List;
import org.mapstruct.Mapper;

/** Mapper for the entity {@link Parameter} and its DTO {@link ParameterDTO}. */
@Mapper(componentModel = "spring", implementationName = "ParameterMapperTOImpl")
public interface ParameterMapper extends MapperInterface<ParameterTO, ParameterDTO> {

  List<ParameterDTO> toDto(List<ParameterTO> s);

  List<ParameterTO> toTO(List<ParameterDTO> s);
}
