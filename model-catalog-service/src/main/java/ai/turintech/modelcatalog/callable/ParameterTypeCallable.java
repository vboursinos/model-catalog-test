package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterTypeMapper;
import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.repository.ParameterTypeRepository;
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
public class ParameterTypeCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private ParameterTypeDTO parameterTypeDTO;

    public ParameterTypeCallable(String name) {
        this.name = name;
    }

    public ParameterTypeCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ParameterTypeCallable(String name, ParameterTypeDTO parameterTypeDTO) {
        this.name = name;
        this.parameterTypeDTO = parameterTypeDTO;
    }

    @Autowired
    private ParameterTypeRepository parameterTypeRepository;

    @Autowired
    private ParameterTypeMapper parameterTypeMapper;

    private List<ParameterTypeDTO> findAll() {
        return parameterTypeRepository.findAll().stream().map(parameterTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public ParameterTypeDTO findById() throws Exception {
        Optional<ParameterType> parameterType = parameterTypeRepository.findById(id);
        if (!parameterType.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return parameterTypeMapper.toDto(parameterType.get());
    }

    public ParameterTypeDTO create() throws Exception {
        ParameterType parameterType = parameterTypeMapper.toEntity(parameterTypeDTO);
        parameterType = parameterTypeRepository.save(parameterType);
        return parameterTypeMapper.toDto(parameterType);
    }

    public ParameterTypeDTO update() throws Exception {
        ParameterType parameterType = parameterTypeMapper.toEntity(parameterTypeDTO);
        parameterType = parameterTypeRepository.save(parameterType);
        return parameterTypeMapper.toDto(parameterType);
    }

    public ParameterTypeDTO partialUpdate() throws Exception {
        return parameterTypeRepository
                .findById(parameterTypeDTO.getId())
                .map(existingParameterType -> {
                    parameterTypeMapper.partialUpdate(existingParameterType, parameterTypeDTO);

                    return existingParameterType;
                })
                .map(parameterTypeRepository::save)
                .map(parameterTypeMapper::toDto).get();
    }

    public void delete() throws Exception {
        parameterTypeRepository.deleteById(id);
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