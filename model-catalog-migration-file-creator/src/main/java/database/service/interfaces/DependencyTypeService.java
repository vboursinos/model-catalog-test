package database.service.interfaces;

import database.entity.DependencyType;
import java.util.List;
import java.util.UUID;

public interface DependencyTypeService {

  public DependencyType save(DependencyType dependencyType);

  public DependencyType update(DependencyType dependencyType);

  public void delete(UUID id);

  public DependencyType findOne(UUID id);

  public List<DependencyType> findAll();

  public boolean existsById(UUID id);
}
