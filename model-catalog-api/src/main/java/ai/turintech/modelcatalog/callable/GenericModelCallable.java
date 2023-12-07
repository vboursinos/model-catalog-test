package ai.turintech.modelcatalog.callable;

import ai.turintech.components.architecture.reactive.ReactiveAbstractCrudCallable;
import ai.turintech.components.data.common.dto.AbstractDTO;
import ai.turintech.components.data.common.entity.AbstractEntity;
import java.util.List;
import java.util.UUID;

public interface GenericModelCallable<T, DTO extends AbstractDTO, ENTITY extends AbstractEntity>
    extends ReactiveAbstractCrudCallable<T, DTO, ENTITY, UUID> {

  /**
   * Find all entities
   *
   * @return list of entities
   */
  public List<DTO> findAll();

  /**
   * Find entity by id
   *
   * @return entity
   */
  public DTO findById() throws Exception;

  /**
   * Check if entity exists by id
   *
   * @return true if entity exists
   */
  public Boolean existsById();

  /**
   * Create entity
   *
   * @return created entity
   */
  public DTO create();

  /**
   * Update entity
   *
   * @return updated entity
   */
  public DTO update();

  /**
   * Partially update entity
   *
   * @return partially updated entity
   */
  public DTO partialUpdate() throws Exception;

  /** Delete entity */
  public void delete();

  public T call() throws Exception;
}
