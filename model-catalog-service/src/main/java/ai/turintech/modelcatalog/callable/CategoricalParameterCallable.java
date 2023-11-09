package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.CategoricalParameterMapper;
import ai.turintech.modelcatalog.entity.CategoricalParameter;
import ai.turintech.modelcatalog.repository.CategoricalParameterRepository;
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
public class CategoricalParameterCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private CategoricalParameterDTO categoricalParameterDTO;

    public CategoricalParameterCallable(String name) {
        this.name = name;
    }

    public CategoricalParameterCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public CategoricalParameterCallable(String name, CategoricalParameterDTO categoricalParameterDTO) {
        this.name = name;
        this.categoricalParameterDTO = categoricalParameterDTO;
    }

    @Autowired
    private CategoricalParameterRepository categoricalParameterRepository;
    @Autowired
    private CategoricalParameterMapper categoricalParameterMapper;

    private List<CategoricalParameterDTO> findAll() {
        return categoricalParameterRepository
                .findAll()
                .stream()
                .map(categoricalParameterMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public CategoricalParameterDTO findById() throws Exception {
        Optional<CategoricalParameter> categoricalParameter = categoricalParameterRepository.findById(id);
        if (!categoricalParameter.isPresent()) {
            throw new Exception("Parameter distribution type with ID " + id + " not found.");
        }
        return categoricalParameterMapper.toDto(categoricalParameter.get());
    }

    public CategoricalParameterDTO create() throws Exception {
        CategoricalParameter categoricalParameter = categoricalParameterMapper.toEntity(categoricalParameterDTO);
        categoricalParameter = categoricalParameterRepository.save(categoricalParameter);
        return categoricalParameterMapper.toDto(categoricalParameter);
    }

    public CategoricalParameterDTO update() throws Exception {
        CategoricalParameter categoricalParameter = categoricalParameterMapper.toEntity(categoricalParameterDTO);
        categoricalParameter = categoricalParameterRepository.save(categoricalParameter);
        return categoricalParameterMapper.toDto(categoricalParameter);
    }

    public CategoricalParameterDTO partialUpdate() throws Exception {
        return categoricalParameterRepository
                .findById(categoricalParameterDTO.getParameterTypeDefinitionId())
                .map(existingCategoricalParameter -> {
                    categoricalParameterMapper.partialUpdate(existingCategoricalParameter, categoricalParameterDTO);

                    return existingCategoricalParameter;
                })
                .map(categoricalParameterRepository::save)
                .map(categoricalParameterMapper::toDto).get();
    }

    public void delete() throws Exception {
        categoricalParameterRepository.deleteById(id);
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