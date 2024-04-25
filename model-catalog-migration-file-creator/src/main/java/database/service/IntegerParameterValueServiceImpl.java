package database.service;

import database.entity.IntegerParameterValue;
import database.repository.IntegerParameterValueRepository;
import database.service.interfaces.IntegerParameterValueService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IntegerParameterValueServiceImpl implements IntegerParameterValueService {

  @Autowired private IntegerParameterValueRepository integerParameterValueRepository;

  @Transactional
  public IntegerParameterValue save(IntegerParameterValue integerParameterValue) {
    return integerParameterValueRepository.save(integerParameterValue);
  }

  @Transactional
  public IntegerParameterValue update(IntegerParameterValue integerParameterValue) {
    return integerParameterValueRepository.save(integerParameterValue);
  }

  @Transactional
  public void delete(UUID id) {
    integerParameterValueRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public IntegerParameterValue findOne(UUID id) {
    return integerParameterValueRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<IntegerParameterValue> findAll() {
    return integerParameterValueRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return integerParameterValueRepository.existsById(id);
  }
}
