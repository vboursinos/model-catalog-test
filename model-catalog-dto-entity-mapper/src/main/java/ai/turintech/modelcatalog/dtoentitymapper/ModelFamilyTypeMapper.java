package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.entity.ModelFamilyType;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ModelFamilyType} and its DTO {@link ModelFamilyTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelFamilyTypeMapper extends AbstractMapper<ModelFamilyTypeDTO, ModelFamilyType> {
}
