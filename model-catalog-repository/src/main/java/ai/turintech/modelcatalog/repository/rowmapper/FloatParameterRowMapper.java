package ai.turintech.modelcatalog.repository.rowmapper;

import ai.turintech.modelcatalog.entity.FloatParameter;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link FloatParameter}, with proper type conversions.
 */
@Service
public class FloatParameterRowMapper implements BiFunction<Row, String, FloatParameter> {

    private final ColumnConverter converter;

    public FloatParameterRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link FloatParameter} stored in the database.
     */
    @Override
    public FloatParameter apply(Row row, String prefix) {
        FloatParameter entity = new FloatParameter();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDefaultValue(converter.fromRow(row, prefix + "_default_value", Float.class));
        return entity;
    }
}
