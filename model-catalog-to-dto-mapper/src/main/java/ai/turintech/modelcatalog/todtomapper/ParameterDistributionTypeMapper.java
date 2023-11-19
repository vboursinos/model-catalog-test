package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
import java.util.List;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterDistributionType} and its DTO {@link
 * ParameterDistributionTypeDTO}.
 */
@Mapper(componentModel = "spring", implementationName = "ParameterDistributionTypeMapperTOImpl")
public interface ParameterDistributionTypeMapper
    extends MapperInterface<ParameterDistributionTypeTO, ParameterDistributionTypeDTO> {

  List<ParameterDistributionTypeDTO> toDto(List<ParameterDistributionTypeTO> s);

  List<ParameterDistributionTypeTO> toTO(List<ParameterDistributionTypeDTO> s);
}
