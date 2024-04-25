package database.service;

import database.entity.ModelFamilyType;
import database.repository.ModelFamilyTypeRepository;
import database.service.interfaces.ModelFamilyTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelFamilyTypeServiceImpl implements ModelFamilyTypeService {

  @Autowired private ModelFamilyTypeRepository modelFamilyTypeRepository;

  @Transactional
  public ModelFamilyType save(ModelFamilyType modelFamilyType) {
    return modelFamilyTypeRepository.save(modelFamilyType);
  }

  @Transactional
  public ModelFamilyType update(ModelFamilyType modelFamilyType) {
    return modelFamilyTypeRepository.save(modelFamilyType);
  }

  @Transactional
  public void delete(UUID id) {
    modelFamilyTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ModelFamilyType findOne(UUID id) {
    return modelFamilyTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ModelFamilyType> findAll() {
    return modelFamilyTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return modelFamilyTypeRepository.existsById(id);
  }
}
