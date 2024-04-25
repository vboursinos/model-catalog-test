package database.service;

import database.entity.Model;
import database.repository.ModelRepository;
import database.service.interfaces.ModelService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelServiceImpl implements ModelService {

  @Autowired private ModelRepository modelRepository;

  @Transactional
  public Model save(Model model) {
    return modelRepository.save(model);
  }

  @Transactional
  public Model update(Model model) {
    return modelRepository.save(model);
  }

  @Transactional
  public void delete(UUID id) {
    modelRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Model findOne(UUID id) {
    return modelRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<Model> findAll() {
    return modelRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return modelRepository.existsById(id);
  }

  @Transactional(readOnly = true)
  public Model findByName(String name) {
    return modelRepository.findByName(name).orElse(null);
  }
}
