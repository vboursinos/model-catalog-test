package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import org.mapstruct.*;

/** Mapper for the entity {@link BooleanParameter} and its DTO {@link BooleanParameterDTO}. */
@Mapper(componentModel = "spring")
public interface BooleanParameterMapper
    extends AbstractMapper<BooleanParameterDTO,BooleanParameter> {

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget BooleanParameter entity, BooleanParameterDTO dto);
}
