package ai.turintech.modelcatalog.repository.rowmapper;

import ai.turintech.modelcatalog.entity.MlTaskType;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link MlTaskType}, with proper type conversions.
 */
@Service
public class MlTaskTypeRowMapper implements BiFunction<Row, String, MlTaskType> {

    private final ColumnConverter converter;

    public MlTaskTypeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link MlTaskType} stored in the database.
     */
    @Override
    public MlTaskType apply(Row row, String prefix) {
        MlTaskType entity = new MlTaskType();
        entity.setId(converter.fromRow(row, prefix + "_id", UUID.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setModelsId(converter.fromRow(row, prefix + "_models_id", UUID.class));
        return entity;
    }
}
