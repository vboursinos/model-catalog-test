package ai.turintech.modelcatalog.dtoentitymapper;

import ai.turintech.components.mapper.api.MapperInterface;

public interface AbstractMapper<D, E> extends MapperInterface<D, E>, EntityMapper<D, E> {
}
