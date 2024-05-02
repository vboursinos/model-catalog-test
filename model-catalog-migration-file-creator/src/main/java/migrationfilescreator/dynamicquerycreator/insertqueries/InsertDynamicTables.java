package migrationfilescreator.dynamicquerycreator.insertqueries;

import ai.turintech.modelcatalog.dto.ModelDTO;
import java.util.*;
import migrationfilescreator.model.*;

public interface InsertDynamicTables {
  public String buildInsertSQL(Models models, List<ModelDTO> dbModels);
}
