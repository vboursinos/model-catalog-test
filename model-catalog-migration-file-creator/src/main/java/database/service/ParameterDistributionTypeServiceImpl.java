package database.service;

import database.entity.ParameterDistributionType;
import database.repository.ParameterDistributionTypeRepository;
import database.service.interfaces.ParameterDistributionTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParameterDistributionTypeServiceImpl implements ParameterDistributionTypeService {

  @Autowired private ParameterDistributionTypeRepository parameterDistributionTypeRepository;

  @Transactional
  public ParameterDistributionType save(ParameterDistributionType parameterDistributionType) {
    return parameterDistributionTypeRepository.save(parameterDistributionType);
  }

  @Transactional
  public ParameterDistributionType update(ParameterDistributionType parameterDistributionType) {
    return parameterDistributionTypeRepository.save(parameterDistributionType);
  }

  @Transactional
  public void delete(UUID id) {
    parameterDistributionTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ParameterDistributionType findOne(UUID id) {
    return parameterDistributionTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ParameterDistributionType> findAll() {
    return parameterDistributionTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return parameterDistributionTypeRepository.existsById(id);
  }
}
