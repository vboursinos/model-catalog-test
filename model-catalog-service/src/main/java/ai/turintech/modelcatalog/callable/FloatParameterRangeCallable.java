package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.dtoentitymapper.FloatParameterRangeMapper;
import ai.turintech.modelcatalog.entity.FloatParameterRange;
import ai.turintech.modelcatalog.repository.FloatParameterRangeRepository;
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
public class FloatParameterRangeCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private FloatParameterRangeDTO floatParameterRangeDTO;

    public FloatParameterRangeCallable(String name) {
        this.name = name;
    }

    public FloatParameterRangeCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public FloatParameterRangeCallable(String name, FloatParameterRangeDTO floatParameterRangeDTO) {
        this.name = name;
        this.floatParameterRangeDTO = floatParameterRangeDTO;
    }

    @Autowired
    private FloatParameterRangeRepository floatParameterRangeRepository;
    @Autowired
    private FloatParameterRangeMapper floatParameterRangeMapper;

    private List<FloatParameterRangeDTO> findAll() {
        return floatParameterRangeRepository
                .findAll()
                .stream()
                .map(floatParameterRangeMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public FloatParameterRangeDTO findById() throws Exception {
        Optional<FloatParameterRange> floatParameterRange = floatParameterRangeRepository.findById(id);
        if (!floatParameterRange.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return floatParameterRangeMapper.toDto(floatParameterRange.get());
    }

    public FloatParameterRangeDTO create() throws Exception {
        FloatParameterRange floatParameterRange = floatParameterRangeMapper.toEntity(floatParameterRangeDTO);
        floatParameterRange = floatParameterRangeRepository.save(floatParameterRange);
        return floatParameterRangeMapper.toDto(floatParameterRange);
    }

    public FloatParameterRangeDTO update() throws Exception {
        FloatParameterRange floatParameterRange = floatParameterRangeMapper.toEntity(floatParameterRangeDTO);
        floatParameterRange = floatParameterRangeRepository.save(floatParameterRange);
        return floatParameterRangeMapper.toDto(floatParameterRange);
    }

    public FloatParameterRangeDTO partialUpdate() throws Exception {
        return floatParameterRangeRepository
                .findById(floatParameterRangeDTO.getId())
                .map(existingFloatParameter -> {
                    floatParameterRangeMapper.partialUpdate(existingFloatParameter, floatParameterRangeDTO);

                    return existingFloatParameter;
                })
                .map(floatParameterRangeRepository::save)
                .map(floatParameterRangeMapper::toDto).get();
    }

    public void delete() throws Exception {
        floatParameterRangeRepository.deleteById(id);
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