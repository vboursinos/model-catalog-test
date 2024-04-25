package database.service;

import database.entity.CategoricalParameterValue;
import database.repository.CategoricalParameterValueRepository;
import database.service.interfaces.CategoricalParameterValueService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoricalParameterValueServiceImpl implements CategoricalParameterValueService {

  @Autowired private CategoricalParameterValueRepository categoricalParameterValueRepository;

  @Transactional
  public CategoricalParameterValue save(CategoricalParameterValue categoricalParameterValue) {
    return categoricalParameterValueRepository.save(categoricalParameterValue);
  }

  @Transactional
  public CategoricalParameterValue update(CategoricalParameterValue categoricalParameterValue) {
    return categoricalParameterValueRepository.save(categoricalParameterValue);
  }

  @Transactional
  public void delete(UUID id) {
    categoricalParameterValueRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public CategoricalParameterValue findOne(UUID id) {
    return categoricalParameterValueRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<CategoricalParameterValue> findAll() {
    return categoricalParameterValueRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return categoricalParameterValueRepository.existsById(id);
  }
}
