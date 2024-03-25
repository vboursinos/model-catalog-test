package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.DependencyGroupTypeDTO;
import ai.turintech.modelcatalog.to.DependencyGroupTypeTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "DependencyGroupTypeMapperTOImpl")
public interface DependencyGroupTypeMapper
    extends MapperInterface<DependencyGroupTypeTO, DependencyGroupTypeDTO> {}
