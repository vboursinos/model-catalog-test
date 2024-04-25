package database.service;

import database.entity.Metric;
import database.repository.MetricRepository;
import database.service.interfaces.MetricService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetricServiceImpl implements MetricService {

  @Autowired private MetricRepository metricRepository;

  @Transactional
  public Metric save(Metric metric) {
    return metricRepository.save(metric);
  }

  @Transactional
  public Metric update(Metric metric) {
    return metricRepository.save(metric);
  }

  @Transactional
  public void delete(UUID id) {
    metricRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Metric findOne(UUID id) {
    return metricRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Metric> findAll() {
    return metricRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return metricRepository.existsById(id);
  }
}
