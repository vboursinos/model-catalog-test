package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
import org.mapstruct.*;

/** Mapper for the entity {@link ModelGroupType} and its DTO {@link ModelGroupTypeDTO}. */
@Mapper(componentModel = "spring", implementationName = "ModelGroupTypeMapperTOImpl")
public interface ModelGroupTypeMapper extends EntityMapper<ModelGroupTypeTO, ModelGroupTypeDTO> {}
