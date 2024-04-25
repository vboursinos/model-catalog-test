package database.service;

import database.entity.FloatParameterRange;
import database.repository.FloatParameterRangeRepository;
import database.service.interfaces.FloatParameterRangeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FloatParameterRangeServiceImpl implements FloatParameterRangeService {

  @Autowired private FloatParameterRangeRepository floatParameterRangeRepository;

  @Transactional
  public FloatParameterRange save(FloatParameterRange floatParameterRange) {
    return floatParameterRangeRepository.save(floatParameterRange);
  }

  @Transactional
  public FloatParameterRange update(FloatParameterRange floatParameterRange) {
    return floatParameterRangeRepository.save(floatParameterRange);
  }

  @Transactional
  public void delete(UUID id) {
    floatParameterRangeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public FloatParameterRange findOne(UUID id) {
    return floatParameterRangeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<FloatParameterRange> findAll() {
    return floatParameterRangeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return floatParameterRangeRepository.existsById(id);
  }
}
