package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.Parameter;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parameter} and its DTO {@link ParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterMapper extends EntityMapper<ParameterDTO, Parameter> {
    ParameterDTO toDto(Parameter s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
