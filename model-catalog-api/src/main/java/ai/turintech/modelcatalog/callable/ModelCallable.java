package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;

import java.util.concurrent.Callable;

public interface ModelCallable<T> extends Callable<T> {

  public ModelPaginatedListDTO findAll();

  public T call();
}
