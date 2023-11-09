package ai.turintech.modelcatalog.dtoentitymapper;
import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoricalParameterValue} and its DTO {@link CategoricalParameterValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoricalParameterValueMapper extends EntityMapper<CategoricalParameterValueDTO, CategoricalParameterValue> {
    CategoricalParameterValueDTO toDto(CategoricalParameterValue s);

    @Named("categoricalParameterId")
    @BeanMapping(ignoreByDefault = true)
    CategoricalParameterDTO toDtoCategoricalParameterId(CategoricalParameter categoricalParameter);
}
