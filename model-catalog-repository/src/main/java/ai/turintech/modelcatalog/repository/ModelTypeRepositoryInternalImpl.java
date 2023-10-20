package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ModelType;
import ai.turintech.modelcatalog.repository.rowmapper.ModelRowMapper;
import ai.turintech.modelcatalog.repository.rowmapper.ModelTypeRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the ModelType entity.
 */
@SuppressWarnings("unused")
class ModelTypeRepositoryInternalImpl extends SimpleR2dbcRepository<ModelType, UUID> implements ModelTypeRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ModelRowMapper modelMapper;
    private final ModelTypeRowMapper modeltypeMapper;

    private static final Table entityTable = Table.aliased("model_type", EntityManager.ENTITY_ALIAS);
    private static final Table modelsTable = Table.aliased("model", "models");

    public ModelTypeRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ModelRowMapper modelMapper,
        ModelTypeRowMapper modeltypeMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ModelType.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.modeltypeMapper = modeltypeMapper;
    }

    @Override
    public Flux<ModelType> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ModelType> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ModelTypeSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ModelSqlHelper.getColumns(modelsTable, "models"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(modelsTable)
            .on(Column.create("models_id", entityTable))
            .equals(Column.create("id", modelsTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ModelType.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ModelType> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ModelType> findById(UUID id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(StringUtils.wrap(id.toString(), "'")));
        return createQuery(null, whereClause).one();
    }

    private ModelType process(Row row, RowMetadata metadata) {
        ModelType entity = modeltypeMapper.apply(row, "e");
        entity.setModels(modelMapper.apply(row, "models"));
        return entity;
    }

    @Override
    public <S extends ModelType> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
