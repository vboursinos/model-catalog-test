package database.service;

import database.entity.MlTaskType;
import database.repository.MlTaskTypeRepository;
import database.service.interfaces.MlTaskTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MlTaskTypeServiceImpl implements MlTaskTypeService {

  @Autowired private MlTaskTypeRepository mlTaskTypeRepository;

  @Transactional
  public MlTaskType save(MlTaskType mlTaskType) {
    return mlTaskTypeRepository.save(mlTaskType);
  }

  @Transactional
  public MlTaskType update(MlTaskType mlTaskType) {
    return mlTaskTypeRepository.save(mlTaskType);
  }

  @Transactional
  public void delete(UUID id) {
    mlTaskTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public MlTaskType findOne(UUID id) {
    return mlTaskTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<MlTaskType> findAll() {
    return mlTaskTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return mlTaskTypeRepository.existsById(id);
  }
}
