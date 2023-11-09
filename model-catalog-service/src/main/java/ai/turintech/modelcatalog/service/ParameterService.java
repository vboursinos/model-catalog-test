package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.callable.ParameterCallable;
import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterMapper;
import ai.turintech.modelcatalog.entity.Parameter;
import ai.turintech.modelcatalog.repository.ParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Service Implementation for managing {@link Parameter}.
 */
@Service
@Transactional
public class ParameterService {

    private final Logger log = LoggerFactory.getLogger(ParameterService.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Scheduler jdbcScheduler;
    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private ParameterMapper parameterMapper;

    /**
     * Save a parameter.
     *
     * @param parameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ParameterDTO> save(ParameterDTO parameterDTO) {
        log.debug("Request to save Parameter : {}", parameterDTO);
        return Mono.fromCallable(() ->  {
            Parameter parameter = parameterMapper.toEntity(parameterDTO);
            parameter = parameterRepository.save(parameter);
            return parameterMapper.toDto(parameter);
        });
    }

    /**
     * Update a parameter.
     *
     * @param parameterDTO the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Mono<ParameterDTO> update(ParameterDTO parameterDTO) {
        log.debug("Request to update Parameter : {}", parameterDTO);
        Callable<ParameterDTO> callable = context.getBean(ParameterCallable.class, "update", parameterDTO);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Partially update a parameter.
     *
     * @param parameterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Optional<ParameterDTO>> partialUpdate(ParameterDTO parameterDTO) {
        log.debug("Request to partially update Parameter : {}", parameterDTO);
        Callable<Optional<ParameterDTO>> callable = context.getBean(ParameterCallable.class, "partialUpdate", parameterDTO);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Get all the parameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Mono<List<ParameterDTO>> findAll(Pageable pageable) {
        log.debug("Request to get all Parameters");
        Callable<List<ParameterDTO>> callable = context.getBean(ParameterCallable.class, "findAll");
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }


    @Transactional(readOnly = true)
    public Flux<ParameterDTO> findAllStream(Pageable pageable) {
        log.debug("Request to get all Parameters");
        return Flux.defer(() -> Flux.fromIterable(parameterRepository.findAll(pageable)
                        .map(parameterMapper::toDto).getContent()))
                .subscribeOn(Schedulers.boundedElastic());
    }
    /**
     * Get one parameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ParameterDTO> findOne(UUID id) {
        log.debug("Request to get Parameter : {}", id);
        Callable<ParameterDTO> callable = context.getBean(ParameterCallable.class, "findById", id);
        return Mono.fromCallable(callable).subscribeOn(jdbcScheduler);
    }

    /**
     * Delete the parameter by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete Parameter : {}", id);
        Callable<Void> callable = context.getBean(ParameterCallable.class, "delete" , id);
        Mono delete = Mono.fromCallable(callable);
        delete.subscribe();
        return delete;
    }
}
