package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CategoricalParameter} and its DTO {@link CategoricalParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterMapper extends EntityMapper<CategoricalParameterDTO, CategoricalParameter> {
    //    @Mapping(target = "parameterTypeDefinition", source = "parameterTypeDefinition", qualifiedByName = "parameterTypeDefinitionId")
    @Mapping(target = "defaultValue", source = "defaultValue")
    @Mapping(target = "categoricalParameterValues", source = "categoricalParameterValues")
    CategoricalParameterDTO toDto(CategoricalParameter s);

    @Named("parameterTypeDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParameterTypeDefinitionDTO toDtoParameterTypeDefinitionId(ParameterTypeDefinition parameterTypeDefinition);
}
