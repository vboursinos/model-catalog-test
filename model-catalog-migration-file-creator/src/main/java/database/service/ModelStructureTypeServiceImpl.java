package database.service;

import database.entity.ModelStructureType;
import database.repository.ModelStructureTypeRepository;
import database.service.interfaces.ModelStructureTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelStructureTypeServiceImpl implements ModelStructureTypeService {

  @Autowired private ModelStructureTypeRepository modelStructureTypeRepository;

  @Transactional
  public ModelStructureType save(ModelStructureType modelStructureType) {
    return modelStructureTypeRepository.save(modelStructureType);
  }

  @Transactional
  public ModelStructureType update(ModelStructureType modelStructureType) {
    return modelStructureTypeRepository.save(modelStructureType);
  }

  @Transactional
  public void delete(UUID id) {
    modelStructureTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ModelStructureType findOne(UUID id) {
    return modelStructureTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ModelStructureType> findAll() {
    return modelStructureTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return modelStructureTypeRepository.existsById(id);
  }
}
