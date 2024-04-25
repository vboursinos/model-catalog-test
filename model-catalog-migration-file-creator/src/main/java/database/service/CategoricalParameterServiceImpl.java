package database.service;

import database.entity.CategoricalParameter;
import database.repository.CategoricalParameterRepository;
import database.service.interfaces.CategoricalParameterService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoricalParameterServiceImpl implements CategoricalParameterService {

  @Autowired private CategoricalParameterRepository categoricalParameterRepository;

  @Transactional
  public CategoricalParameter save(CategoricalParameter categoricalParameter) {
    return categoricalParameterRepository.save(categoricalParameter);
  }

  @Transactional
  public CategoricalParameter update(CategoricalParameter categoricalParameter) {
    return categoricalParameterRepository.save(categoricalParameter);
  }

  @Transactional
  public void delete(UUID id) {
    categoricalParameterRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public CategoricalParameter findOne(UUID id) {
    return categoricalParameterRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<CategoricalParameter> findAll() {
    return categoricalParameterRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return categoricalParameterRepository.existsById(id);
  }
}
