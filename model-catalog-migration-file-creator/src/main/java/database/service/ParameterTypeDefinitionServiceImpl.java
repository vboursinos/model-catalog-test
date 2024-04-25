package database.service;

import database.entity.ParameterTypeDefinition;
import database.repository.ParameterTypeDefinitionRepository;
import database.service.interfaces.ParameterTypeDefinitionService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParameterTypeDefinitionServiceImpl implements ParameterTypeDefinitionService {

  @Autowired private ParameterTypeDefinitionRepository parameterTypeDefinitionRepository;

  @Transactional
  public ParameterTypeDefinition save(ParameterTypeDefinition parameterTypeDefinition) {
    return parameterTypeDefinitionRepository.save(parameterTypeDefinition);
  }

  @Transactional
  public ParameterTypeDefinition update(ParameterTypeDefinition parameterTypeDefinition) {
    return parameterTypeDefinitionRepository.save(parameterTypeDefinition);
  }

  @Transactional
  public void delete(UUID id) {
    parameterTypeDefinitionRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ParameterTypeDefinition findOne(UUID id) {
    return parameterTypeDefinitionRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ParameterTypeDefinition> findAll() {
    return parameterTypeDefinitionRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return parameterTypeDefinitionRepository.existsById(id);
  }
}
