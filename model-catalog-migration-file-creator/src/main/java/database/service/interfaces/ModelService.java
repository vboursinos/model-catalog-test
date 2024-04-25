package database.service.interfaces;

import database.entity.Model;
import java.util.List;
import java.util.UUID;

public interface ModelService {

  public Model save(Model model);

  public Model update(Model model);

  public void delete(UUID id);

  public Model findOne(UUID id);

  public List<Model> findAll();

  public boolean existsById(UUID id);

  public Model findByName(String name);
}
