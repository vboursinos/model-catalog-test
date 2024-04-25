package database.service;

import database.entity.ModelEnsembleType;
import database.repository.ModelEnsembleTypeRepository;
import database.service.interfaces.ModelEnsembleTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModelEnsembleTypeServiceImpl implements ModelEnsembleTypeService {

  @Autowired private ModelEnsembleTypeRepository modelEnsembleTypeRepository;

  @Transactional
  public ModelEnsembleType save(ModelEnsembleType modelEnsembleType) {
    return modelEnsembleTypeRepository.save(modelEnsembleType);
  }

  @Transactional
  public ModelEnsembleType update(ModelEnsembleType modelEnsembleType) {
    return modelEnsembleTypeRepository.save(modelEnsembleType);
  }

  @Transactional
  public void delete(UUID id) {
    modelEnsembleTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public ModelEnsembleType findOne(UUID id) {
    return modelEnsembleTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<ModelEnsembleType> findAll() {
    return modelEnsembleTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return modelEnsembleTypeRepository.existsById(id);
  }
}
