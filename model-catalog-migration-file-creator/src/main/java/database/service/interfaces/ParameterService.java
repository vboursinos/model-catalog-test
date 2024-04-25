package database.service.interfaces;

import database.entity.Parameter;
import java.util.List;
import java.util.UUID;

public interface ParameterService {

  public Parameter save(Parameter parameter);

  public Parameter update(Parameter parameter);

  public void delete(UUID id);

  public Parameter findOne(UUID id);

  public List<Parameter> findAll();

  public boolean existsById(UUID id);

  public Parameter findByName(String name);
}
