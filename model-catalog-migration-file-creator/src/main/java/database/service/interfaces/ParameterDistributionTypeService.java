package database.service.interfaces;

import database.entity.ParameterDistributionType;
import java.util.List;
import java.util.UUID;

public interface ParameterDistributionTypeService {

  public ParameterDistributionType save(ParameterDistributionType parameterDistributionType);

  public ParameterDistributionType update(ParameterDistributionType parameterDistributionType);

  public void delete(UUID id);

  public ParameterDistributionType findOne(UUID id);

  public List<ParameterDistributionType> findAll();

  public boolean existsById(UUID id);
}
