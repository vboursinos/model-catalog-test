package database.service.interfaces;

import database.entity.MlTaskType;
import java.util.List;
import java.util.UUID;

public interface MlTaskTypeService {

  public MlTaskType save(MlTaskType mlTaskType);

  public MlTaskType update(MlTaskType mlTaskType);

  public void delete(UUID id);

  public MlTaskType findOne(UUID id);

  public List<MlTaskType> findAll();

  public boolean existsById(UUID id);
}
