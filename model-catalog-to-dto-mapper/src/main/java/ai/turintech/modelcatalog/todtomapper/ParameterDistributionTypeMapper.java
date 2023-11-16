package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.to.ParameterDistributionTypeTO;
import ai.turintech.modelcatalog.to.ParameterTO;
import org.mapstruct.*;

import java.util.List;

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
