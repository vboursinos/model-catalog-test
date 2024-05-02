package ai.turintech.modelcatalog.migrationfilescreator.dynamicquerycreator.insertqueries.model.pivotTables;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PivotStrategyFactory {

  @Autowired private List<Pivot> pivotStrategies;

  public Pivot getPivotStrategy(String pivotType) {
    for (Pivot pivotStrategy : pivotStrategies) {
      if (pivotStrategy.getTableName().equals(pivotType)) {
        return pivotStrategy;
      }
    }
    return null;
  }
}
