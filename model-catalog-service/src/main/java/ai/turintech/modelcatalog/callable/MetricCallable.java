package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.dtoentitymapper.MetricMapper;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.repository.MetricRepository;
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
public class MetricCallable<T> implements Callable<T> {
    private String name;
    private UUID id;
    private MetricDTO metricDTO;

    public MetricCallable(String name) {
        this.name = name;
    }

    public MetricCallable(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public MetricCallable(String name, MetricDTO metricDTO) {
        this.name = name;
        this.metricDTO = metricDTO;
    }

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private MetricMapper metricMapper;

    private List<MetricDTO> findAll() {
        return metricRepository.findAll().stream().map(metricMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public MetricDTO findById() throws Exception {
        Optional<Metric> metric = metricRepository.findById(id);
        if (!metric.isPresent()) {
            throw new Exception("Metric with ID " + id + " not found.");
        }
        return metricMapper.toDto(metric.get());
    }

    public MetricDTO create() throws Exception {
        Metric metric = metricMapper.toEntity(metricDTO);
        metric = metricRepository.save(metric);
        return metricMapper.toDto(metric);
    }

    public MetricDTO update() throws Exception {
        Metric metric = metricMapper.toEntity(metricDTO);
        metric = metricRepository.save(metric);
        return metricMapper.toDto(metric);
    }

    public MetricDTO partialUpdate() throws Exception {
        return metricRepository
                .findById(metricDTO.getId())
                .map(existingMetric -> {
                    metricMapper.partialUpdate(existingMetric, metricDTO);

                    return existingMetric;
                })
                .map(metricRepository::save)
                .map(metricMapper::toDto).get();
    }

    public void delete() throws Exception {
        metricRepository.deleteById(id);
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