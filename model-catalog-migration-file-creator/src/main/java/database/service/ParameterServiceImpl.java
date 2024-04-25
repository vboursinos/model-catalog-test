package database.service;

import database.entity.Parameter;
import database.repository.ParameterRepository;
import database.service.interfaces.ParameterService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParameterServiceImpl implements ParameterService {

  @Autowired private ParameterRepository parameterRepository;

  @Transactional
  public Parameter save(Parameter parameter) {
    return parameterRepository.save(parameter);
  }

  @Transactional
  public Parameter update(Parameter parameter) {
    return parameterRepository.save(parameter);
  }

  @Transactional
  public void delete(UUID id) {
    parameterRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Parameter findOne(UUID id) {
    return parameterRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Parameter> findAll() {
    return parameterRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return parameterRepository.existsById(id);
  }

  @Transactional(readOnly = true)
  public Parameter findByName(String name) {
    return parameterRepository.findByName(name).orElse(null);
  }
}
