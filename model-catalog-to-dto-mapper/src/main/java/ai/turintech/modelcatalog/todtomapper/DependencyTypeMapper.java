package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.DependencyTypeDTO;
import ai.turintech.modelcatalog.to.DependencyTypeTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "DependencyTypeMapperTOImpl")
public interface DependencyTypeMapper
    extends MapperInterface<DependencyTypeTO, DependencyTypeDTO> {}
