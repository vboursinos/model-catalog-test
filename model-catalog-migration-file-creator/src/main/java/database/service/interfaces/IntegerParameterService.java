package database.service.interfaces;

import database.entity.IntegerParameter;
import java.util.List;
import java.util.UUID;

public interface IntegerParameterService {

  public IntegerParameter save(IntegerParameter integerParameter);

  public IntegerParameter update(IntegerParameter integerParameter);

  public void delete(UUID id);

  public IntegerParameter findOne(UUID id);

  public List<IntegerParameter> findAll();

  public boolean existsById(UUID id);
}
