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

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.facade.BooleanParameterFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing {@link ai.turintech.catalog.domain.BooleanParameter}.
 */
@RestController
@RequestMapping("/api")
public class BooleanParameterResource {

    private final Logger log = LoggerFactory.getLogger(BooleanParameterResource.class);

    private static final String ENTITY_NAME = "modelCatalogBooleanParameter";

    @Value("${jhipster.clientApp.name:'modelCatalogApp'}")
    private String applicationName;

    private final BooleanParameterFacade booleanParameterFacade;


    public BooleanParameterResource(BooleanParameterFacade booleanParameterFacade) {
        this.booleanParameterFacade = booleanParameterFacade;
    }

    /**
     * {@code POST  /boolean-parameters} : Create a new booleanParameter.
     *
     * @param booleanParameterDTO the booleanParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new booleanParameterDTO, or with status {@code 400 (Bad Request)} if the booleanParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boolean-parameters")
    public Mono<ResponseEntity<BooleanParameterDTO>> createBooleanParameter(@RequestBody BooleanParameterDTO booleanParameterDTO)
        throws URISyntaxException {
        log.debug("REST request to save BooleanParameter : {}", booleanParameterDTO);
        if (booleanParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new booleanParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return booleanParameterFacade
            .save(booleanParameterDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/boolean-parameters/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /boolean-parameters/:id} : Updates an existing booleanParameter.
     *
     * @param id the id of the booleanParameterDTO to save.
     * @param booleanParameterDTO the booleanParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated booleanParameterDTO,
     * or with status {@code 400 (Bad Request)} if the booleanParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the booleanParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boolean-parameters/{id}")
    public Mono<ResponseEntity<BooleanParameterDTO>> updateBooleanParameter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BooleanParameterDTO booleanParameterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BooleanParameter : {}, {}", id, booleanParameterDTO);
        if (booleanParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, booleanParameterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return booleanParameterFacade.existsById(id)
        		.flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return booleanParameterFacade
                    .update(booleanParameterDTO)
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
     * {@code PATCH  /boolean-parameters/:id} : Partial updates given fields of an existing booleanParameter, field will ignore if it is null
     *
     * @param id the id of the booleanParameterDTO to save.
     * @param booleanParameterDTO the booleanParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated booleanParameterDTO,
     * or with status {@code 400 (Bad Request)} if the booleanParameterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the booleanParameterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the booleanParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/boolean-parameters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<BooleanParameterDTO>> partialUpdateBooleanParameter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BooleanParameterDTO booleanParameterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BooleanParameter partially : {}, {}", id, booleanParameterDTO);
        if (booleanParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, booleanParameterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return booleanParameterFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<BooleanParameterDTO> result = booleanParameterFacade.partialUpdate(booleanParameterDTO);

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
     * {@code GET  /boolean-parameters} : get all the booleanParameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of booleanParameters in body.
     */
    @GetMapping(value = "/boolean-parameters", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<BooleanParameterDTO>> getAllBooleanParameters() {
        log.debug("REST request to get all BooleanParameters");
        return booleanParameterFacade.findAll().collectList();
    }

    /**
     * {@code GET  /boolean-parameters} : get all the booleanParameters as a stream.
     * @return the {@link Flux} of booleanParameters.
     */
    @GetMapping(value = "/boolean-parameters", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<BooleanParameterDTO> getAllBooleanParametersAsStream() {
        log.debug("REST request to get all BooleanParameters as a stream");
        return booleanParameterFacade.findAll();
    }

    /**
     * {@code GET  /boolean-parameters/:id} : get the "id" booleanParameter.
     *
     * @param id the id of the booleanParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the booleanParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boolean-parameters/{id}")
    public Mono<ResponseEntity<BooleanParameterDTO>> getBooleanParameter(@PathVariable Long id) {
        log.debug("REST request to get BooleanParameter : {}", id);
        Mono<BooleanParameterDTO> booleanParameterDTO = booleanParameterFacade.findOne(id);
        return ResponseUtil.wrapOrNotFound(booleanParameterDTO);
    }

    /**
     * {@code DELETE  /boolean-parameters/:id} : delete the "id" booleanParameter.
     *
     * @param id the id of the booleanParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boolean-parameters/{id}")
    public Mono<ResponseEntity<Void>> deleteBooleanParameter(@PathVariable Long id) {
        log.debug("REST request to delete BooleanParameter : {}", id);
        return booleanParameterFacade
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
