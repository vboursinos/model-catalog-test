package database.service;

import database.entity.DependencyGroupType;
import database.repository.DependencyGroupTypeRepository;
import database.service.interfaces.DependencyGroupTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DependencyGroupTypeServiceImpl implements DependencyGroupTypeService {

  @Autowired private DependencyGroupTypeRepository dependencyGroupTypeRepository;

  @Transactional
  public DependencyGroupType save(DependencyGroupType dependencyGroupType) {
    return dependencyGroupTypeRepository.save(dependencyGroupType);
  }

  @Transactional
  public DependencyGroupType update(DependencyGroupType dependencyGroupType) {
    return dependencyGroupTypeRepository.save(dependencyGroupType);
  }

  @Transactional
  public void delete(UUID id) {
    dependencyGroupTypeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public DependencyGroupType findOne(UUID id) {
    return dependencyGroupTypeRepository.findById(id).orElse(null);
  }

  @Transactional(readOnly = true)
  public List<DependencyGroupType> findAll() {
    return dependencyGroupTypeRepository.findAll();
  }

  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return dependencyGroupTypeRepository.existsById(id);
  }
}
