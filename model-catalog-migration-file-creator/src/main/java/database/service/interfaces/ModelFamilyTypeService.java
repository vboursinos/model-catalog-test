package database.service.interfaces;

import database.entity.ModelFamilyType;
import java.util.List;
import java.util.UUID;

public interface ModelFamilyTypeService {

  public ModelFamilyType save(ModelFamilyType modelFamilyType);

  public ModelFamilyType update(ModelFamilyType modelFamilyType);

  public void delete(UUID id);

  public ModelFamilyType findOne(UUID id);

  public List<ModelFamilyType> findAll();

  public boolean existsById(UUID id);
}
