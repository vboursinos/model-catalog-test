package migration_files_creator.insert_queries.staticTables;

public interface StaticTableCreator {
  void createStaticTable(String latestFilename);
}
