package ai.turintech.modelcatalog.repository.rowmapper;

import ai.turintech.modelcatalog.entity.Model;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Model}, with proper type conversions.
 */
@Service
public class ModelRowMapper implements BiFunction<Row, String, Model> {

    private final ColumnConverter converter;

    public ModelRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Model} stored in the database.
     */
    @Override
    public Model apply(Row row, String prefix) {
        Model entity = new Model();
        entity.setId(converter.fromRow(row, prefix + "_id", UUID.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setDisplayName(converter.fromRow(row, prefix + "_display_name", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setAdvantages(converter.fromRow(row, prefix + "_advantages", String.class));
        entity.setDisadvantages(converter.fromRow(row, prefix + "_disadvantages", String.class));
        entity.setEnabled(converter.fromRow(row, prefix + "_enabled", Boolean.class));
        entity.setDecistionTree(converter.fromRow(row, prefix + "_decistion_tree", Boolean.class));
        entity.setParametersId(converter.fromRow(row, prefix + "_parameters_id", UUID.class));
        return entity;
    }
}
