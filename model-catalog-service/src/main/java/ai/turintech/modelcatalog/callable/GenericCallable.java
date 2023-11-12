package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dtoentitymapper.EntityMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Transactional
@Component
@Scope("prototype")
public class GenericCallable<T, DTO, ENTITY> implements Callable<T> {
    private String name;
    private UUID id;
    private DTO dto;
    private JpaRepository<ENTITY, UUID> repository;
    private EntityMapper<DTO, ENTITY> mapper;

    public GenericCallable(String name, JpaRepository<ENTITY, UUID> repository, EntityMapper<DTO, ENTITY> mapper) {
        this.name = name;
        this.repository = repository;
        this.mapper = mapper;
    }

    public GenericCallable(String name, UUID id, JpaRepository<ENTITY, UUID> repository, EntityMapper<DTO, ENTITY> mapper) {
        this.name = name;
        this.id = id;
        this.repository = repository;
        this.mapper = mapper;
    }

    public GenericCallable(String name, DTO dto, JpaRepository<ENTITY, UUID> repository, EntityMapper<DTO, ENTITY> mapper) {
        this.name = name;
        this.dto = dto;
        this.repository = repository;
        this.mapper = mapper;
    }

    private List<DTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public DTO findById() throws Exception {
        Optional<ENTITY> entity = repository.findById(id);
        if (!entity.isPresent()) {
            throw new Exception(name + " with ID " + id + " not found.");
        }
        return mapper.toDto(entity.get());
    }

    public DTO create() throws Exception {
        ENTITY entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public DTO update() throws Exception {
        ENTITY entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public DTO partialUpdate() throws Exception {
        return repository
                .findById(id)
                .map(existingEntity -> {
                    mapper.partialUpdate(existingEntity, dto);
                    return existingEntity;
                })
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new Exception(name + " with ID " + id + " not found."));
    }

    public void delete() throws Exception {
        repository.deleteById(id);
    }

    @Override
    public T call() throws Exception {
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
            case "delete":
                delete();
                break;
        }
        return null;
    }
}