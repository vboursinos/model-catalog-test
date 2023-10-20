package ai.turintech.modelcatalog.repository;

import ai.turintech.modelcatalog.entity.ParameterType;
import ai.turintech.modelcatalog.repository.rowmapper.ParameterRowMapper;
import ai.turintech.modelcatalog.repository.rowmapper.ParameterTypeDefinitionRowMapper;
import ai.turintech.modelcatalog.repository.rowmapper.ParameterTypeRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the ParameterType entity.
 */
@SuppressWarnings("unused")
class ParameterTypeRepositoryInternalImpl extends SimpleR2dbcRepository<ParameterType, UUID> implements ParameterTypeRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ParameterRowMapper parameterMapper;
    private final ParameterTypeDefinitionRowMapper parametertypedefinitionMapper;
    private final ParameterTypeRowMapper parametertypeMapper;

    private static final Table entityTable = Table.aliased("parameter_type", EntityManager.ENTITY_ALIAS);
    private static final Table parameterTable = Table.aliased("parameter", "parameter");
    private static final Table parameterTypeDefinitionTable = Table.aliased("parameter_type_definition", "parameterTypeDefinition");

    public ParameterTypeRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ParameterRowMapper parameterMapper,
        ParameterTypeDefinitionRowMapper parametertypedefinitionMapper,
        ParameterTypeRowMapper parametertypeMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ParameterType.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.parameterMapper = parameterMapper;
        this.parametertypedefinitionMapper = parametertypedefinitionMapper;
        this.parametertypeMapper = parametertypeMapper;
    }

    @Override
    public Flux<ParameterType> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ParameterType> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ParameterTypeSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ParameterSqlHelper.getColumns(parameterTable, "parameter"));
        columns.addAll(ParameterTypeDefinitionSqlHelper.getColumns(parameterTypeDefinitionTable, "parameterTypeDefinition"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(parameterTable)
            .on(Column.create("parameter_id", entityTable))
            .equals(Column.create("id", parameterTable))
            .leftOuterJoin(parameterTypeDefinitionTable)
            .on(Column.create("parameter_type_definition_id", entityTable))
            .equals(Column.create("id", parameterTypeDefinitionTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ParameterType.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ParameterType> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ParameterType> findById(UUID id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(StringUtils.wrap(id.toString(), "'")));
        return createQuery(null, whereClause).one();
    }

    private ParameterType process(Row row, RowMetadata metadata) {
        ParameterType entity = parametertypeMapper.apply(row, "e");
        entity.setParameter(parameterMapper.apply(row, "parameter"));
        entity.setParameterTypeDefinition(parametertypedefinitionMapper.apply(row, "parameterTypeDefinition"));
        return entity;
    }

    @Override
    public <S extends ParameterType> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
