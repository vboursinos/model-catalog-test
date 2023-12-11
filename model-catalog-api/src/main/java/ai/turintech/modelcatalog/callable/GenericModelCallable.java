package ai.turintech.modelcatalog.callable;

import ai.turintech.components.architecture.reactive.ReactiveAbstractUUIDIdentityCrudCallable;
import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import ai.turintech.components.data.common.entity.AbstractEntity;
import java.util.UUID;

public interface GenericModelCallable<
        T, DTO extends AbstractUUIDIdentityDTO<UUID>, ENTITY extends AbstractEntity>
    extends ReactiveAbstractUUIDIdentityCrudCallable<T, DTO, ENTITY, UUID> {}
