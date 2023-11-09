package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelStructureTypeMapper;
import ai.turintech.modelcatalog.entity.ModelStructureType;
import ai.turintech.modelcatalog.repository.ModelStructureTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
public class ModelStructureTypeCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private ModelStructureTypeDTO modelStructureTypeDTO;

    public ModelStructureTypeCallable(String name) {
        this.name = name;
    }

    public ModelStructureTypeCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ModelStructureTypeCallable(String name, ModelStructureTypeDTO modelStructureTypeDTO) {
        this.name = name;
        this.modelStructureTypeDTO = modelStructureTypeDTO;
    }

    @Autowired
    private ModelStructureTypeRepository modelStructureTypeRepository;

    @Autowired
    private ModelStructureTypeMapper modelStructureTypeMapper;

    private List<ModelStructureTypeDTO> findAll() {
        return modelStructureTypeRepository.findAll().stream().map(modelStructureTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public ModelStructureTypeDTO findById() throws Exception {
        Optional<ModelStructureType> modelStructureType = modelStructureTypeRepository.findById(id);
        if (!modelStructureType.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return modelStructureTypeMapper.toDto(modelStructureType.get());
    }

    public ModelStructureTypeDTO create() throws Exception {
        ModelStructureType modelStructureType = modelStructureTypeMapper.toEntity(modelStructureTypeDTO);
        modelStructureType = modelStructureTypeRepository.save(modelStructureType);
        return modelStructureTypeMapper.toDto(modelStructureType);
    }

    public ModelStructureTypeDTO update() throws Exception {
        ModelStructureType modelStructureType = modelStructureTypeMapper.toEntity(modelStructureTypeDTO);
        modelStructureType = modelStructureTypeRepository.save(modelStructureType);
        return modelStructureTypeMapper.toDto(modelStructureType);
    }

    public ModelStructureTypeDTO partialUpdate() throws Exception {
        return modelStructureTypeRepository
                .findById(modelStructureTypeDTO.getId())
                .map(existingStructureType -> {
                    modelStructureTypeMapper.partialUpdate(existingStructureType, modelStructureTypeDTO);
                    return existingStructureType;
                })
                .map(modelStructureTypeRepository::save)
                .map(modelStructureTypeMapper::toDto).get();
    }

    public void delete() throws Exception {
        modelStructureTypeRepository.deleteById(id);
    }

    @Override
    public T call() throws Exception {
        if (name.equalsIgnoreCase("create")) {
            return (T) create();
        } else if (name.equalsIgnoreCase("findAll")) {
            return (T) findAll();
        } else if (name.equalsIgnoreCase("findById")) {
            return (T) findById();
        } else if (name.equalsIgnoreCase("update")) {
            return (T) update();
        } else if (name.equalsIgnoreCase("partialUpdate")) {
            return (T) partialUpdate();
        } else if (name.equalsIgnoreCase("delete")) {
            delete();
        }
        return null;
    }
}