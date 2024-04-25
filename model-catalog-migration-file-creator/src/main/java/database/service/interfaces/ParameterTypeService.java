package database.service.interfaces;

import database.entity.ParameterType;
import java.util.List;
import java.util.UUID;

public interface ParameterTypeService {

  public ParameterType save(ParameterType parameterType);

  public ParameterType update(ParameterType parameterType);

  public void delete(UUID id);

  public ParameterType findOne(UUID id);

  public List<ParameterType> findAll();

  public boolean existsById(UUID id);
}
