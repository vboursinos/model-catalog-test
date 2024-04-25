package database.service.interfaces;

import database.entity.IntegerParameterValue;
import java.util.List;
import java.util.UUID;

public interface IntegerParameterValueService {

  public IntegerParameterValue save(IntegerParameterValue integerParameterValue);

  public IntegerParameterValue update(IntegerParameterValue integerParameterValue);

  public void delete(UUID id);

  public IntegerParameterValue findOne(UUID id);

  public List<IntegerParameterValue> findAll();

  public boolean existsById(UUID id);
}
