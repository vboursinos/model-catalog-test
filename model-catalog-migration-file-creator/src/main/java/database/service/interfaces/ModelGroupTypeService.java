package database.service.interfaces;

import database.entity.ModelGroupType;
import java.util.List;
import java.util.UUID;

public interface ModelGroupTypeService {

  public ModelGroupType save(ModelGroupType modelGroupType);

  public ModelGroupType update(ModelGroupType modelGroupType);

  public void delete(UUID id);

  public ModelGroupType findOne(UUID id);

  public List<ModelGroupType> findAll();

  public boolean existsById(UUID id);
}
