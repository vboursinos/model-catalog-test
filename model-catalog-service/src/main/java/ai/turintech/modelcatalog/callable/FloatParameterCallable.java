package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterMapper;
import ai.turintech.modelcatalog.entity.FloatParameter;
import ai.turintech.modelcatalog.repository.FloatParameterRepository;
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
public class FloatParameterCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private FloatParameterDTO floatParameterDTO;

    public FloatParameterCallable(String name) {
        this.name = name;
    }

    public FloatParameterCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public FloatParameterCallable(String name, FloatParameterDTO floatParameterDTO) {
        this.name = name;
        this.floatParameterDTO = floatParameterDTO;
    }

    @Autowired
    private FloatParameterRepository floatParameterRepository;
    @Autowired
    private FloatParameterMapper floatParameterMapper;

    private List<FloatParameterDTO> findAll() {
        return floatParameterRepository
                .findAll()
                .stream()
                .map(floatParameterMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public FloatParameterDTO findById() throws Exception {
        Optional<FloatParameter> floatParameter = floatParameterRepository.findById(id);
        if (!floatParameter.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return floatParameterMapper.toDto(floatParameter.get());
    }

    public FloatParameterDTO create() throws Exception {
        FloatParameter floatParameter = floatParameterMapper.toEntity(floatParameterDTO);
        floatParameter = floatParameterRepository.save(floatParameter);
        return floatParameterMapper.toDto(floatParameter);
    }

    public FloatParameterDTO update() throws Exception {
        FloatParameter floatParameter = floatParameterMapper.toEntity(floatParameterDTO);
        floatParameter = floatParameterRepository.save(floatParameter);
        return floatParameterMapper.toDto(floatParameter);
    }

    public FloatParameterDTO partialUpdate() throws Exception {
        return floatParameterRepository
                .findById(floatParameterDTO.getParameterTypeDefinitionId())
                .map(existingFloatParameter -> {
                    floatParameterMapper.partialUpdate(existingFloatParameter, floatParameterDTO);

                    return existingFloatParameter;
                })
                .map(floatParameterRepository::save)
                .map(floatParameterMapper::toDto).get();
    }

    public void delete() throws Exception {
        floatParameterRepository.deleteById(id);
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