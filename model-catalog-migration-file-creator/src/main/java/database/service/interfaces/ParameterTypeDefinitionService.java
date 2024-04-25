package database.service.interfaces;

import database.entity.ParameterTypeDefinition;
import java.util.List;
import java.util.UUID;

public interface ParameterTypeDefinitionService {

  public ParameterTypeDefinition save(ParameterTypeDefinition parameterTypeDefinition);

  public ParameterTypeDefinition update(ParameterTypeDefinition parameterTypeDefinition);

  public void delete(UUID id);

  public ParameterTypeDefinition findOne(UUID id);

  public List<ParameterTypeDefinition> findAll();

  public boolean existsById(UUID id);
}
