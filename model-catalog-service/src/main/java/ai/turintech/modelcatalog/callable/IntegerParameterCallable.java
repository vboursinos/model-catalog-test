package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterMapper;
import ai.turintech.modelcatalog.entity.IntegerParameter;
import ai.turintech.modelcatalog.repository.IntegerParameterRepository;
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
public class IntegerParameterCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private IntegerParameterDTO integerParameterDTO;

    public IntegerParameterCallable(String name) {
        this.name = name;
    }

    public IntegerParameterCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public IntegerParameterCallable(String name, IntegerParameterDTO integerParameterDTO) {
        this.name = name;
        this.integerParameterDTO = integerParameterDTO;
    }

    @Autowired
    private IntegerParameterRepository integerParameterRepository;
    @Autowired
    private IntegerParameterMapper integerParameterMapper;

    private List<IntegerParameterDTO> findAll() {
        return integerParameterRepository
                .findAll()
                .stream()
                .map(integerParameterMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public IntegerParameterDTO findById() throws Exception {
        Optional<IntegerParameter> integerParameter = integerParameterRepository.findById(id);
        if (!integerParameter.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return integerParameterMapper.toDto(integerParameter.get());
    }

    public IntegerParameterDTO create() throws Exception {
        IntegerParameter integerParameter = integerParameterMapper.toEntity(integerParameterDTO);
        integerParameter = integerParameterRepository.save(integerParameter);
        return integerParameterMapper.toDto(integerParameter);
    }

    public IntegerParameterDTO update() throws Exception {
        IntegerParameter integerParameter = integerParameterMapper.toEntity(integerParameterDTO);
        integerParameter = integerParameterRepository.save(integerParameter);
        return integerParameterMapper.toDto(integerParameter);
    }

    public IntegerParameterDTO partialUpdate() throws Exception {
        return integerParameterRepository
                .findById(integerParameterDTO.getParameterTypeDefinitionId())
                .map(existingIntegerParameter -> {
                    integerParameterMapper.partialUpdate(existingIntegerParameter, integerParameterDTO);

                    return existingIntegerParameter;
                })
                .map(integerParameterRepository::save)
                .map(integerParameterMapper::toDto).get();
    }

    public void delete() throws Exception {
        integerParameterRepository.deleteById(id);
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