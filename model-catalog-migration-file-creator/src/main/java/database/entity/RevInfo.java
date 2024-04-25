package database.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@RevisionEntity
@Table(name = "revinfo")
public class RevInfo {
  @Id
  @RevisionNumber
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rev")
  private Long rev;

  @RevisionTimestamp
  @Column(name = "revtstmp")
  private LocalDateTime revtstmp;

  public RevInfo() {}

  public RevInfo(Long rev, LocalDateTime revtstmp) {
    this.rev = rev;
    this.revtstmp = revtstmp;
  }

  public Long getRev() {
    return rev;
  }

  public void setRev(Long rev) {
    this.rev = rev;
  }

  public LocalDateTime getRevtstmp() {
    return revtstmp;
  }

  public void setRevtstmp(LocalDateTime revtstmp) {
    this.revtstmp = revtstmp;
  }
}
