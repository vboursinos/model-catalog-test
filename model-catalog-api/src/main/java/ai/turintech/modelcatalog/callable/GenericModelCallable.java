package ai.turintech.modelcatalog.callable;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudCallable;
import ai.turintech.components.data.common.dto.AbstractDTO;
import ai.turintech.components.data.common.entity.AbstractEntity;
import java.util.List;
import java.util.UUID;

public interface GenericModelCallable<T, DTO extends AbstractDTO, ENTITY extends AbstractEntity>
    extends ReactiveAbstractCrudCallable<T, DTO, ENTITY, UUID> {
  public List<DTO> findAll();

  public DTO findById() throws Exception;

  public Boolean existsById();

  public DTO create();

  public DTO update();

  public DTO partialUpdate() throws Exception;

  public void delete();

  public T call() throws Exception;
}
