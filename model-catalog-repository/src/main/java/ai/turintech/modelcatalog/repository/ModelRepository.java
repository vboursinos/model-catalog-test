package ai.turintech.modelcatalog.repository;

import ai.turintech.components.jpa.search.repository.SearchRepository;
import ai.turintech.components.jpa.search.repository.SearchRepositoryAnnotation;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.entity.Model;
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
@SearchRepositoryAnnotation(entity = Model.class)
@Repository
public interface ModelRepository
    extends ModelRepositoryWithBagRelationships,
        JpaRepository<Model, UUID>,
        SearchRepository<Model> {
  default Optional<Model> findOneWithEagerRelationships(UUID id) {
    return this.fetchBagRelationships(this.findById(id));
  }

  default List<Model> findAllWithEagerRelationships() {
    return this.fetchBagRelationships(this.findAll());
  }

  default Page<Model> findAllWithEagerRelationships(Pageable pageable) {
    return this.fetchBagRelationships(this.findAll(pageable));
  }

  @Query(
      "SELECT new ai.turintech.modelcatalog.dto.ModelDTO(m.id, m.name, m.displayName, m.description, m.advantages, m.disadvantages, m.enabled, m.decisionTree, t.name) FROM Model m JOIN m.mlTask t")
  List<ModelDTO> findAllModelsWithoutRelationships(Pageable pageable);
}
