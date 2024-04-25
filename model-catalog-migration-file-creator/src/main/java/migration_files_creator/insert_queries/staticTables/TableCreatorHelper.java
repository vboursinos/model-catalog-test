package migration_files_creator.insert_queries.staticTables;

import java.text.SimpleDateFormat;

public class TableCreatorHelper {

  public static String buildRevInfoInsertSQL() {
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    return "INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ), '"
        + timeStamp
        + "');\n";
  }

  public static String buildInsertAuditSQL(String value, String tableName, int revType) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO ")
        .append(tableName)
        .append("_aud(id, rev, revtype, name, created_at, updated_at) VALUES (")
        .append("(select id from ")
        .append(tableName)
        .append(" where name = '")
        .append(value)
        .append("'), (select max(rev) from revinfo),")
        .append(revType)
        .append(", '")
        .append(value)
        .append("', '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }

  public static String buildDeleteAuditSQL(String value, String tableName, int revType) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO ")
        .append(tableName)
        .append("_aud(id, rev, revtype, name, created_at, updated_at) VALUES (")
        .append("(select id from ")
        .append(tableName)
        .append(" where name = '")
        .append(value)
        .append("'), (select max(rev) from revinfo),")
        .append(revType)
        .append(", null, '")
        .append(date)
        .append("', '")
        .append(date)
        .append("');\n");
    return sb.toString();
  }
}
