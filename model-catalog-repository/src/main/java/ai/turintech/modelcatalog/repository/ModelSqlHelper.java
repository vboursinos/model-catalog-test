package ai.turintech.modelcatalog.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ModelSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("name", table, columnPrefix + "_name"));
        columns.add(Column.aliased("display_name", table, columnPrefix + "_display_name"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("advantages", table, columnPrefix + "_advantages"));
        columns.add(Column.aliased("disadvantages", table, columnPrefix + "_disadvantages"));
        columns.add(Column.aliased("enabled", table, columnPrefix + "_enabled"));
        columns.add(Column.aliased("decistion_tree", table, columnPrefix + "_decistion_tree"));

        columns.add(Column.aliased("parameters_id", table, columnPrefix + "_parameters_id"));
        return columns;
    }
}
