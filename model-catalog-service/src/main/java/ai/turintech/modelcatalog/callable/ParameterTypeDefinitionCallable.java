package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterTypeDefinitionMapper;
import ai.turintech.modelcatalog.entity.ParameterTypeDefinition;
import ai.turintech.modelcatalog.repository.ParameterTypeDefinitionRepository;
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
public class ParameterTypeDefinitionCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private ParameterTypeDefinitionDTO parameterTypeDefinitionDTO;

    public ParameterTypeDefinitionCallable(String name) {
        this.name = name;
    }

    public ParameterTypeDefinitionCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ParameterTypeDefinitionCallable(String name, ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
        this.name = name;
        this.parameterTypeDefinitionDTO = parameterTypeDefinitionDTO;
    }

    @Autowired
    private ParameterTypeDefinitionRepository parameterTypeDefinitionRepository;

    @Autowired
    private ParameterTypeDefinitionMapper parameterTypeDefinitionMapper;

    private List<ParameterTypeDefinitionDTO> findAll() {
        return parameterTypeDefinitionRepository.findAll().stream().map(parameterTypeDefinitionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public ParameterTypeDefinitionDTO findById() throws Exception {
        Optional<ParameterTypeDefinition> parameterTypeDefinition = parameterTypeDefinitionRepository.findById(id);
        if (!parameterTypeDefinition.isPresent()) {
            throw new Exception("Parameter type definition with ID " + id + " not found.");
        }
        return parameterTypeDefinitionMapper.toDto(parameterTypeDefinition.get());
    }

    public ParameterTypeDefinitionDTO create() throws Exception {
        ParameterTypeDefinition parameterTypeDefinition = parameterTypeDefinitionMapper.toEntity(parameterTypeDefinitionDTO);
        parameterTypeDefinition = parameterTypeDefinitionRepository.save(parameterTypeDefinition);
        return parameterTypeDefinitionMapper.toDto(parameterTypeDefinition);
    }

    public ParameterTypeDefinitionDTO update() throws Exception {
        ParameterTypeDefinition parameterTypeDefinition = parameterTypeDefinitionMapper.toEntity(parameterTypeDefinitionDTO);
        parameterTypeDefinition = parameterTypeDefinitionRepository.save(parameterTypeDefinition);
        return parameterTypeDefinitionMapper.toDto(parameterTypeDefinition);
    }

    public ParameterTypeDefinitionDTO partialUpdate() throws Exception {
        return parameterTypeDefinitionRepository
                .findById(parameterTypeDefinitionDTO.getId())
                .map(existingParameterTypeDefinition -> {
                    parameterTypeDefinitionMapper.partialUpdate(existingParameterTypeDefinition, parameterTypeDefinitionDTO);

                    return existingParameterTypeDefinition;
                })
                .map(parameterTypeDefinitionRepository::save)
                .map(parameterTypeDefinitionMapper::toDto).get();
    }

    public void delete() throws Exception {
        parameterTypeDefinitionRepository.deleteById(id);
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