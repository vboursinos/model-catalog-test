package database.service.interfaces;

import database.entity.FloatParameter;
import java.util.List;
import java.util.UUID;

public interface FloatParameterService {

  public FloatParameter save(FloatParameter floatParameter);

  public FloatParameter update(FloatParameter floatParameter);

  public void delete(UUID id);

  public FloatParameter findOne(UUID id);

  public List<FloatParameter> findAll();

  public boolean existsById(UUID id);
}
