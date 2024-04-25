package database.service;

import database.entity.IntegerParameter;
import database.repository.IntegerParameterRepository;
import database.service.interfaces.IntegerParameterService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IntegerParameterServiceImpl implements IntegerParameterService {

  @Autowired private IntegerParameterRepository integerParameterRepository;

  @Transactional
  public IntegerParameter save(IntegerParameter integerParameter) {
    return integerParameterRepository.save(integerParameter);
  }

  @Transactional
  public IntegerParameter update(IntegerParameter integerParameter) {
    return integerParameterRepository.save(integerParameter);
  }

  @Transactional
  public void delete(UUID id) {
    integerParameterRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public IntegerParameter findOne(UUID id) {
    return integerParameterRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<IntegerParameter> findAll() {
    return integerParameterRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return integerParameterRepository.existsById(id);
  }
}
