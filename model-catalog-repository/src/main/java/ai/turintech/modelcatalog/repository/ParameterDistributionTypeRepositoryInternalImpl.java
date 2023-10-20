package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterDistributionType;
import ai.turintech.modelcatalog.repository.rowmapper.ParameterDistributionTypeRowMapper;
import ai.turintech.modelcatalog.repository.rowmapper.ParameterRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the ParameterDistributionType entity.
 */
@SuppressWarnings("unused")
class ParameterDistributionTypeRepositoryInternalImpl
    extends SimpleR2dbcRepository<ParameterDistributionType, UUID>
    implements ParameterDistributionTypeRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ParameterRowMapper parameterMapper;
    private final ParameterDistributionTypeRowMapper parameterdistributiontypeMapper;

    private static final Table entityTable = Table.aliased("parameter_distribution_type", EntityManager.ENTITY_ALIAS);
    private static final Table parameterTable = Table.aliased("parameter", "parameter");

    public ParameterDistributionTypeRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ParameterRowMapper parameterMapper,
        ParameterDistributionTypeRowMapper parameterdistributiontypeMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(
                converter.getMappingContext().getRequiredPersistentEntity(ParameterDistributionType.class)
            ),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.parameterMapper = parameterMapper;
        this.parameterdistributiontypeMapper = parameterdistributiontypeMapper;
    }

    @Override
    public Flux<ParameterDistributionType> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ParameterDistributionType> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ParameterDistributionTypeSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ParameterSqlHelper.getColumns(parameterTable, "parameter"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(parameterTable)
            .on(Column.create("parameter_id", entityTable))
            .equals(Column.create("id", parameterTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ParameterDistributionType.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ParameterDistributionType> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ParameterDistributionType> findById(UUID id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(StringUtils.wrap(id.toString(), "'")));
        return createQuery(null, whereClause).one();
    }

    private ParameterDistributionType process(Row row, RowMetadata metadata) {
        ParameterDistributionType entity = parameterdistributiontypeMapper.apply(row, "e");
        entity.setParameter(parameterMapper.apply(row, "parameter"));
        return entity;
    }

    @Override
    public <S extends ParameterDistributionType> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
