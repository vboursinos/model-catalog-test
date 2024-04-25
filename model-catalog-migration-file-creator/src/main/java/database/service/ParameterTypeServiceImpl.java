package database.service;

import database.entity.ParameterType;
import database.repository.ParameterTypeRepository;
import database.service.interfaces.ParameterTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParameterTypeServiceImpl implements ParameterTypeService {

  @Autowired private ParameterTypeRepository parameterTypeRepository;

  @Transactional
  public ParameterType save(ParameterType parameterType) {
    return parameterTypeRepository.save(parameterType);
  }

  @Transactional
  public ParameterType update(ParameterType parameterType) {
    return parameterTypeRepository.save(parameterType);
  }

  @Transactional
  public void delete(UUID id) {
    parameterTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ParameterType findOne(UUID id) {
    return parameterTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ParameterType> findAll() {
    return parameterTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return parameterTypeRepository.existsById(id);
  }
}
