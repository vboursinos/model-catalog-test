package ai.turintech.modelcatalog.callable;

import java.util.List;

public interface GenericModelCallable<T, DTO, ENTITY> {
  public List<DTO> findAll();

  public DTO findById() throws Exception;

  public Boolean existsById();

  public DTO create();

  public DTO update();

  public DTO partialUpdate() throws Exception;

  public void delete();

  public T call() throws Exception;
}
