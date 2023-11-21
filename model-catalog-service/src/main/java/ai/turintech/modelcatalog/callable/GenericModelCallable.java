package ai.turintech.modelcatalog.callable;

import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.exceptions.FindOneException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@Scope("prototype")
public class GenericModelCallable<T, DTO, ENTITY> implements Callable<T> {
  private String name;
  private UUID id;
  private DTO dto;
  private JpaRepository<ENTITY, UUID> repository;
  private MapperInterface<DTO, ENTITY> mapper;

  public GenericModelCallable(
      String name, JpaRepository<ENTITY, UUID> repository, MapperInterface<DTO, ENTITY> mapper) {
    this.name = name;
    this.repository = repository;
    this.mapper = mapper;
  }

  public GenericModelCallable(
      String name,
      UUID id,
      JpaRepository<ENTITY, UUID> repository,
      MapperInterface<DTO, ENTITY> mapper) {
    this.name = name;
    this.id = id;
    this.repository = repository;
    this.mapper = mapper;
  }

  public GenericModelCallable(
      String name,
      DTO dto,
      JpaRepository<ENTITY, UUID> repository,
      MapperInterface<DTO, ENTITY> mapper) {
    this.name = name;
    this.dto = dto;
    this.repository = repository;
    this.mapper = mapper;
  }

  public GenericModelCallable(
      String name,
      UUID id,
      DTO dto,
      JpaRepository<ENTITY, UUID> repository,
      MapperInterface<DTO, ENTITY> mapper) {
    this.name = name;
    this.id = id;
    this.dto = dto;
    this.repository = repository;
    this.mapper = mapper;
  }

  private List<DTO> findAll() {
    return repository.findAll().stream()
        .map(mapper::to)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  public DTO findById() throws FindOneException {
    Optional<ENTITY> entity = repository.findById(id);
    if (!entity.isPresent()) {
      throw new FindOneException(name + " with ID " + id + " not found.");
    }
    return mapper.to(entity.get());
  }

  public Boolean existsById() {
    return repository.existsById(id);
  }

  public DTO create() {
    ENTITY entity = mapper.from(dto);
    entity = repository.save(entity);
    return mapper.to(entity);
  }

  public DTO update() {
    ENTITY entity = mapper.from(dto);
    entity = repository.save(entity);
    return mapper.to(entity);
  }

  public DTO partialUpdate() throws FindOneException {
    return repository
        .findById(id)
        .map(
            existingEntity -> {
              mapper.partialUpdate(existingEntity, dto);
              return existingEntity;
            })
        .map(repository::save)
        .map(mapper::to)
        .orElseThrow(() -> new FindOneException(name + " with ID " + id + " not found."));
  }

  public void delete() {
    repository.deleteById(id);
  }

  @Override
  public T call() throws FindOneException {
    switch (name.toLowerCase()) {
      case "create":
        return (T) create();
      case "findall":
        return (T) findAll();
      case "findbyid":
        return (T) findById();
      case "update":
        return (T) update();
      case "partialupdate":
        return (T) partialUpdate();
      case "existsbyid":
        return (T) existsById();
      case "delete":
        delete();
        break;
    }
    return null;
  }
}
