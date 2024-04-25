package database.service.interfaces;

import database.entity.DependencyGroupType;
import java.util.List;
import java.util.UUID;

public interface DependencyGroupTypeService {

  public DependencyGroupType save(DependencyGroupType dependencyGroupType);

  public DependencyGroupType update(DependencyGroupType dependencyGroupType);

  public void delete(UUID id);

  public DependencyGroupType findOne(UUID id);

  public List<DependencyGroupType> findAll();

  public boolean existsById(UUID id);
}
