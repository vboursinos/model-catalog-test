package ai.turintech.modelcatalog.repository;

import ai.turintech.components.jpa.search.repository.SearchRepository;
import ai.turintech.components.jpa.search.repository.SearchRepositoryAnnotation;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.entity.ModelLimited;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the Model entity.
 *
 * <p>When extending this class, extend ModelRepositoryWithBagRelationships too. For more
 * information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@SearchRepositoryAnnotation(entity = ModelLimited.class)
@Repository
public interface ModelLimitedRepository
    extends ModelRepositoryWithBagRelationships,
        JpaRepository<ModelLimited, UUID>,
        SearchRepository<ModelLimited> {}
