package ai.turintech.modelcatalog.repository.rowmapper;

import ai.turintech.modelcatalog.entity.Parameter;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Parameter}, with proper type conversions.
 */
@Service
public class ParameterRowMapper implements BiFunction<Row, String, Parameter> {

    private final ColumnConverter converter;

    public ParameterRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Parameter} stored in the database.
     */
    @Override
    public Parameter apply(Row row, String prefix) {
        Parameter entity = new Parameter();
        entity.setId(converter.fromRow(row, prefix + "_id", UUID.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setLabel(converter.fromRow(row, prefix + "_label", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setEnbled(converter.fromRow(row, prefix + "_enbled", Boolean.class));
        entity.setFixedValue(converter.fromRow(row, prefix + "_fixed_value", Boolean.class));
        entity.setOrdering(converter.fromRow(row, prefix + "_ordering", Integer.class));
        entity.setDefinitionsId(converter.fromRow(row, prefix + "_definitions_id", UUID.class));
        return entity;
    }
}
