package database.service.interfaces;

import database.entity.CategoricalParameter;
import java.util.List;
import java.util.UUID;

public interface CategoricalParameterService {

  public CategoricalParameter save(CategoricalParameter categoricalParameter);

  public CategoricalParameter update(CategoricalParameter categoricalParameter);

  public void delete(UUID id);

  public CategoricalParameter findOne(UUID id);

  public List<CategoricalParameter> findAll();

  public boolean existsById(UUID id);
}
