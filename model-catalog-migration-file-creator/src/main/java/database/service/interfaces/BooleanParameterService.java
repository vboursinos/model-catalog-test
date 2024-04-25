package database.service.interfaces;

import database.entity.BooleanParameter;
import java.util.List;
import java.util.UUID;

public interface BooleanParameterService {

  public BooleanParameter save(BooleanParameter booleanParameter);

  public BooleanParameter update(BooleanParameter booleanParameter);

  public void delete(UUID id);

  public BooleanParameter findOne(UUID id);

  public List<BooleanParameter> findAll();

  public boolean existsById(UUID id);
}
