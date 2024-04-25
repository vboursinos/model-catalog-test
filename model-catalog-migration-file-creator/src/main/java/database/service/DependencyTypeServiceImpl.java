package database.service;

import database.entity.DependencyType;
import database.repository.DependencyTypeRepository;
import database.service.interfaces.DependencyTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DependencyTypeServiceImpl implements DependencyTypeService {

  @Autowired private DependencyTypeRepository dependencyTypeRepository;

  @Transactional
  public DependencyType save(DependencyType dependencyType) {
    return dependencyTypeRepository.save(dependencyType);
  }

  @Transactional
  public DependencyType update(DependencyType dependencyType) {
    return dependencyTypeRepository.save(dependencyType);
  }

  @Transactional
  public void delete(UUID id) {
    dependencyTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public DependencyType findOne(UUID id) {
    return dependencyTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<DependencyType> findAll() {
    return dependencyTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return dependencyTypeRepository.existsById(id);
  }
}
