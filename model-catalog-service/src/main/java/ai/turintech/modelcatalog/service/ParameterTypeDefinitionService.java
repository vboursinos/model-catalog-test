package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.repository.ParameterTypeDefinitionRepository;
import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ParameterTypeDefinitionMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link ai.turintech.catalog.domain.ParameterTypeDefinition}.
 */
@Service
@Transactional
public class ParameterTypeDefinitionService {

    private final Logger log = LoggerFactory.getLogger(ParameterTypeDefinitionService.class);

    private final ParameterTypeDefinitionRepository parameterTypeDefinitionRepository;

    private final ParameterTypeDefinitionMapper parameterTypeDefinitionMapper;

    public ParameterTypeDefinitionService(
        ParameterTypeDefinitionRepository parameterTypeDefinitionRepository,
        ParameterTypeDefinitionMapper parameterTypeDefinitionMapper
    ) {
        this.parameterTypeDefinitionRepository = parameterTypeDefinitionRepository;
        this.parameterTypeDefinitionMapper = parameterTypeDefinitionMapper;
    }

    /**
     * Save a parameterTypeDefinition.
     *
     * @param parameterTypeDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ParameterTypeDefinitionDTO> save(ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
        log.debug("Request to save ParameterTypeDefinition : {}", parameterTypeDefinitionDTO);
        return parameterTypeDefinitionRepository
            .save(parameterTypeDefinitionMapper.toEntity(parameterTypeDefinitionDTO))
            .map(parameterTypeDefinitionMapper::toDto);
    }

    /**
     * Update a parameterTypeDefinition.
     *
     * @param parameterTypeDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ParameterTypeDefinitionDTO> update(ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
        log.debug("Request to update ParameterTypeDefinition : {}", parameterTypeDefinitionDTO);
        return parameterTypeDefinitionRepository
            .save(parameterTypeDefinitionMapper.toEntity(parameterTypeDefinitionDTO).setIsPersisted())
            .map(parameterTypeDefinitionMapper::toDto);
    }

    /**
     * Partially update a parameterTypeDefinition.
     *
     * @param parameterTypeDefinitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ParameterTypeDefinitionDTO> partialUpdate(ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
        log.debug("Request to partially update ParameterTypeDefinition : {}", parameterTypeDefinitionDTO);

        return parameterTypeDefinitionRepository
            .findById(parameterTypeDefinitionDTO.getId())
            .map(existingParameterTypeDefinition -> {
                parameterTypeDefinitionMapper.partialUpdate(existingParameterTypeDefinition, parameterTypeDefinitionDTO);

                return existingParameterTypeDefinition;
            })
            .flatMap(parameterTypeDefinitionRepository::save)
            .map(parameterTypeDefinitionMapper::toDto);
    }

    /**
     * Get all the parameterTypeDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ParameterTypeDefinitionDTO> findAll() {
        log.debug("Request to get all ParameterTypeDefinitions");
        return parameterTypeDefinitionRepository.findAll().map(parameterTypeDefinitionMapper::toDto);
    }

    /**
     * Returns the number of parameterTypeDefinitions available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return parameterTypeDefinitionRepository.count();
    }

    /**
     * Get one parameterTypeDefinition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ParameterTypeDefinitionDTO> findOne(UUID id) {
        log.debug("Request to get ParameterTypeDefinition : {}", id);
        return parameterTypeDefinitionRepository.findById(id).map(parameterTypeDefinitionMapper::toDto);
    }

    /**
     * Delete the parameterTypeDefinition by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(UUID id) {
        log.debug("Request to delete ParameterTypeDefinition : {}", id);
        return parameterTypeDefinitionRepository.deleteById(id);
    }
    
    /**
     * Returns whether or not a ParameterTypeDefinition exists with provided id.
     * @param id
     * @return a Mono to signal the existence of the ParameterTypeDefinition
     */
    public Mono<Boolean> existsById(UUID id) {
    	log.debug("Request to delete ParameterTypeDefinition : {}", id);
    	return this.parameterTypeDefinitionRepository.existsById(id);
    }
}
