package database.service.interfaces;

import database.entity.FloatParameterRange;
import java.util.List;
import java.util.UUID;

public interface FloatParameterRangeService {

  public FloatParameterRange save(FloatParameterRange floatParameterRange);

  public FloatParameterRange update(FloatParameterRange floatParameterRange);

  public void delete(UUID id);

  public FloatParameterRange findOne(UUID id);

  public List<FloatParameterRange> findAll();

  public boolean existsById(UUID id);
}
