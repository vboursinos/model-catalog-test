package database.service;

import database.entity.ModelType;
import database.repository.ModelTypeRepository;
import database.service.interfaces.ModelTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelTypeServiceImpl implements ModelTypeService {

  @Autowired private ModelTypeRepository modelTypeRepository;

  @Transactional
  public ModelType save(ModelType modelType) {
    return modelTypeRepository.save(modelType);
  }

  @Transactional
  public ModelType update(ModelType modelType) {
    return modelTypeRepository.save(modelType);
  }

  @Transactional
  public void delete(UUID id) {
    modelTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ModelType findOne(UUID id) {
    return modelTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ModelType> findAll() {
    return modelTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return modelTypeRepository.existsById(id);
  }
}
