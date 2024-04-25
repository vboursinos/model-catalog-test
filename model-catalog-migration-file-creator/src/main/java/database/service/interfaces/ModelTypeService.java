package database.service.interfaces;

import database.entity.ModelType;
import java.util.List;
import java.util.UUID;

public interface ModelTypeService {

  public ModelType save(ModelType modelType);

  public ModelType update(ModelType modelType);

  public void delete(UUID id);

  public ModelType findOne(UUID id);

  public List<ModelType> findAll();

  public boolean existsById(UUID id);
}
