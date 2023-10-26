package ai.turintech.modelcatalog.rest.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ai.turintech.modelcatalog.facade.FloatParameterFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import ai.turintech.modelcatalog.to.FloatParameterTO;
import ai.turintech.modelcatalog.todtomapper.FloatParameterMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing {@link ai.turintech.catalog.domain.FloatParameter}.
 */
@RestController
@RequestMapping("/api")
public class FloatParameterResource {

    private final Logger log = LoggerFactory.getLogger(FloatParameterResource.class);

    private static final String ENTITY_NAME = "modelCatalogFloatParameter";

    @Value("${jhipster.clientApp.name:'modelCatalogApp'}")
    private String applicationName;

    private final FloatParameterFacade floatParameterFacade;
    private final FloatParameterMapper floatParameterMapper;

    public FloatParameterResource(FloatParameterFacade floatParameterFacade,FloatParameterMapper floatParameterMapper) {
        this.floatParameterFacade = floatParameterFacade;
        this.floatParameterMapper = floatParameterMapper;
    }

    /**
     * {@code POST  /float-parameters} : Create a new floatParameter.
     *
     * @param floatParameterDTO the floatParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new floatParameterDTO, or with status {@code 400 (Bad Request)} if the floatParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/float-parameters")
    public Mono<ResponseEntity<FloatParameterTO>> createFloatParameter(@RequestBody FloatParameterTO floatParameterTO)
        throws URISyntaxException {
        log.debug("REST request to save FloatParameter : {}", floatParameterTO);
        if (floatParameterTO.getId() != null) {
            throw new BadRequestAlertException("A new floatParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return floatParameterFacade
            .save(floatParameterMapper.toDto(floatParameterTO)).map(floatParameterMapper::toTo)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/float-parameters/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /float-parameters/:id} : Updates an existing floatParameter.
     *
     * @param id the id of the floatParameterDTO to save.
     * @param floatParameterDTO the floatParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floatParameterDTO,
     * or with status {@code 400 (Bad Request)} if the floatParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the floatParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/float-parameters/{id}")
    public Mono<ResponseEntity<FloatParameterTO>> updateFloatParameter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FloatParameterTO floatParameterTO
    ) throws URISyntaxException {
        log.debug("REST request to update FloatParameter : {}, {}", id, floatParameterTO);
        if (floatParameterTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floatParameterTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return floatParameterFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return floatParameterFacade
                    .update(floatParameterMapper.toDto(floatParameterTO)).map(floatParameterMapper::toTo)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /float-parameters/:id} : Partial updates given fields of an existing floatParameter, field will ignore if it is null
     *
     * @param id the id of the floatParameterDTO to save.
     * @param floatParameterDTO the floatParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floatParameterDTO,
     * or with status {@code 400 (Bad Request)} if the floatParameterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the floatParameterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the floatParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/float-parameters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<FloatParameterTO>> partialUpdateFloatParameter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FloatParameterTO floatParameterTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FloatParameter partially : {}, {}", id, floatParameterTO);
        if (floatParameterTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floatParameterTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return floatParameterFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<FloatParameterTO> result = floatParameterFacade.partialUpdate(floatParameterMapper.toDto(floatParameterTO)).map(floatParameterMapper::toTo);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /float-parameters} : get all the floatParameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floatParameters in body.
     */
    @GetMapping(value = "/float-parameters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<FloatParameterTO>> getAllFloatParameters() {
        log.debug("REST request to get all FloatParameters");
        return floatParameterFacade.findAll().collectList().map(floatParameterMapper::toTo);
    }

    /**
     * {@code GET  /float-parameters} : get all the floatParameters as a stream.
     * @return the {@link Flux} of floatParameters.
     */
    @GetMapping(value = "/float-parameters", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<FloatParameterTO> getAllFloatParametersAsStream() {
        log.debug("REST request to get all FloatParameters as a stream");
        return floatParameterFacade.findAll().map(floatParameterMapper::toTo);
    }

    /**
     * {@code GET  /float-parameters/:id} : get the "id" floatParameter.
     *
     * @param id the id of the floatParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the floatParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/float-parameters/{id}")
    public Mono<ResponseEntity<FloatParameterTO>> getFloatParameter(@PathVariable Long id) {
        log.debug("REST request to get FloatParameter : {}", id);
        Mono<FloatParameterTO> floatParameterTO = floatParameterFacade.findOne(id).map(floatParameterMapper::toTo);
        return ResponseUtil.wrapOrNotFound(floatParameterTO);
    }

    /**
     * {@code DELETE  /float-parameters/:id} : delete the "id" floatParameter.
     *
     * @param id the id of the floatParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/float-parameters/{id}")
    public Mono<ResponseEntity<Void>> deleteFloatParameter(@PathVariable Long id) {
        log.debug("REST request to delete FloatParameter : {}", id);
        return floatParameterFacade
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
