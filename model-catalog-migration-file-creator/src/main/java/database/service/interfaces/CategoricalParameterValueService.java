package database.service.interfaces;

import database.entity.CategoricalParameterValue;
import java.util.List;
import java.util.UUID;

public interface CategoricalParameterValueService {

  public CategoricalParameterValue save(CategoricalParameterValue categoricalParameterValue);

  public CategoricalParameterValue update(CategoricalParameterValue categoricalParameterValue);

  public void delete(UUID id);

  public CategoricalParameterValue findOne(UUID id);

  public List<CategoricalParameterValue> findAll();

  public boolean existsById(UUID id);
}
