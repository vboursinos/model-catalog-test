package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ModelEnsembleTypeDTO;
import database.entity.ModelEnsembleType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ModelEnsembleTypeMapper
    extends MapperInterface<ModelEnsembleTypeDTO, ModelEnsembleType> {}
