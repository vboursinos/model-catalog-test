package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelEnsembleType} and its DTO {@link ModelEnsembleTypeDTO}. */
@Mapper(componentModel = "spring")
public interface ModelEnsembleTypeMapper
    extends MapperInterface<ModelEnsembleTypeDTO, ModelEnsembleType> {}
