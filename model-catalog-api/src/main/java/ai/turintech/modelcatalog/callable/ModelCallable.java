package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;

public interface ModelCallable<T> {

  public ModelPaginatedListDTO findAll();

  public T call();
}
