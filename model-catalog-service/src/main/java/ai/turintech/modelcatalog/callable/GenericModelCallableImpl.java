package ai.turintech.modelcatalog.callable;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import ai.turintech.components.data.common.entity.AbstractEntity;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@Scope("prototype")
public abstract class GenericModelCallableImpl<
        T, DTO extends AbstractUUIDIdentityDTO<UUID>, ENTITY extends AbstractEntity>
    implements GenericModelCallable<T, DTO, ENTITY> {}
