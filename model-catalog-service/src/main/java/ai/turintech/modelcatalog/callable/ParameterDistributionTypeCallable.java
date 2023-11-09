package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterDistributionTypeMapper;
import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.repository.ParameterDistributionTypeRepository;
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
public class ParameterDistributionTypeCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private ParameterDistributionTypeDTO parameterDistributionTypeDTO;

    public ParameterDistributionTypeCallable(String name) {
        this.name = name;
    }

    public ParameterDistributionTypeCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public ParameterDistributionTypeCallable(String name, ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
        this.name = name;
        this.parameterDistributionTypeDTO = parameterDistributionTypeDTO;
    }

    @Autowired
    private ParameterDistributionTypeRepository parameterDistributionTypeRepository;

    @Autowired
    private ParameterDistributionTypeMapper parameterDistributionTypeMapper;

    private List<ParameterDistributionTypeDTO> findAll() {
        return parameterDistributionTypeRepository.findAll().stream().map(parameterDistributionTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public ParameterDistributionTypeDTO findById() throws Exception {
        Optional<ParameterDistributionType> parameterDistributionType = parameterDistributionTypeRepository.findById(id);
        if (!parameterDistributionType.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return parameterDistributionTypeMapper.toDto(parameterDistributionType.get());
    }

    public ParameterDistributionTypeDTO create() throws Exception {
        ParameterDistributionType parameterDistributionType = parameterDistributionTypeMapper.toEntity(parameterDistributionTypeDTO);
        parameterDistributionType = parameterDistributionTypeRepository.save(parameterDistributionType);
        return parameterDistributionTypeMapper.toDto(parameterDistributionType);
    }

    public ParameterDistributionTypeDTO update() throws Exception {
        ParameterDistributionType parameterDistributionType = parameterDistributionTypeMapper.toEntity(parameterDistributionTypeDTO);
        parameterDistributionType = parameterDistributionTypeRepository.save(parameterDistributionType);
        return parameterDistributionTypeMapper.toDto(parameterDistributionType);
    }

    public ParameterDistributionTypeDTO partialUpdate() throws Exception {
        return parameterDistributionTypeRepository
                .findById(parameterDistributionTypeDTO.getId())
                .map(existingDistributionType -> {
                    parameterDistributionTypeMapper.partialUpdate(existingDistributionType, parameterDistributionTypeDTO);
                    return existingDistributionType;
                })
                .map(parameterDistributionTypeRepository::save)
                .map(parameterDistributionTypeMapper::toDto).get();
    }

    public void delete() throws Exception {
        parameterDistributionTypeRepository.deleteById(id);
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