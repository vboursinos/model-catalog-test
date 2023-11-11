package ai.turintech.modelcatalog.todtomapper;

import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;
import ai.turintech.modelcatalog.to.ModelPaginatedListTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ModelPaginatedListMapper {
    ModelPaginatedListTO toTo(ModelPaginatedListDTO s);
}
