package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.IntegerParameterValueMapper;
import ai.turintech.modelcatalog.entity.IntegerParameterValue;
import ai.turintech.modelcatalog.repository.IntegerParameterValueRepository;
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
public class IntegerParameterValueCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private IntegerParameterValueDTO integerParameterValueDTO;

    public IntegerParameterValueCallable(String name) {
        this.name = name;
    }

    public IntegerParameterValueCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public IntegerParameterValueCallable(String name, IntegerParameterValueDTO integerParameterValueDTO) {
        this.name = name;
        this.integerParameterValueDTO = integerParameterValueDTO;
    }

    @Autowired
    private IntegerParameterValueRepository integerParameterValueRepository;
    @Autowired
    private IntegerParameterValueMapper integerParameterValueMapper;

    private List<IntegerParameterValueDTO> findAll() {
        return integerParameterValueRepository
                .findAll()
                .stream()
                .map(integerParameterValueMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public IntegerParameterValueDTO findById() throws Exception {
        Optional<IntegerParameterValue> integerParameterValue = integerParameterValueRepository.findById(id);
        if (!integerParameterValue.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return integerParameterValueMapper.toDto(integerParameterValue.get());
    }

    public IntegerParameterValueDTO create() throws Exception {
        IntegerParameterValue integerParameterValue = integerParameterValueMapper.toEntity(integerParameterValueDTO);
        integerParameterValue = integerParameterValueRepository.save(integerParameterValue);
        return integerParameterValueMapper.toDto(integerParameterValue);
    }

    public IntegerParameterValueDTO update() throws Exception {
        IntegerParameterValue integerParameterValue = integerParameterValueMapper.toEntity(integerParameterValueDTO);
        integerParameterValue = integerParameterValueRepository.save(integerParameterValue);
        return integerParameterValueMapper.toDto(integerParameterValue);
    }

    public IntegerParameterValueDTO partialUpdate() throws Exception {
        return integerParameterValueRepository
                .findById(integerParameterValueDTO.getId())
                .map(existingIntegerParameter -> {
                    integerParameterValueMapper.partialUpdate(existingIntegerParameter, integerParameterValueDTO);

                    return existingIntegerParameter;
                })
                .map(integerParameterValueRepository::save)
                .map(integerParameterValueMapper::toDto).get();
    }

    public void delete() throws Exception {
        integerParameterValueRepository.deleteById(id);
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