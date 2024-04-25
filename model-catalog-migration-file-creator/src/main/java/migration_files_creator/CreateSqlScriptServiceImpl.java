package migration_files_creator;

import java.nio.file.Paths;
import migration_files_creator.init.DynamicTablesQueryCreation;
import migration_files_creator.insert_queries.staticTables.InsertStaticTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.FileUtils;

@Component
public class CreateSqlScriptServiceImpl implements CreateSqlScriptService {
  private static final String SQL_DIR_PATH = "sql_scripts";
  @Autowired private DynamicTablesQueryCreation dynamicTablesQueryCreation;
  @Autowired private InsertStaticTables insertStaticTables;

  public CreateSqlScriptServiceImpl() {
    FileUtils.createDirectory(Paths.get(SQL_DIR_PATH));
  }

  @Transactional
  public void createFiles() {
    insertStaticTables.insertDataScripts();
    dynamicTablesQueryCreation.insertDataScripts();
  }
}
