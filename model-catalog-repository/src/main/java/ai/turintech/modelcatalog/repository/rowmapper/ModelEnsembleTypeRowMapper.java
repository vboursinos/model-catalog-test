package ai.turintech.modelcatalog.repository.rowmapper;

import ai.turintech.modelcatalog.entity.ModelEnsembleType;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ModelEnsembleType}, with proper type conversions.
 */
@Service
public class ModelEnsembleTypeRowMapper implements BiFunction<Row, String, ModelEnsembleType> {

    private final ColumnConverter converter;

    public ModelEnsembleTypeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ModelEnsembleType} stored in the database.
     */
    @Override
    public ModelEnsembleType apply(Row row, String prefix) {
        ModelEnsembleType entity = new ModelEnsembleType();
        entity.setId(converter.fromRow(row, prefix + "_id", UUID.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setModelsId(converter.fromRow(row, prefix + "_models_id", UUID.class));
        return entity;
    }
}
