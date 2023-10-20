package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.to.ModelGroupTypeTO;
import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelGroupType} and its DTO {@link ModelGroupTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelGroupTypeMapper extends EntityMapper<ModelGroupTypeTO, ModelGroupTypeDTO> {}
