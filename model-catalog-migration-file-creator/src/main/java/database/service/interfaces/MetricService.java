package database.service.interfaces;

import database.entity.Metric;
import java.util.List;
import java.util.UUID;

public interface MetricService {

  public Metric save(Metric metric);

  public Metric update(Metric metric);

  public void delete(UUID id);

  public Metric findOne(UUID id);

  public List<Metric> findAll();

  public boolean existsById(UUID id);
}
