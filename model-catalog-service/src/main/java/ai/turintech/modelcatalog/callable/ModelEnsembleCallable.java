package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelEnsembleTypeMapper;
import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import ai.turintech.modelcatalog.repository.ModelEnsembleTypeRepository;
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
public class ModelEnsembleCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private ModelEnsembleTypeDTO modelEnsembleTypeDTO;

    public ModelEnsembleCallable(String name) {
        this.name = name;
    }

    public ModelEnsembleCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ModelEnsembleCallable(String name, ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
        this.name = name;
        this.modelEnsembleTypeDTO = modelEnsembleTypeDTO;
    }

    @Autowired
    private ModelEnsembleTypeRepository modelEnsembleTypeRepository;

    @Autowired
    private ModelEnsembleTypeMapper modelEnsembleTypeMapper;

    private List<ModelEnsembleTypeDTO> findAll() {
        return modelEnsembleTypeRepository.findAll().stream().map(modelEnsembleTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public ModelEnsembleTypeDTO findById() throws Exception {
        Optional<ModelEnsembleType> modelEnsembleType = modelEnsembleTypeRepository.findById(id);
        if (!modelEnsembleType.isPresent()) {
            throw new Exception("ML Task Type with ID " + id + " not found.");
        }
        return modelEnsembleTypeMapper.toDto(modelEnsembleType.get());
    }

    public ModelEnsembleTypeDTO create() throws Exception {
        ModelEnsembleType modelEnsembleType = modelEnsembleTypeMapper.toEntity(modelEnsembleTypeDTO);
        modelEnsembleType = modelEnsembleTypeRepository.save(modelEnsembleType);
        return modelEnsembleTypeMapper.toDto(modelEnsembleType);
    }

    public ModelEnsembleTypeDTO update() throws Exception {
        ModelEnsembleType modelEnsembleType = modelEnsembleTypeMapper.toEntity(modelEnsembleTypeDTO);
        modelEnsembleType = modelEnsembleTypeRepository.save(modelEnsembleType);
        return modelEnsembleTypeMapper.toDto(modelEnsembleType);
    }

    public ModelEnsembleTypeDTO partialUpdate() throws Exception {
        return modelEnsembleTypeRepository
                .findById(modelEnsembleTypeDTO.getId())
                .map(existingModelEnsemble -> {
                    modelEnsembleTypeMapper.partialUpdate(existingModelEnsemble, modelEnsembleTypeDTO);

                    return existingModelEnsemble;
                })
                .map(modelEnsembleTypeRepository::save)
                .map(modelEnsembleTypeMapper::toDto).get();
    }

    public void delete() throws Exception {
        modelEnsembleTypeRepository.deleteById(id);
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