package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.BooleanParameterMapper;
import ai.turintech.modelcatalog.entity.BooleanParameter;
import ai.turintech.modelcatalog.repository.BooleanParameterRepository;
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
public class BooleanParameterCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private BooleanParameterDTO booleanParameterDTO;

    public BooleanParameterCallable(String name) {
        this.name = name;
    }

    public BooleanParameterCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public BooleanParameterCallable(String name, BooleanParameterDTO booleanParameterDTO) {
        this.name = name;
        this.booleanParameterDTO = booleanParameterDTO;
    }
    @Autowired
    private BooleanParameterRepository booleanParameterRepository;
    @Autowired
    private BooleanParameterMapper booleanParameterMapper;

    private List<BooleanParameterDTO> findAll() {
        return booleanParameterRepository
                .findAll()
                .stream()
                .map(booleanParameterMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public BooleanParameterDTO findById() throws Exception {
        Optional<BooleanParameter> booleanParameter = booleanParameterRepository.findById(id);
        if (!booleanParameter.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return booleanParameterMapper.toDto(booleanParameter.get());
    }

    public BooleanParameterDTO create() throws Exception {
        BooleanParameter booleanParameter = booleanParameterMapper.toEntity(booleanParameterDTO);
        booleanParameter = booleanParameterRepository.save(booleanParameter);
        return booleanParameterMapper.toDto(booleanParameter);
    }

    public BooleanParameterDTO update() throws Exception {
        BooleanParameter booleanParameter = booleanParameterMapper.toEntity(booleanParameterDTO);
        booleanParameter = booleanParameterRepository.save(booleanParameter);
        return booleanParameterMapper.toDto(booleanParameter);
    }

    public BooleanParameterDTO partialUpdate() throws Exception {
        return booleanParameterRepository
                .findById(booleanParameterDTO.getParameterTypeDefinitionId())
                .map(existingBooleanParameter -> {
                    booleanParameterMapper.partialUpdate(existingBooleanParameter, booleanParameterDTO);

                    return existingBooleanParameter;
                })
                .map(booleanParameterRepository::save)
                .map(booleanParameterMapper::toDto).get();
    }

    public void delete() throws Exception {
        booleanParameterRepository.deleteById(id);
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