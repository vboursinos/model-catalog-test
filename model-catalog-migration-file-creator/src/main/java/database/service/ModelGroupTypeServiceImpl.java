package database.service;

import database.entity.ModelGroupType;
import database.repository.ModelGroupTypeRepository;
import database.service.interfaces.ModelGroupTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelGroupTypeServiceImpl implements ModelGroupTypeService {

  @Autowired private ModelGroupTypeRepository modelGroupTypeRepository;

  @Transactional
  public ModelGroupType save(ModelGroupType modelGroupType) {
    return modelGroupTypeRepository.save(modelGroupType);
  }

  @Transactional
  public ModelGroupType update(ModelGroupType modelGroupType) {
    return modelGroupTypeRepository.save(modelGroupType);
  }

  @Transactional
  public void delete(UUID id) {
    modelGroupTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ModelGroupType findOne(UUID id) {
    return modelGroupTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ModelGroupType> findAll() {
    return modelGroupTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return modelGroupTypeRepository.existsById(id);
  }
}
