package database.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;
import database.dto.ModelDTO;
import database.entity.Model;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ModelMapper extends MapperInterface<ModelDTO, Model> {
  //  @Mapping(target = "groups", source = "groups")
  //  @Mapping(target = "incompatibleMetrics", source = "incompatibleMetrics")
  //  @Mapping(target = "mlTask", source = "mlTask")
  //  @Mapping(target = "structure", source = "structure")
  //  @Mapping(target = "types", source = "types")
  //  @Mapping(target = "familyType", source = "familyType")
  //  @Mapping(target = "ensembleType", source = "ensembleType")
  //  ModelDTO toDto(Model s);
}
