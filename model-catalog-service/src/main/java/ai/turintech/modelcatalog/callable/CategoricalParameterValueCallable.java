package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.dtoentitymapper.CategoricalParameterValueMapper;
import ai.turintech.modelcatalog.entity.CategoricalParameterValue;
import ai.turintech.modelcatalog.repository.CategoricalParameterValueRepository;
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
public class CategoricalParameterValueCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private CategoricalParameterValueDTO categoricalParameterValueDTO;

    public CategoricalParameterValueCallable(String name) {
        this.name = name;
    }

    public CategoricalParameterValueCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public CategoricalParameterValueCallable(String name, CategoricalParameterValueDTO categoricalParameterValueDTO) {
        this.name = name;
        this.categoricalParameterValueDTO = categoricalParameterValueDTO;
    }

    @Autowired
    private CategoricalParameterValueRepository categoricalParameterValueRepository;
    @Autowired
    private CategoricalParameterValueMapper categoricalParameterValueMapper;

    private List<CategoricalParameterValueDTO> findAll() {
        return categoricalParameterValueRepository
                .findAll()
                .stream()
                .map(categoricalParameterValueMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public CategoricalParameterValueDTO findById() throws Exception {
        Optional<CategoricalParameterValue> categoricalParameterValue = categoricalParameterValueRepository.findById(id);
        if (!categoricalParameterValue.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return categoricalParameterValueMapper.toDto(categoricalParameterValue.get());
    }

    public CategoricalParameterValueDTO create() throws Exception {
        CategoricalParameterValue categoricalParameterValue = categoricalParameterValueMapper.toEntity(categoricalParameterValueDTO);
        categoricalParameterValue = categoricalParameterValueRepository.save(categoricalParameterValue);
        return categoricalParameterValueMapper.toDto(categoricalParameterValue);
    }

    public CategoricalParameterValueDTO update() throws Exception {
        CategoricalParameterValue categoricalParameterValue = categoricalParameterValueMapper.toEntity(categoricalParameterValueDTO);
        categoricalParameterValue = categoricalParameterValueRepository.save(categoricalParameterValue);
        return categoricalParameterValueMapper.toDto(categoricalParameterValue);
    }

    public CategoricalParameterValueDTO partialUpdate() throws Exception {
        return categoricalParameterValueRepository
                .findById(categoricalParameterValueDTO.getId())
                .map(existingCategoricalParameter -> {
                    categoricalParameterValueMapper.partialUpdate(existingCategoricalParameter, categoricalParameterValueDTO);

                    return existingCategoricalParameter;
                })
                .map(categoricalParameterValueRepository::save)
                .map(categoricalParameterValueMapper::toDto).get();
    }

    public void delete() throws Exception {
        categoricalParameterValueRepository.deleteById(id);
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