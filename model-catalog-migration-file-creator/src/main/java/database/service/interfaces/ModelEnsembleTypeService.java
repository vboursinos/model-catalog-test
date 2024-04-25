package database.service.interfaces;

import database.entity.ModelEnsembleType;
import java.util.List;
import java.util.UUID;

public interface ModelEnsembleTypeService {

  public ModelEnsembleType save(ModelEnsembleType modelEnsembleType);

  public ModelEnsembleType update(ModelEnsembleType modelEnsembleType);

  public void delete(UUID id);

  public ModelEnsembleType findOne(UUID id);

  public List<ModelEnsembleType> findAll();

  public boolean existsById(UUID id);
}
