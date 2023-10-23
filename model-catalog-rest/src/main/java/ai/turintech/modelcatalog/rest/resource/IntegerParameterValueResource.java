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

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.facade.IntegerParameterValueFacade;
import ai.turintech.modelcatalog.rest.errors.BadRequestAlertException;
import ai.turintech.modelcatalog.rest.support.HeaderUtil;
import ai.turintech.modelcatalog.rest.support.reactive.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for managing {@link ai.turintech.catalog.domain.IntegerParameterValue}.
 */
@RestController
@RequestMapping("/api")
public class IntegerParameterValueResource {

    private final Logger log = LoggerFactory.getLogger(IntegerParameterValueResource.class);

    private static final String ENTITY_NAME = "modelCatalogIntegerParameterValue";

    @Value("${jhipster.clientApp.name:'modelCatalogApp'}")
    private String applicationName;

    private final IntegerParameterValueFacade integerParameterValueFacade;

    public IntegerParameterValueResource(IntegerParameterValueFacade integerParameterValueFacade) {
        this.integerParameterValueFacade = integerParameterValueFacade;
    }

    /**
     * {@code POST  /integer-parameter-values} : Create a new integerParameterValue.
     *
     * @param integerParameterValueDTO the integerParameterValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integerParameterValueDTO, or with status {@code 400 (Bad Request)} if the integerParameterValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integer-parameter-values")
    public Mono<ResponseEntity<IntegerParameterValueDTO>> createIntegerParameterValue(
        @Valid @RequestBody IntegerParameterValueDTO integerParameterValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to save IntegerParameterValue : {}", integerParameterValueDTO);
        if (integerParameterValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new integerParameterValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return integerParameterValueFacade
            .save(integerParameterValueDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/integer-parameter-values/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /integer-parameter-values/:id} : Updates an existing integerParameterValue.
     *
     * @param id the id of the integerParameterValueDTO to save.
     * @param integerParameterValueDTO the integerParameterValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integerParameterValueDTO,
     * or with status {@code 400 (Bad Request)} if the integerParameterValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integerParameterValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integer-parameter-values/{id}")
    public Mono<ResponseEntity<IntegerParameterValueDTO>> updateIntegerParameterValue(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntegerParameterValueDTO integerParameterValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IntegerParameterValue : {}, {}", id, integerParameterValueDTO);
        if (integerParameterValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integerParameterValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return integerParameterValueFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return integerParameterValueFacade
                    .update(integerParameterValueDTO)
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
     * {@code PATCH  /integer-parameter-values/:id} : Partial updates given fields of an existing integerParameterValue, field will ignore if it is null
     *
     * @param id the id of the integerParameterValueDTO to save.
     * @param integerParameterValueDTO the integerParameterValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integerParameterValueDTO,
     * or with status {@code 400 (Bad Request)} if the integerParameterValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the integerParameterValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the integerParameterValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/integer-parameter-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<IntegerParameterValueDTO>> partialUpdateIntegerParameterValue(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntegerParameterValueDTO integerParameterValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntegerParameterValue partially : {}, {}", id, integerParameterValueDTO);
        if (integerParameterValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integerParameterValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return integerParameterValueFacade
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<IntegerParameterValueDTO> result = integerParameterValueFacade.partialUpdate(integerParameterValueDTO);

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
     * {@code GET  /integer-parameter-values} : get all the integerParameterValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integerParameterValues in body.
     */
    @GetMapping(value = "/integer-parameter-values", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<IntegerParameterValueDTO>> getAllIntegerParameterValues() {
        log.debug("REST request to get all IntegerParameterValues");
        return integerParameterValueFacade.findAll().collectList();
    }

    /**
     * {@code GET  /integer-parameter-values} : get all the integerParameterValues as a stream.
     * @return the {@link Flux} of integerParameterValues.
     */
    @GetMapping(value = "/integer-parameter-values", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<IntegerParameterValueDTO> getAllIntegerParameterValuesAsStream() {
        log.debug("REST request to get all IntegerParameterValues as a stream");
        return integerParameterValueFacade.findAll();
    }

    /**
     * {@code GET  /integer-parameter-values/:id} : get the "id" integerParameterValue.
     *
     * @param id the id of the integerParameterValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integerParameterValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integer-parameter-values/{id}")
    public Mono<ResponseEntity<IntegerParameterValueDTO>> getIntegerParameterValue(@PathVariable Long id) {
        log.debug("REST request to get IntegerParameterValue : {}", id);
        Mono<IntegerParameterValueDTO> integerParameterValueDTO = integerParameterValueFacade.findOne(id);
        return ResponseUtil.wrapOrNotFound(integerParameterValueDTO);
    }

    /**
     * {@code DELETE  /integer-parameter-values/:id} : delete the "id" integerParameterValue.
     *
     * @param id the id of the integerParameterValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integer-parameter-values/{id}")
    public Mono<ResponseEntity<Void>> deleteIntegerParameterValue(@PathVariable Long id) {
        log.debug("REST request to delete IntegerParameterValue : {}", id);
        return integerParameterValueFacade
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
