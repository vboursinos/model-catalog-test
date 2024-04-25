package database.service;

import database.entity.BooleanParameter;
import database.repository.BooleanParameterRepository;
import database.service.interfaces.BooleanParameterService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BooleanParameterServiceImpl implements BooleanParameterService {

  @Autowired private BooleanParameterRepository booleanParameterRepository;

  @Transactional
  public BooleanParameter save(BooleanParameter booleanParameter) {
    return booleanParameterRepository.save(booleanParameter);
  }

  @Transactional
  public BooleanParameter update(BooleanParameter booleanParameter) {
    return booleanParameterRepository.save(booleanParameter);
  }

  @Transactional
  public void delete(UUID id) {
    booleanParameterRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public BooleanParameter findOne(UUID id) {
    return booleanParameterRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<BooleanParameter> findAll() {
    return booleanParameterRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return booleanParameterRepository.existsById(id);
  }
}
