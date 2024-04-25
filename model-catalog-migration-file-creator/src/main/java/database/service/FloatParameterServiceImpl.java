package database.service;

import database.entity.FloatParameter;
import database.repository.FloatParameterRepository;
import database.service.interfaces.FloatParameterService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FloatParameterServiceImpl implements FloatParameterService {

  @Autowired private FloatParameterRepository floatParameterRepository;

  @Transactional
  public FloatParameter save(FloatParameter floatParameter) {
    return floatParameterRepository.save(floatParameter);
  }

  @Transactional
  public FloatParameter update(FloatParameter floatParameter) {
    return floatParameterRepository.save(floatParameter);
  }

  @Transactional
  public void delete(UUID id) {
    floatParameterRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public FloatParameter findOne(UUID id) {
    return floatParameterRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<FloatParameter> findAll() {
    return floatParameterRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return floatParameterRepository.existsById(id);
  }
}
