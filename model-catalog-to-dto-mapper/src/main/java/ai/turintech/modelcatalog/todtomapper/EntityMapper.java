package ai.turintech.modelcatalog.todtomapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <T> - TO type parameter.
 * @param <D> - DTO type parameter.
 */

public interface EntityMapper<T, D> {
    T toTo(D dto);

    D toDto(T to);

    List<T> toTo(List<D> dtoList);

    List<D> toDto(List<D> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget T to, D dto);
}
