package database.service.interfaces;

import database.entity.ModelStructureType;
import java.util.List;
import java.util.UUID;

public interface ModelStructureTypeService {

  public ModelStructureType save(ModelStructureType modelStructureType);

  public ModelStructureType update(ModelStructureType modelStructureType);

  public void delete(UUID id);

  public ModelStructureType findOne(UUID id);

  public List<ModelStructureType> findAll();

  public boolean existsById(UUID id);
}
