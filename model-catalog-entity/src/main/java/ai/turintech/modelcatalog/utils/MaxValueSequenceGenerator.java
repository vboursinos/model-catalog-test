package ai.turintech.modelcatalog.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class MaxValueSequenceGenerator extends SequenceStyleGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object obj)
      throws HibernateException {
    String sql = "SELECT COALESCE(MAX(rev), 0) + 1 FROM revinfo";
    return session.doReturningWork(
        connection -> {
          try (PreparedStatement stmt = connection.prepareStatement(sql);
              ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
              return rs.getLong(1);
            } else {
              throw new HibernateException("Unable to get sequence value from database");
            }
          }
        });
  }
}
